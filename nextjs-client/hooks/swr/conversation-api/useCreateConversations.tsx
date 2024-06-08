import { API_BASE_URL } from "@/constants";
import axios from "axios";
import useSWR from "swr";

import swr from "swr";

const fetcher = async (url: string) => {
  const response = await axios.post(url);
  return response.data;
};

export const useCreateConversations = (currentUserId: any, friendId: any) => {
  const {
    data,
    error,
    mutate: createConversationMutate,
  } = useSWR(
    `${API_BASE_URL}/conversations/${currentUserId}/${friendId}`,
    fetcher,
    {
      revalidateOnFocus: false,
    }
  );

  const createConversation = async () => {
    try {
      const response = await fetcher(
        `${API_BASE_URL}/conversations/${currentUserId}/${friendId}`
      );
      createConversationMutate(response, false);
      return response; // Trả về response từ API
    } catch (error: any) {
      console.log("Error creating conversation: ", error.message);
      throw error; // Rethrow the error to handle it in the component
    }
  };

  return {
    data,
    isLoading: !error && !data,
    isError: error,
    createConversation,
  };
};

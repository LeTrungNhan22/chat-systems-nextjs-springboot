import { API_BASE_URL } from "@/constants";
import axios from "axios";

import useSWR from "swr";

const fetcher = async (url: string) => {
  const response = await axios.get(url);
  return response.data;
};

export const useConversationByChatId = (chatId: string | string[]) => {
  const { data, error } = useSWR(
    `${API_BASE_URL}/conversations/${chatId}`,
    fetcher,
    {
      revalidateOnFocus: false,
    }
  );

  return {
    data,
    isLoading: !error && !data,
    isError: error,
  };
};

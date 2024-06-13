import { API_BASE_URL } from "@/constants";
import axios from "axios";
import useSWR from "swr";

const fetcher = async (url: string) => {
  const response = await axios.get(url);
  return response.data;
};

export const useListConversationsByUseId = (currentUserId: any) => {
  const {
    data,
    error,
    mutate: mutateListConversations,
  } = useSWR(`${API_BASE_URL}/conversations/user/${currentUserId}`, fetcher, {
    revalidateOnFocus: true,
    revalidateOnReconnect: true,
    refreshWhenOffline: false,
  });

  return {
    data,
    isLoading: !error && !data,
    isError: error,
    mutateListConversations,
  };
};

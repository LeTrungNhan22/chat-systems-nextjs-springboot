import useSWR from "swr";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

const fetcher = async (url: string) => {
  const res = await axios.get(url);
  return res.data;
};

export const useGetMessagesByChatId = (chatId: string[] | string) => {
  const { data, error } = useSWR(
    `${API_BASE_URL}/messages/by-chat/${chatId}`,
    fetcher,
    {
      revalidateOnFocus: true,
      revalidateOnReconnect: true,
    }
  );

  return {
    responseMessageApi: data,
    content: data?.content as any[],
    isLoading: !error && !data,
    isError: error,
  };
};

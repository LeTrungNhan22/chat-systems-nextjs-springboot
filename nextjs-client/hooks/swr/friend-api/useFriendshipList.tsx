import swr from "swr";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useFriendshipList(userId: string | undefined) {
  const { data, error } = swr(
    `${API_BASE_URL}/friends/${userId}/friends`,
    fetcher
  );

  return {
    friendshipList: data,
    isLoading: !error && !data,
    isError: error,
  };
}

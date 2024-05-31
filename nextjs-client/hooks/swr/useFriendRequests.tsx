import useSWR from "swr";
import { FriendRequest } from "@/utils/types/friends/friendTypes";
import axios from "axios";

const fetcher = async (url: string): Promise<FriendRequest[]> => {
  const res = await axios.get(url);
  return res.data;
};

export default function useFriendRequests(userId: string | null) {
  const {
    data: sentRequests,
    error: sentError,
    mutate: mutateSent,
  } = useSWR<FriendRequest[], any>(
    userId ? `/api/friends/${userId}/requests/sent` : null,
    fetcher,
    {
      onError: (error) => console.error("Error fetching sent requests:", error),
      fallbackData: [], // default data [] while waiting for userId
    }
  );
  const {
    data: receivedRequests,
    error: receivedError,
    mutate: mutateReceived,
  } = useSWR<FriendRequest[], any>(
    userId ? `/api/friends/${userId}/requests/received` : null,
    fetcher,
    {
      onError: (error) =>
        console.error("Error fetching received requests:", error),
      fallbackData: [],
    }
  );

  const isLoading =
    !sentRequests && !receivedRequests && !sentError && !receivedError;
  const isError = sentError || receivedError;

  return {
    sentRequests: sentRequests || [], //not null
    receivedRequests: receivedRequests || [], //not null
    isLoading,
    isError,
    mutateSent,
    mutateReceived,
  };
}

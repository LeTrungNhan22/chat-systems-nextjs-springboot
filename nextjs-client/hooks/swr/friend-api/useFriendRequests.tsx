import useSWR from "swr";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

interface FriendRequestsResponse {
  id: string;
  userId1: string;
  userId2: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

const fetcher = async (url: string): Promise<FriendRequestsResponse[]> => {
  try {
    const res = await axios.get(url);
    return res.data;
  } catch (error) {
    console.error("Error fetching friend requests:", error);
    throw error; // Ném lỗi lên để component sử dụng có thể xử lý
  }
};

export default function useFriendRequests(userId: string | undefined) {
  const {
    data: sentRequests,
    error: sentError,
    mutate: mutateSent,
  } = useSWR<FriendRequestsResponse[], any>(
    userId
      ? `${API_BASE_URL}/friends/${userId}/requests/sent`
      : undefined,
    fetcher,{
      revalidateOnFocus: false,
    }
  );

  const {
    data: receivedRequests,
    error: receivedError,
    mutate: mutateReceived,
  } = useSWR<FriendRequestsResponse[], any>(
    userId
      ? `${API_BASE_URL}/friends/${userId}/requests/received`
      : undefined,
    fetcher,{
      revalidateOnFocus: false,
    }
  );

  // isLoading chỉ đúng khi cả 2 requests chưa hoàn thành và không có lỗi.
  const isLoading =
    !sentRequests && !receivedRequests && !sentError && !receivedError;

  return {
    sentRequests: sentRequests || [],
    receivedRequests: receivedRequests || [],
    isLoading,
    isError: sentError || receivedError, // Báo lỗi nếu có bất kỳ lỗi nào trong 2 request
    mutateSent,
    mutateReceived,
  };
}

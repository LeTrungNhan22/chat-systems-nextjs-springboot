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

export default function useGetListFriend(userId: string | undefined) {
  const {
    data: listFriendByUserId,
    error: sentError,
    mutate: mutateCurrentList,
  } = useSWR<FriendRequestsResponse[], any>(
    userId ? `${API_BASE_URL}/friends/${userId}/friends` : undefined,
    fetcher,
    {
      revalidateOnFocus: false,
    }
  );
  const isLoading = !listFriendByUserId && !sentError;

  return {
    listFriend: listFriendByUserId || [],
    isLoading,
    isError: sentError,
    mutateCurrentList,
  };
}

import useSWR from "swr";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

const useAddFriend = () => {
  const { data, error, isLoading, mutate } = useSWR(null, null);

  const addFriend = async (
    currentUserId: string | undefined,
    friendId: string
  ) => {
    try {
      const response = await axios.post(
        `${API_BASE_URL}/api/v1/friends/${currentUserId}/requests/${friendId}`
      );
      mutate(response.data);
      return response.data; // return response.data
    } catch (error) {
      console.log("Error adding friend: ", error);
      throw error;
    }
  };

  return {
    data,
    error,
    isLoading,
    addFriend,
  };
};

export default useAddFriend;

import useSWR from "swr";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

const fetchUserProfile = async (url: string) => {
  try {
    const res = await axios.get(url);
    return res.data;
  } catch (error) {
    console.error("Error fetching user profile:", error);
    throw error;
  }
};

export default function useUserProfileById(userId: string) {
  const { data, error, isLoading } = useSWR(
	userId ? `${API_BASE_URL}/users/${userId}` : null,
    fetchUserProfile,
    { revalidateOnFocus: false }
  );

  return {
    data,
    error,
    isLoading,
  };
}

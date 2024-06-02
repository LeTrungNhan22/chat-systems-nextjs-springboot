// File: hooks/swr/useUserSearchMutation.ts
import useSWRMutation from "swr/mutation";
import { TUser } from "@/utils/types/users/auth";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

const fetcher = (url: string) => axios.get(url).then((res) => res.data.content);

function useUserSearchMutation() {
  const { trigger, isMutating, error } = useSWRMutation(
    `${API_BASE_URL}/api/v1/users/search`, // Keep the base URL here
    (url, { arg }: { arg: string }) => {
      // Pass the query as arg
      return fetcher(`${url}?query=${arg}`);
    }
  );

  const searchUsers = async (query: string): Promise<TUser[]> => {
    const results = await trigger(query);
    return results;
  };

  return {
    searchUsers,
    isMutating,
    error,
  };
}

export default useUserSearchMutation;

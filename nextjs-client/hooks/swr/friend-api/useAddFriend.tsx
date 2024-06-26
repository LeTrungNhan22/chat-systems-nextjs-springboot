"use client";

import useSWR from "swr";
import axios from "axios";
import { API_BASE_URL } from "@/constants";
import { useContext } from "react";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";

const useAddFriend = () => {
  const { data, error, isLoading, mutate } = useSWR(null, null);
  const { user } = useContext(AuthContext);
  const addFriend = async (userId2: string | undefined) => {
    try {
      const response = await axios.post(
        `${API_BASE_URL}/friends/${user?.user.id}/requests/${userId2}`
      );
      mutate(response.data);
      return response.data; // return response.data
    } catch (error: any) {
      // error from server
      if (error.response) {
        throw error.response.data;
      } else {
        // any other error
        throw error;
      }
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

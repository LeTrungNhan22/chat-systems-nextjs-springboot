import useSWRMutation from "swr/mutation";
import { FriendRequestDto } from "@/utils/types/friends/friendTypes";
import useFriendRequests from "@/hooks/swr/useFriendRequests";
import axios from "axios";

const fetcher = async (key: string, { arg }: { arg: FriendRequestDto }) => {
  const res = await axios.post(key, arg);
  return res.data;
};

function useSendFriendRequest(friendReqId: string | null) {
  const key: string | null = friendReqId
    ? `/api/friends/${friendReqId}/requests`
    : null;
  const { mutateSent, mutateReceived } = useFriendRequests(friendReqId);

  const { trigger, isMutating } = useSWRMutation(key, fetcher, {
    onSuccess: () => {
      if (friendReqId) {
        mutateSent();
        mutateReceived();
      }
    },
    onError: (error) => {
      console.error("Error sending friend request:", error);
    },
  });

  return {
    sendFriendRequest: async (friendId: string) => {
      const newFriendRequest: FriendRequestDto = { friendId };
      if (key) {
        return trigger(newFriendRequest);
      } else {
        throw new Error("User ID is required to send a friend request.");
      }
    },
    isSendingRequest: isMutating,
  };
}

export default useSendFriendRequest;

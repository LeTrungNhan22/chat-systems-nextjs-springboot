import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { API_BASE_URL } from "@/constants";
import useGetListFriend from "@/hooks/swr/useGetListFriend";
import useUserProfileById from "@/hooks/swr/useUserProfileById";
import axios from "axios";
import Image from "next/image";
import React from "react";

type Props = {};

const FriendRequestReceivedCardItem = ({
  request,
  mutateReceived,
  currentUserId,
}: {
  request: any;
  mutateReceived: any;
  currentUserId: string | undefined;
}) => {
  const { data: userProfile, isLoading } = useUserProfileById(request.userId1);
  const { mutateCurrentList } = useGetListFriend(currentUserId);
  const handleAcceptRequest = async (friendId: string) => {
    try {
      await axios.put(
        `${API_BASE_URL}/api/v1/friends/${currentUserId}/requests/${friendId}?friend-status=ACCEPTED`
      );
      mutateReceived();
      mutateCurrentList();
    } catch (error) {
      console.error("Error accepting friend request:", error);
      // Handle error, e.g., display error message to the user
    }
  };

  const handleRejectRequest = async (friendId: string) => {
    try {
      await axios.put(
        `${API_BASE_URL}/api/v1/friends/${currentUserId}/requests/${friendId}?friend-status=REJECTED`
      );
      mutateReceived();
      mutateCurrentList();
    } catch (error) {
      console.error("Error rejecting friend request:", error);
      // Handle error, e.g., display error message to the user
    }
  };

  return (
    <Card key={request.id} className=" rounded-lg shadow-md max-w-52 h-full">
      {isLoading ? (
        <div>Loading profile...</div>
      ) : userProfile ? (
        <>
          <Image
            src={userProfile.imageUrl}
            alt="avatar"
            width={110}
            height={110}
            className="w-full h-[110px] object-cover rounded-t-lg"
          />
          <div className="p-2 text-surface dark:text-white">
            <h5 className="my-2 text font-medium leading-tight">
              {userProfile.username}
            </h5>
            <p className="mb-4 text-base truncate text-gray-700 dark:text-gray-400">
              {userProfile.email}
            </p>
            <div className="flex items-center justify-between gap-2">
              <Button
                size="sm"
                onClick={() => handleAcceptRequest(request.userId1)}
                disabled={isLoading}
              >
                Chấp nhận
              </Button>
              <Button
                size="sm"
                onClick={() => handleRejectRequest(request.userId2)}
                variant="destructive"
                disabled={isLoading}
              >
                Từ chối
              </Button>
            </div>
          </div>
        </>
      ) : (
        <div>Error loading profile</div>
      )}
    </Card>
  );
};

export default FriendRequestReceivedCardItem;

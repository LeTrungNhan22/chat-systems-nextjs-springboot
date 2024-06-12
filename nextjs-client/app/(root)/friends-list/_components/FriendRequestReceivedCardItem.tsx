import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { API_BASE_URL } from "@/constants";
import useGetListFriend from "@/hooks/swr/friend-api/useGetListFriend";
import useUserProfileById from "@/hooks/swr/friend-api/useUserProfileById";
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
        `${API_BASE_URL}/friends/${currentUserId}/requests/${friendId}?friend-status=ACCEPTED`
      );
      mutateCurrentList();
      mutateReceived();
    } catch (error) {
      console.error("Error accepting friend request:", error);
      // Handle error, e.g., display error message to the user
    }
  };

  const handleRejectRequest = async (friendId: string) => {
    try {
      await axios.put(
        `${API_BASE_URL}/friends/${currentUserId}/requests/${friendId}?friend-status=REJECTED`
      );
      mutateCurrentList();
      mutateReceived();
    } catch (error) {
      console.error("Error rejecting friend request:", error);
      // Handle error, e.g., display error message to the user
    }
  };

  return (
    <Card key={request.id} className="rounded-lg shadow-md max-w-52 h-full">
      {isLoading ? (
        <div>Loading profile...</div>
      ) : userProfile ? (
        <>
          <div className="w-full h-[100px] relative">
            <Image
              src={userProfile.imageUrl}
              alt={userProfile.username}
              fill // Giữ nguyên fill
              sizes="100px"
              loading="lazy"
              className="object-cover rounded-t-lg"
              style={{ objectFit: "cover" }} // Thêm objectFit: "cover"
            />
          </div>
          <div className="p-2  dark:text-white">
            <h5 className="my-2 text-base font-semibold leading-tight">
              {userProfile.username}
            </h5>
            <p className="mb-3 text-base truncate text-gray-700 dark:text-gray-400">
              {userProfile.email}
            </p>
            <div className="mb-1 flex items-center justify-between ga ">
              <Button
                size="sm"
                onClick={() => handleAcceptRequest(request.userId1)}
                disabled={isLoading}
                className="btn-gradient-blue"
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

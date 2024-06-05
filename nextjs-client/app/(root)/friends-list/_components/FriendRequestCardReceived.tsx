"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Check, X } from "lucide-react";
import React from "react";
import axios from "axios";
import { API_BASE_URL } from "@/constants";
import useUserProfileById from "@/hooks/swr/useUserProfileById";

// Custom component for each FriendRequestCard
function FriendRequestCardReceived({
  request,
  mutateReceived,
  currentUserId,
}: {
  request: any;
  mutateReceived: any;
  currentUserId: string | undefined;
}) {
  const { data: userProfile, isLoading } = useUserProfileById(request.userId1);

  const handleAcceptRequest = async (friendId: string) => {
    try {
      await axios.put(
        `${API_BASE_URL}/api/v1/friends/${currentUserId}/requests/${friendId}?friend-status=ACCEPTED`
      );
      mutateReceived();
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
    } catch (error) {
      console.error("Error rejecting friend request:", error);
      // Handle error, e.g., display error message to the user
    }
  };

  return (
    <Card
      key={request.id}
      className="w-full p-2 flex flex-row items-center justify-between gap-2"
    >
      {isLoading ? (
        <div>Loading profile...</div>
      ) : userProfile ? (
        <div className="flex items-center gap-2 truncate">
          <Avatar>
            <AvatarImage src={userProfile.imageUrl} />
            <AvatarFallback>CN</AvatarFallback>
          </Avatar>
          <div className="flex flex-col truncate">
            <h4 className="truncate">{userProfile.username}</h4>
            <p className="text-xs text-muted-foreground truncate">
              {userProfile.email}
            </p>
          </div>
        </div>
      ) : (
        <div>Error loading profile</div>
      )}

      <div className="flex items-center gap-2">
        <Button
          size="icon"
          onClick={() => handleAcceptRequest(request.userId1)}
          disabled={isLoading}
        >
          <Check />
        </Button>
        <Button
          size="icon"
          onClick={() => handleRejectRequest(request.userId2)}
          variant="destructive"
          disabled={isLoading}
        >
          <X className="h-4 w-4" />
        </Button>
      </div>
    </Card>
  );
}

export default FriendRequestCardReceived;

// Fetch user profile data

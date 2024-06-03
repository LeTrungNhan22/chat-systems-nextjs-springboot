"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Check, X } from "lucide-react";
import React from "react";
import axios from "axios";
import { API_BASE_URL } from "@/constants";
import useSWR from "swr";

const fetchUserProfile = async (url: string) => {
  try {
    const res = await axios.get(url);
    return res.data;
  } catch (error) {
    console.error("Error fetching user profile:", error);
    throw error;
  }
};

// Custom component for each FriendRequestCard
function FriendRequestCard({
  request,
  mutateReceived,
}: {
  request: any;
  mutateReceived: any;
}) {
  const { data: senderProfile, isLoading } = useSWR(
    request.currentUserId
      ? `${API_BASE_URL}/api/v1/users/${request.currentUserId}`
      : null,
    fetchUserProfile,
    { revalidateOnFocus: false }
  );

  const handleAcceptRequest = async (friendId: string) => {
    // ... (your handleAcceptRequest logic remains the same)
  };

  const handleRejectRequest = async (friendId: string) => {
    // ... (your handleRejectRequest logic remains the same)
  };

  return (
    <Card
      key={request.id}
      className="w-full p-2 flex flex-row items-center justify-between gap-2"
    >
      {isLoading ? (
        <div>Loading profile...</div>
      ) : senderProfile ? (
        <div className="flex items-center gap-2 truncate">
          <Avatar>
            <AvatarImage src={senderProfile.imageUrl} />
            <AvatarFallback>CN</AvatarFallback>
          </Avatar>
          <div className="flex flex-col truncate">
            <h4 className="truncate">{senderProfile.username}</h4>
            <p className="text-xs text-muted-foreground truncate">
              {senderProfile.email}
            </p>
          </div>
        </div>
      ) : (
        <div>Error loading profile</div>
      )}

      <div className="flex items-center gap-2">
        <Button
          size="icon"
          onClick={() => handleAcceptRequest(request.currentUserId)}
          disabled={isLoading}
        >
          <Check />
        </Button>
        <Button
          size="icon"
          onClick={() => handleRejectRequest(request.currentUserId)}
          variant="destructive"
          disabled={isLoading}
        >
          <X className="h-4 w-4" />
        </Button>
      </div>
    </Card>
  );
}

export default FriendRequestCard;

// Fetch user profile data

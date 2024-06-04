"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Check, X } from "lucide-react";
import React from "react";
import axios from "axios";
import { API_BASE_URL } from "@/constants";
import useSWR from "swr";
import { Badge } from "@/components/ui/badge";
import useUserProfileById from "@/hooks/swr/useUserProfileById";

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
function FriendRequestCardSent({
  request,
  mutateSent,
}: {
  request: any;
  mutateSent: any;
}) {
  const { data: userProfile, isLoading } = useUserProfileById(request.userId2);

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

      {!isLoading && request.status == "PENDING" ? (
        <div className="flex items-center gap-2">
          <Button disabled variant={"secondary"}>
            Pending
          </Button>
        </div>
      ) : null}
    </Card>
  );
}

export default FriendRequestCardSent;

// Fetch user profile data

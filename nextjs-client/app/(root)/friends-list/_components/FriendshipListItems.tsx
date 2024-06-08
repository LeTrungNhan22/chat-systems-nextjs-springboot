"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import React from "react";
import useUserProfileById from "@/hooks/swr/friend-api/useUserProfileById";
import FriendshipAction from "./FriendshipAction";

// Custom component for each FriendRequestCard
function FriendshipListItems({
  request,
  mutateCurrentList,
  currentUserId,
}: {
  request: any;
  mutateCurrentList: any;
  currentUserId: string | undefined;
}) {
  const otherUserId =
    request.userId1 === currentUserId ? request.userId2 : request.userId1;
  const { data: userProfile, isLoading } = useUserProfileById(otherUserId);

  return (
    <>
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
          <FriendshipAction
            otherUserId={otherUserId}
            currentUserId={currentUserId}
          />
        </div>
      </Card>
    </>
  );
}

export default FriendshipListItems;

// Fetch user profile data

"use client";

import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import useAddFriend from "@/hooks/swr/useAddFriend";
import useFriendRequests from "@/hooks/swr/useFriendRequests";
import { TUser } from "@/utils/types/users/auth";
import { useContext, useState } from "react";
type Props = {
  userSearchItem: TUser;
};

const UserSearchItem = ({ userSearchItem }: Props) => {
  const { user } = useContext(AuthContext);
  const { addFriend } = useAddFriend();
  const { sentRequests, mutateSent } = useFriendRequests(user?.user.id);

  const onHandleAddFriend = (friendId: string) => async () => {
    try {
      await addFriend(friendId);
      mutateSent();
    } catch (error) {
      console.log("Error adding friend: ", error);
    }
  };

  return (
    <>
      <Card className="w-full p-2 flex flex-row items-center justify-between gap-2">
        <div className="flex items-center gap-4 truncate">
          <Avatar>
            <AvatarImage
              src={userSearchItem.imageUrl}
              alt={userSearchItem.username}
            />
            <AvatarFallback>CN</AvatarFallback>
          </Avatar>
          <div className="flex flex-col truncate">
            <h4 className="truncate">{userSearchItem.username}</h4>
            <p className="text-xs text-muted-foreground truncate">
              {userSearchItem.email}
            </p>
          </div>
        </div>
        {
          <Button
            onClick={onHandleAddFriend(userSearchItem.id)}
            size="default"
            variant="secondary"
            disabled={sentRequests.some(
              (request) => request.userId2 === userSearchItem.id
            )}
          >
            {sentRequests.some(
              (request) => request.userId2 === userSearchItem.id
            )
              ? "Đã gửi "
              : "Kết bạn"}
          </Button>
        }
      </Card>
    </>
  );
};

export default UserSearchItem;

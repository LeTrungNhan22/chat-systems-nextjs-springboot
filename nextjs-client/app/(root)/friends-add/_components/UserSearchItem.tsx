import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import useAddFriend from "@/hooks/swr/useAddFriend";
import useFriendRequests from "@/hooks/swr/useFriendRequests";
import { TUser } from "@/utils/types/users/auth";
import React, { useContext } from "react";

type Props = {
  userSearchItem: TUser;
};

const UserSearchItem = ({ userSearchItem }: Props) => {
  const { data, error, isLoading, addFriend } = useAddFriend();
  const { user } = useContext(AuthContext);
  const onHandleAddFriend = (friendId: string) => async () => {
    try {
      await addFriend(user?.user.id, friendId);
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
        <Button
          size={"sm"}
          onClick={onHandleAddFriend(userSearchItem.id)}
          variant={"secondary"}
        >
          Gửi yêu cầu
        </Button>
      </Card>
    </>
  );
};

export default UserSearchItem;

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { TUser } from "@/utils/types/users/auth";
import React from "react";

type Props = {
  user: TUser;
};

const UserSearchItem = ({ user }: Props) => {
  return (
    <>
      <Card className="w-full p-2 flex flex-row items-center justify-between gap-2">
        <div className="flex items-center gap-4 truncate">
          <Avatar>
            <AvatarImage src={user.imageUrl} alt={user.username} />
            <AvatarFallback>CN</AvatarFallback>
          </Avatar>
          <div className="flex flex-col truncate">
            <h4 className="truncate">{user.username}</h4>
            <p className="text-xs text-muted-foreground truncate">
              {user.email}
            </p>
          </div>
        </div>
        <Button
          size={"sm"}
          onClick={() => {
            console.log(user.id);
          }}
          variant={"secondary"}
        >
          Gửi yêu cầu
        </Button>
      </Card>
    </>
  );
};

export default UserSearchItem;

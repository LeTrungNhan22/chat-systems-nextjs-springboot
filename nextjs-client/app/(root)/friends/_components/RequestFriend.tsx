'use client'
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { TUser } from "@/utils/types/auth";

import { Check, X } from "lucide-react";
import React from "react";

type Props = {};

const RequestFriend = (props: Props) => {
  return (
    <Card className="w-full p-2 flex flex-row items-center justify-between gap-2">
      <div className="flex items-center gap-4 truncate">
        <Avatar>
          <AvatarImage src="https://github.com/shadcn.png" />
          <AvatarFallback>CN</AvatarFallback>
        </Avatar>
        <div className="flex flex-col truncate">
          <h4 className="truncate">User Friend</h4>
          <p className="text-xs text-muted-foreground truncate">
            example@gmail.com
          </p>
        </div>
      </div>
      <div className="flex items-center gap-2">
        <Button size="icon" onClick={() => {}}>
          <Check />
        </Button>
        <Button size="icon" onClick={() => {}} variant={"destructive"}>
          <X className="h-4 w-4" />
        </Button>
      </div>
    </Card>
  );
};

export default RequestFriend;

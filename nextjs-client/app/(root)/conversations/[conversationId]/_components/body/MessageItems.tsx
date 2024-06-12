import { AvatarImage } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import React from "react";

import { format } from "date-fns";

type Props = {
  message: any;
  currentUserId: string | undefined;
};

const MessageItems = ({ message, currentUserId }: Props) => {
  const { chat, sender } = message;
  const fromCurrentUser = currentUserId === sender?.id;
  const formattedDate = format(new Date(message.timestamp), "HH:mm");

console.log("message", message);


  
  

  console.log("message", message);
  console.log("currentUserId", fromCurrentUser);

  return (
    <div
      className={cn("flex items-end ", {
        "justify-end": fromCurrentUser,
      })}
    >
      <div
        className={cn("flex flex-col w-full mx-2", {
          "order-1 items-end": fromCurrentUser,
          "order-1 items-start": !fromCurrentUser,
        })}
      >
        <div
          className={cn("px-4 py-2 rounded-lg max-w-[70%]", {
            "bg-primary text-primary-foreground": fromCurrentUser,
            "text-secondary-foreground": !fromCurrentUser,
            "rounded-br-none": fromCurrentUser,
          })}
        >
          <p className="text-wrap break-words whitespace-pre-wrap">
            {message?.messageContent}
          </p>
        </div>
      </div>

      <Avatar
        className={cn("relative w-8 h-8", {
          "order-2": fromCurrentUser,
          "order-0": !fromCurrentUser,
        })}
      >
        <AvatarImage src={sender?.imageUrl} alt={sender?.name} />
        <AvatarFallback>CN</AvatarFallback>
      </Avatar>
    </div>
  );
};

export default MessageItems;

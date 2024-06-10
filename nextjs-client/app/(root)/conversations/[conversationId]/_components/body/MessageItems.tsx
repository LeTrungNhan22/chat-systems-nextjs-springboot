import { AvatarImage } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import React from "react";

type Props = {};

const MessageItems = (props: Props) => {
  return (
    <div
      className={cn("flex items-center", {
        "justify-end": true,
      })}
    >
      <div
        className={cn("flex w-full mx-2", {
          "order-1 items-end": true,
          "order-1 items-start": !true,
        })}
      >
        <div
          className={cn("px-4 py-2 rounded-lg max-w-[70%]", {
            "text-foreground bg-primary": true,
            "text-background bg-secondary": !true,
            // "rounded-br-none": true,
          })}
        >
          <p className="text-wrap break-words whitespace-pre-wrap">
            This is UI test message
          </p>
        </div>
      </div>

      <Avatar className="relative w-8 h-8">
        <AvatarImage src="https://github.com/shadcn.png" alt="@shadcn" />
        <AvatarFallback>CN</AvatarFallback>
      </Avatar>
    </div>
  );
};

export default MessageItems;

import { AvatarImage } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import React from "react";

import { format } from "date-fns";
import Image from "next/image";
import { API_BASE_URL } from "@/constants";
import { Card } from "@/components/ui/card";

type Props = {
  message: any;
  currentUserId: string | undefined;
};

const MessageItems = ({ message, currentUserId }: Props) => {
  const { chat, sender, messageType } = message;
  const fromCurrentUser = currentUserId === sender?.id;
  const formattedDateToday = format(new Date(message.timestamp), "HH:mm");
  const formattedDate = format(new Date(message.timestamp), "dd/MM/yyyy HH:mm");
  const lastMessageByUser = chat?.lastMessageByUser?.sender?.id === sender?.id;

  console.log("message", message);
  console.log("currentUserId", fromCurrentUser);

  return (
    <div
      className={cn("flex items-end", {
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
          className={cn(
            "px-4 py-2 rounded-lg min-w-0 max-w-[70%] shadow-md break-words",
            {
              "bg-primary text-primary-foreground": fromCurrentUser,
              "bg-gradient text-secondary-foreground ": !fromCurrentUser,
              "rounded-br-none": !lastMessageByUser && fromCurrentUser,
              "rounded-bl-none": !lastMessageByUser && !fromCurrentUser,
            }
          )}
        >
          {messageType === "TEXT" ? (
            <p className="text-wrap break-words whitespace-pre-wrap">
              {message?.messageContent}
            </p>
          ) : messageType === "IMAGE" ? (
            <>
              <Card className="flex gap-2 p-2">
                {message?.mediaUrl && (
                  message?.mediaUrl.map((item: any) => (
                    <div key={item?.split("/").pop()} className="relative h-[100px] w-[100px]">
                      <Image
                        src={`${API_BASE_URL}${item}`}

                        alt={item}
                        fill
                        className="object-cover rounded-md"
                        placeholder="blur"
                        sizes="100px"
                        blurDataURL={`${API_BASE_URL}${item}`}

                      />
                    </div>

                  ))
                )}
              </Card>
            </>
          ) : (null)}
          <p
            className={cn("text-xs flex w-full my-1", {
              "text-primary-foreground justify-end": fromCurrentUser,
              "text-secondary-foreground justify-start": !fromCurrentUser,
            })}
          >
            {formattedDate}
          </p>
        </div>
      </div>

      <Avatar
        className={cn("relative w-8 h-8", {
          "order-2": fromCurrentUser,
          "order-0": !fromCurrentUser,
          invisible: lastMessageByUser,
        })}
      >
        <AvatarImage src={sender?.imageUrl} alt={sender?.name} />
        <AvatarFallback>CN</AvatarFallback>
      </Avatar>
    </div>
  );
};

export default MessageItems;

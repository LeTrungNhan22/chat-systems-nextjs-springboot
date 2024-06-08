"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import useFilteredConversations from "@/hooks/common/useFilteredConversations";
import Link from "next/link";
import React from "react";
type Props = {
  conversationsList: any | [];
  currentUserId: string | undefined;
};

const DMConversations = ({ conversationsList, currentUserId }: Props) => {
  const filteredConversationsList = useFilteredConversations(
    conversationsList,
    currentUserId
  );
  return (
    <>
      {filteredConversationsList.map((chat: any) => (
        <Link
          key={chat.id}
          href={`/conversations/${chat?.id}`}
          className="w-full"
        >
          {chat.participants.map((participant: any) => (
            <Card
              key={participant.id}
              className="p-2 flex flex-row items-center gap-4 truncate"
            >
              <div className="flex flex-row gap-4 items-center truncate">
                <Avatar>
                  <AvatarImage src={participant.imageUrl} />
                  <AvatarFallback>CN</AvatarFallback>
                </Avatar>
                <div className="flex flex-col truncate">
                  <h4 className="truncate">{participant.username}</h4>
                  <p className="text-sm text-muted-foreground truncate">
                    Nhắn tin ngay
                  </p>
                </div>
              </div>
            </Card>
          ))}
        </Link>
      ))}
    </>
  );
};

export default DMConversations;

"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import { HANDLE_ERROR, HANDLE_PENDING } from "@/constants";
import useFilteredConversations from "@/hooks/common/useFilteredConversations";
import Link from "next/link";
import React from "react";
type Props = {
  conversationsList: any | [];
  currentUserId: string | undefined;
  isError: boolean
  isLoading: boolean
};

const DMConversations = ({ conversationsList, currentUserId, isError,
  isLoading }: Props) => {
  const filteredConversationsList = useFilteredConversations(
    conversationsList,
    currentUserId
  );

  const filteredConversations = filteredConversationsList.filter(
    (chat: any) => chat.lastMessageByUser !== null
  );

  // console.log("filteredConversationsList", filteredConversationsList);
  // console.log("lastMessageByUser", filteredConversations);
  // console.log("fromCurrentUser", fromCurrentUser);


  {
    if (isLoading) return <p>{HANDLE_PENDING}</p>
  }
  {
    if (isError) return <p>{HANDLE_ERROR}</p>
  }

  return (
    <>
      {filteredConversations.length === 0 ? (
        <p className="text-center text-muted-foreground ">
          Bắt đầu cuộc trò chuyện với bạn bè của bạn
        </p>
      ) : (
        <>
          {filteredConversations.map((chat: any) => (
            <Link
              key={chat.id}
              href={`/conversations/${chat?.id}`}
              className="w-full"
            >
              {chat.participants.map((participant: any) => (
                <Card
                  key={participant.id}
                  className="p-2 flex flex-row items-center gap-4 truncate "
                >
                  <div className="flex flex-row gap-4 items-center truncate w-full">
                    <Avatar>
                      <AvatarImage src={participant.imageUrl} />
                      <AvatarFallback>CN</AvatarFallback>
                    </Avatar>
                    <div className="flex flex-col truncate w-full">
                      <h4 className="truncate">{participant.username}</h4>
                      <p className="text-sm text-muted-foreground truncate italic">
                        {chat.lastMessageByUser?.senderId.id === currentUserId
                          ? `${chat.lastMessageByUser?.content === null
                            ? `" Chưa có tin nhắn!!!"`
                            : (chat.lastMessageByUser?.type === "IMAGE"
                              ?
                              " Bạn đã gửi một hình ảnh"
                              : (chat.lastMessageByUser?.type === "FILE"
                                ? " Bạn đã gửi một tệp" :
                                `Bạn: ${chat.lastMessageByUser?.content}`))
                          }`
                          : `${chat.lastMessageByUser?.content === null
                            ? "Chưa có tin nhắn !!!"
                            : (
                              chat.lastMessageByUser?.type === "IMAGE"
                                ?
                                `${chat.lastMessageByUser?.senderId.target.username} : 
                                " Đã gửi một hình ảnh"`
                                : (chat.lastMessageByUser?.type === "FILE"
                                  ? " Đã gửi một tệp" :
                                  ` ${chat.lastMessageByUser?.senderId.target.username}:${chat.lastMessageByUser?.content}`)
                            )
                          }`}
                      </p>
                    </div>
                  </div>
                </Card>
              ))}
            </Link>
          ))}
        </>
      )}
    </>
  );
};

export default DMConversations;

"use client";
import ConversationContainer from "@/components/shared/conversation/ConversationContainer";
import React, { useContext, useEffect } from "react";
import Header from "./_components/Header";
import Body from "./_components/body/Body";
import ChatInput from "./_components/input/ChatInput";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import { useParams } from "next/navigation";
import { useGetMessagesByChatId } from "@/hooks/swr/message-api/useGetMessagesByChatId";
import { useWebSocket } from "../_context/context-websocket";

type Props = {};

const ConversationDetailPage = (props: Props) => {
  const user = useContext(AuthContext);
  const params = useParams();
  const currentUserId = user?.user?.user.id;
  const conversationId = params.conversationId;
  const { content, isLoading, isError } =
    useGetMessagesByChatId(conversationId);
  const { messages, sendMessage, setChatId } = useWebSocket(); // Lấy messages từ WebSocket context

  useEffect(() => {
    setChatId(conversationId as string);
  }, [conversationId]);



  const allMessages = [...(messages || []), ...(content || [])];
  console.log("messages", messages);
  console.log("content", content);
  console.log("allMessages", allMessages);
  

  return (
    <>
      <ConversationContainer>
        <Header currentUserId={currentUserId} conversationId={conversationId} />
        <Body
          currentUserId={currentUserId}
          content={allMessages}
          isLoading={isLoading}
          isError={isError}
        />
        <ChatInput
          handleSendMessage={sendMessage}
          conversationId={conversationId}
          currentUserId={currentUserId}
        />
      </ConversationContainer>
    </>
  );
};

export default ConversationDetailPage;

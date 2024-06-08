"use client";
import ConversationContainer from "@/components/shared/conversation/ConversationContainer";
import React, { useContext } from "react";
import Header from "./_components/Header";
import Body from "./_components/body/Body";
import ChatInput from "./_components/input/ChatInput";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";

type Props = {
  params: {
    conversationId: string;
  };
};

const ConversationDetailPage = (props: Props) => {
  const user = useContext(AuthContext);
  const currentUserId = user?.user?.user.id;
  
  return (
    <>
      <ConversationContainer>
        <Header currentUserId={currentUserId} />
        <Body />
        <ChatInput />
      </ConversationContainer>
    </>
  );
};

export default ConversationDetailPage;

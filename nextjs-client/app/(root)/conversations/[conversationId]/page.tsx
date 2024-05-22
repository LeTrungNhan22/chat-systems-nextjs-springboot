"use client";
import ConversationContainer from "@/components/shared/conversation/ConversationContainer";
import React from "react";
import Header from "./_components/Header";
import Body from "./_components/body/Body";
import ChatInput from "./_components/input/ChatInput";

type Props = {
  params: {
    conversationId: string;
  };
};

const ConversationDetailPage = (props: Props) => {
  return (
    <>
      <ConversationContainer>
        <Header name={""} imageUrl ={""}/>
        <Body/>
        <ChatInput/>
      </ConversationContainer>
    </>
  );
};

export default ConversationDetailPage;

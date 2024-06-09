"use client";
import ConversationContainer from "@/components/shared/conversation/ConversationContainer";
import React, { useContext } from "react";
import Header from "./_components/Header";
import Body from "./_components/body/Body";
import ChatInput from "./_components/input/ChatInput";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import { useParams } from "next/navigation";

type Props = {};

const ConversationDetailPage = (props: Props) => {
  const user = useContext(AuthContext);
  const params = useParams();
  const currentUserId = user?.user?.user.id;
  const conversationId = params.conversationId;

  return (
    <>
      <ConversationContainer>
        <Header currentUserId={currentUserId} conversationId={conversationId} />
        <Body />
        <ChatInput />
      </ConversationContainer>
    </>
  );
};

export default ConversationDetailPage;

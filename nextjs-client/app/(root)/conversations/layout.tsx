"use client";
import React from "react";
import ItemList from "@/components/item-list/ItemList";
import { Separator } from "@/components/ui/separator";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import { HANDLE_ERROR, HANDLE_PENDING } from "@/constants";
import { useListConversationsByUseId } from "@/hooks/swr/conversation-api/useListConversationByUserId";
import DMConversations from "./_components/DMConversations";
import { WebSocketProvider } from "./_context/context-websocket";

type Props = React.PropsWithChildren<{}>;

const ConversationLayout = ({ children }: Props) => {
  const user = React.useContext(AuthContext);
  const currentUserId = user?.user?.user.id;
  const { data, isError, isLoading } =  useListConversationsByUseId(currentUserId);
  

  return (
    <>
      <WebSocketProvider>
        <ItemList title="Cuộc trò chuyện">
          <Separator className="my-2" />
          {data && (
            <DMConversations
              conversationsList={data}
              currentUserId={currentUserId}
              isError={isError}
              isLoading={isLoading}
            />
          )}
        </ItemList>
        {children}
      </WebSocketProvider>
    </>
  );
};

export default ConversationLayout;

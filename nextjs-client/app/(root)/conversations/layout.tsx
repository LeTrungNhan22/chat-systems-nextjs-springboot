import ItemList from "@/components/item-list/ItemList";
import React from "react";
import DMConversations from "./_components/DMConversations";
import { WebSocketProvider } from "@/context/websocket/contextWebsocket";

type Props = React.PropsWithChildren<{}>;

const ConversationLayout = ({ children }: Props) => {
  return (
    <>
      <ItemList title="Conversations">
        <WebSocketProvider>
          <DMConversations />
        </WebSocketProvider>
      </ItemList>
      {children}
    </>
  );
};

export default ConversationLayout;

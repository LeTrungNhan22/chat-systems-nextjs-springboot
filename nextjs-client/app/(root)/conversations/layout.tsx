import ItemList from "@/components/item-list/ItemList";
import React from "react";
import DMConversations from "./_components/DMConversations";
import { WebSocketProvider } from "@/context/websocket/contextWebsocket";
import { Separator } from "@/components/ui/separator";

type Props = React.PropsWithChildren<{}>;

const ConversationLayout = ({ children }: Props) => {
  return (
    <>
      <ItemList title="Cuộc trò chuyện">
        <Separator className="my-2" />
        <WebSocketProvider>
          <DMConversations />
        </WebSocketProvider>
      </ItemList>
      {children}
    </>
  );
};

export default ConversationLayout;

import ItemList from "@/components/item-list/ItemList";
import React from "react";
import DMConversations from "./_components/DMConversations";

type Props = React.PropsWithChildren<{}>;

const ConversationLayout = ({ children }: Props) => {
  return (
    <>
      <ItemList title="Conversations">
        <DMConversations />
      </ItemList>
      {children}
    </>
  );
};

export default ConversationLayout;

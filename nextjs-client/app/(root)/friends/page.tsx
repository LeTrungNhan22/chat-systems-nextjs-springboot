import ItemList from "@/components/item-list/ItemList";
import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import React from "react";

type Props = {};

const FriendPage = (props: Props) => {
  return (
    <>
      <ItemList title="Friends"> Friends Page </ItemList>
      <ConversationFallback />
    </>
  );
};

export default FriendPage;

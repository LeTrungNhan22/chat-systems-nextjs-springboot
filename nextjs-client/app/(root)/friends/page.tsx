import ItemList from "@/components/item-list/ItemList";
import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import React from "react";
import AddFriendDialog from "./_components/AddFriendDialog";
import RequestFriend from "./_components/RequestFriend";

type Props = {

};

const FriendPage = (props: Props) => {
  return (
    <>
      <ItemList action={<AddFriendDialog />} title="Friends">
        <RequestFriend />
      </ItemList>
      <ConversationFallback />
    </>
  );
};

export default FriendPage;

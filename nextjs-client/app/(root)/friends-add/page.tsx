import ItemList from "@/components/item-list/ItemList";
import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import React from "react";
import AddFriendDialog from "./_components/AddFriendDialog";
import FriendRequestSent from "./_components/FriendsRequestSent";
import { Separator } from "@/components/ui/separator";


type Props = {};

const FriendRequestSend = (props: Props) => {
  return (
    <>
      <ItemList action={<AddFriendDialog />} title="Yêu cầu đã gửi">
        <Separator className="my-2" />
        <FriendRequestSent />
      </ItemList>
      <ConversationFallback />
    </>
  );
};

export default FriendRequestSend;

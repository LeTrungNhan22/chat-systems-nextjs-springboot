import ItemList from "@/components/item-list/ItemList";
import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import React from "react";
import FriendRequestReceived from "./_components/FriendsRequestReceived";
import { Separator } from "@/components/ui/separator";
import AddFriendDialog from "../friends-request-sent/_components/AddFriendDialog";

type Props = {};

const FriendPage = (props: Props) => {
  return (
    <>
      <ItemList  title="Danh sách bạn bè">
      <Separator className="my-2" />
        <FriendRequestReceived />
      </ItemList>
      <ConversationFallback />
    </>
  );
};

export default FriendPage;

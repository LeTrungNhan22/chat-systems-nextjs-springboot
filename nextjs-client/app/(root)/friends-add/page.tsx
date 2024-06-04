"use client";
import ItemList from "@/components/item-list/ItemList";
import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import React, { useContext } from "react";
import AddFriendDialog from "./_components/AddFriendDialog";
import FriendRequestSent from "./_components/FriendsRequestSent";
import { Separator } from "@/components/ui/separator";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";

const FriendRequestSend = () => {
  const { user } = useContext(AuthContext);
  return (
    <>
      <ItemList action={<AddFriendDialog />} title="Yêu cầu đã gửi">
        <Separator className="my-2" />
        <FriendRequestSent user={user} />
      </ItemList>
      <ConversationFallback />
    </>
  );
};

export default FriendRequestSend;

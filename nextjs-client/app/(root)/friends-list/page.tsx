"use client";
import React, { useContext } from "react";

import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import ItemList from "@/components/item-list/ItemList";
import { Separator } from "@/components/ui/separator";
import FriendRequestReceived from "./_components/FriendsRequestReceived";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import RequestFriendContainer from "@/components/shared/friend/FriendRequestContainer";

const FriendPage = () => {
  const { user } = useContext(AuthContext);

  return (
    <>
      <ItemList title="Danh sách bạn bè">
        <Separator className="my-2" />
      </ItemList>
      <RequestFriendContainer>
        <FriendRequestReceived user={user} />
      </RequestFriendContainer>
    </>
  );
};

export default FriendPage;

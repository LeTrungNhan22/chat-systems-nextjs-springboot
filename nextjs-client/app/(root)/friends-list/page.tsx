"use client";
import React, { useContext } from "react";
import ItemList from "@/components/item-list/ItemList";
import { Separator } from "@/components/ui/separator";
import FriendRequestReceived from "./_components/FriendsRequestReceived";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import FriendRequestCardReceivedCardList from "./_components/FriendRequestCardReceivedCardList";
import RequestFriendContainer from "@/components/shared/friend/FriendRequestContainer";

const FriendPage = () => {
  const { user } = useContext(AuthContext);

  return (
    <RequestFriendContainer>
      <ItemList title="Danh sách bạn bè">
        <Separator className="my-2" />
        <FriendRequestReceived user={user} />
      </ItemList>
      <FriendRequestCardReceivedCardList />
    </RequestFriendContainer>
  );
};

export default FriendPage;

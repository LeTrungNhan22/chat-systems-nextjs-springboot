"use client";
import React, { useContext } from "react";
import ItemList from "@/components/item-list/ItemList";
import { Separator } from "@/components/ui/separator";
import FriendshipContainer from "./_components/FriendshipListContainer";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import FriendRequestReceivedCardList from "./_components/FriendRequestReceivedCardList";
import RequestFriendContainer from "@/components/shared/friend/FriendRequestContainer";
import FriendshipListContainer from "./_components/FriendshipListContainer";
import FriendshipListItems from "./_components/FriendshipListItems";
import FriendRequestFallback from "@/components/shared/friend/FriendRequestFallback";

const FriendPage = () => {
  const { user } = useContext(AuthContext);
  return (
    <RequestFriendContainer>
      <ItemList title="Danh sách bạn bè">
        <Separator className="my-2" />
        <FriendshipListContainer user={user} />
      </ItemList>
      <FriendRequestReceivedCardList user={user} />
    </RequestFriendContainer>
  );
};

export default FriendPage;

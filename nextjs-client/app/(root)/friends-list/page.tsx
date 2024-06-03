'use client'
import React, { useContext } from "react";

import ConversationFallback from "@/components/shared/conversation/ConversationFallback";
import ItemList from "@/components/item-list/ItemList";
import { Separator } from "@/components/ui/separator";
import FriendRequestReceived from "./_components/FriendsRequestReceived";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import { AuthUser } from "@/utils/types/users/auth";

const FriendPage = () => {
  const { user, setUser, isAuthenticated, isLoading, logOutUser } =
    useContext(AuthContext);
  return (
    <>
      <ItemList title="Danh sách bạn bè">
        <Separator className="my-2" />
        <FriendRequestReceived user={user} />
      </ItemList>
      <ConversationFallback />
    </>
  );
};

export default FriendPage;

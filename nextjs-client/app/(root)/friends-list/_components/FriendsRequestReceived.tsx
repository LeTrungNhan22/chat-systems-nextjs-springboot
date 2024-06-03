"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Check, X } from "lucide-react";
import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import { API_BASE_URL } from "@/constants";
import useFriendRequests from "@/hooks/swr/useFriendRequests";
import { AuthUser, TUser } from "@/utils/types/users/auth";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";
import useSWR from "swr";
import FriendRequestCard from "./FriendRequestCard";

type Props = React.PropsWithChildren<{
  user: AuthUser | null;
}>;
function FriendRequestReceived({ user }: Props) {
  const { receivedRequests, isLoading, isError, mutateReceived } =
    useFriendRequests(user?.user.id);

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error fetching friend requests</div>;

  return (
    <>
      {receivedRequests.length > 0 ? (
        receivedRequests.map((request) => (
          <FriendRequestCard
            key={request.id}
            request={request}
            mutateReceived={mutateReceived}
          />
        ))
      ) : (
        <div>không có lời mời kết bạn mới</div>
      )}
    </>
  );
}

export default FriendRequestReceived;

"use client";

import React from "react";

import useFriendRequests from "@/hooks/swr/useFriendRequests";
import { AuthUser } from "@/utils/types/users/auth";
import FriendRequestCardReceived from "./FriendRequestCardReceived";

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
          <FriendRequestCardReceived
            key={request.id}
            request={request}
            mutateReceived={mutateReceived}
            currentUserId={user?.user.id}
          />
        ))
      ) : (
        <>
          <div className="text-center text-gray-500">
            Chưa có lời mời kết bạn nào
          </div>
        </>
      )}
    </>
  );
}

export default FriendRequestReceived;

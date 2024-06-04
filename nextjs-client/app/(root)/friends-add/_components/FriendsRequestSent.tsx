"use client";

import React from "react";
import useFriendRequests from "@/hooks/swr/useFriendRequests";
import { AuthUser } from "@/utils/types/users/auth";
import FriendRequestCardSent from "./FriendRequestCardSent";

type Props = React.PropsWithChildren<{
  user: AuthUser | null;
}>;

function FriendsRequestSent({ user }: Props) {
  const { sentRequests, isLoading, isError, mutateSent } = useFriendRequests(
    user?.user.id
  );
  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error fetching friend requests</div>;

  return (
    <>
      {sentRequests.length > 0 ? (
        sentRequests.map((request) => (
          <FriendRequestCardSent
            key={request.id}
            request={request}
            mutateSent={mutateSent}
          />
        ))
      ) : (
        <div>Không có lời mời kết bạn mới</div>
      )}
    </>
  );
}

export default FriendsRequestSent;

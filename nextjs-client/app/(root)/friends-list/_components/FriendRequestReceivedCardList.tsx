import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import useFriendRequests from "@/hooks/swr/friend-api/useFriendRequests";
import { AuthUser } from "@/utils/types/users/auth";
import React from "react";
import FriendRequestReceivedCardItem from "./FriendRequestReceivedCardItem";
import FriendRequestFallback from "@/components/shared/friend/FriendRequestFallback";
import FriendRequestFallbackWrapper from "@/components/shared/friend/FriendRequestFallbackWrapper";

type Props = React.PropsWithChildren<{
  user: AuthUser | null;
}>;

const FriendRequestReceivedCardList = ({ user }: Props) => {
  const { receivedRequests, isLoading, isError, mutateReceived } =
    useFriendRequests(user?.user.id);

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error fetching friend requests</div>;
  return (
    <React.Fragment key={receivedRequests.length > 0 ? "list" : "fallback"}>
      {receivedRequests.length > 0 ? (
        <div
          className="flex flex-row gap-3 overflow-x-auto w-full h-auto  no-scrollbar
                          lg:grid lg:grid-cols-3 lg:overflow-y-auto 
                          xl:grid xl:grid-cols-4 xl:overflow-y-auto
                          2xl:grid 2xl:grid-cols-5 2xl:overflow-y-auto"
        >
          {receivedRequests.map((request) => (
            <FriendRequestReceivedCardItem
              key={request.id}
              request={request}
              mutateReceived={mutateReceived}
              currentUserId={user?.user.id}
            />
          ))}
        </div>
      ) : (
        <FriendRequestFallback />
      )}
    </React.Fragment>
  );
};

export default FriendRequestReceivedCardList;

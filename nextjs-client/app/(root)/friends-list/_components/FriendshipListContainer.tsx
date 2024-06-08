"use client";
import React from "react";

import useFriendRequests from "@/hooks/swr/friend-api/useFriendRequests";
import { AuthUser } from "@/utils/types/users/auth";
import FriendshipList from "./FriendshipListItems";
import FriendshipListItems from "./FriendshipListItems";
import useGetListFriend from "@/hooks/swr/friend-api/useGetListFriend";

type Props = React.PropsWithChildren<{
  user: AuthUser | null;
}>;

function FriendshipListContainer({ user }: Props) {
  const { listFriend, isLoading, isError, mutateCurrentList } =
    useGetListFriend(user?.user.id);

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error fetching friend requests</div>;

  return (
    <div className="w-full h-[200px] lg:h-full flex flex-col overflow-y-auto gap-2">
      {listFriend.length > 0 ? (
        listFriend.map((request) => (
          <FriendshipListItems
            key={request.id}
            request={request}
            mutateCurrentList={mutateCurrentList}
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
    </div>
  );
}

export default FriendshipListContainer;

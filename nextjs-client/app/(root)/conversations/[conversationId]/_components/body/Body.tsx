import React, { memo } from "react";
import MessageItems from "./MessageItems";
import { HANDLE_ERROR, HANDLE_PENDING } from "@/constants";
import LoadingLogo from "@/components/shared/LoadingLogo";
import SkeletonMessageItems from "./SkeletonMessageItems";

type Props = {
  currentUserId: string | undefined;
  content: any[];
  isLoading: boolean;
  isError: boolean;
};

const Body = ({ currentUserId, content, isLoading, isError }: Props) => {
  if (isLoading) {
    return  <LoadingLogo />;
  }

  if (isError) {
    return <div>{HANDLE_ERROR}</div>;
  }

  // console.log("messagesList", content);

  return (
    <div className="flex-1 flex-col-reverse flex w-full overflow-y-scroll gap-2 p-3 no-scrollbar">
      {content.length !== 0 &&
        content?.map((message: any) => (
          <MessageItems
            key={message.id}
            message={message}
            currentUserId={currentUserId}
          />
        ))}
    </div>
  );
};

export default Body;

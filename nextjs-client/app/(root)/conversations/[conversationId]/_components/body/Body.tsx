import React from "react";
import MessageItems from "./MessageItems";
import { useGetMessagesByChatId } from "@/hooks/swr/message-api/useGetMessagesByChatId";
import { HANDLE_ERROR, HANDLE_PENDING } from "@/constants";

type Props = {
  currentUserId: string | undefined;
  conversationId: string | string[];
};

const Body = ({ currentUserId, conversationId }: Props) => {
  const { content, isLoading, isError } =
    useGetMessagesByChatId(conversationId);

  {
    isLoading && <h1>{HANDLE_PENDING}</h1>;
  }
  {
    isError && <h1>{HANDLE_ERROR}</h1>;
  }
  console.log("messagesList", content);

  return (
    <div className="flex-1 flex-col-reverse flex w-full overflow-y-scroll gap-2 p-3 no-scrollbar">
      {content?.map((message: any) => (
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

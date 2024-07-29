import React from "react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import { DotsHorizontalIcon } from "@radix-ui/react-icons";
import { useCreateConversations } from "@/hooks/swr/conversation-api/useCreateConversations";

import { useListConversationsByUseId } from "@/hooks/swr/conversation-api/useListConversationByUserId";
import { useRouter } from "next-nprogress-bar";

type Props = {
  currentUserId: string | undefined;
  otherUserId: any;
};

const FriendshipAction = ({ currentUserId, otherUserId }: Props) => {
  const { createConversation } = useCreateConversations(
    currentUserId,
    otherUserId
  );
  const { mutateListConversations } =
    useListConversationsByUseId(currentUserId);
  const navigation = useRouter();

  const handleCreateConversation = async () => {
    try {
      const data = await createConversation();
      if (data) {
        mutateListConversations();
        navigation.push(`/conversations/${data.id}`);
      }
    } catch (err: any) {
      console.log("Error creating conversation: ", err.message);
    }
  };

  return (
    <>
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="secondary">
            <DotsHorizontalIcon className="h-4 w-4" />
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="start" side="right">
          <DropdownMenuItem onClick={handleCreateConversation}>
            Nhắn tin
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem className="text-red-600">
            Xóa bạn bè
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </>
  );
};

export default FriendshipAction;

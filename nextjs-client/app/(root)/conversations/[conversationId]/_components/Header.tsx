import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import { useConversationByChatId } from "@/hooks/swr/conversation-api/useConversationByChatId";
import { Circle, CircleArrowLeft } from "lucide-react";
import Link from "next/link";
import { useParams } from "next/navigation";
import React, { useEffect } from "react";
import SkeletonHeader from "./SkeletonHeader";

type Props = {
  currentUserId: string | undefined;
  conversationId: string | string[];
};

const Header = ({ currentUserId, conversationId }: Props) => {
  const { data, isLoading, isError } = useConversationByChatId(conversationId);
  const otherUserInfo = data?.participants.find(
    (user: any) => user.id !== currentUserId
  );
  {
    isLoading && <SkeletonHeader />;
  }

  {
    isError && <h1>Something went wrong</h1>;
  }

  return (
    <Card className="w-full rounded-lg flex items-center p-2 justify-between">
      <div className="flex items-center gap-2">
        <Link href="/conversations" className="block lg:hidden">
          <CircleArrowLeft />
        </Link>
        <Avatar className="w-8 h-8">
          <AvatarImage src={otherUserInfo.imageUrl} />
          <AvatarFallback>CN</AvatarFallback>
        </Avatar>
        <h2 className="font-semibold">{otherUserInfo.username}</h2>
        
      </div>
    </Card>
  );
};

export default Header;

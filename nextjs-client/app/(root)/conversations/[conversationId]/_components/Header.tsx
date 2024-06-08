import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import { useConversationByChatId } from "@/hooks/swr/conversation-api/useConversationByChatId";
import { Circle, CircleArrowLeft } from "lucide-react";
import Link from "next/link";
import { useParams } from "next/navigation";
import React, { useEffect } from "react";

type Props = {
  currentUserId: string | undefined;
};

const Header = ({ currentUserId: any }: Props) => {
  const params = useParams();
  const chatId = params.conversationId;
  // const chatInfo = useConversationByChatId(chatId);

  return (
    <Card className="w-full rounded-lg flex items-center p-2 justify-between">
      <div className="flex items-center gap-2">
        <Link href="/conversations" className="block lg:hidden">
          <CircleArrowLeft />
        </Link>
        <Avatar className="w-8 h-8">
          <AvatarImage src="https://github.com/shadcn.png" />
          <AvatarFallback>CN</AvatarFallback>
        </Avatar>
        <h2 className="font-semibold">Username</h2>
      </div>
    </Card>
  );
};

export default Header;

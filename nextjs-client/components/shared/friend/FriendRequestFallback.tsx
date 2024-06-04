import { Card } from "@/components/ui/card";
import React from "react";

type Props = {};

function FriendRequestFallback({}: Props) {
  return (
    <Card className="hidden lg:flex h-full w-full items-center justify-center bg-secondary text-secondary-foreground">
      Chưa có yêu cầu kết bạn nào!!
    </Card>
  );
}

export default FriendRequestFallback;

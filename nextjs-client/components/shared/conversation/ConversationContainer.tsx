"use client";
import { Card } from "@/components/ui/card";
import React from "react";

type Props = React.PropsWithChildren<{}>;

function ConversationContainer({ children }: Props) {
  return (
    <Card
      className="flex-1 
                  lg:h-full
                  w-full  h-[calc(100vh-32px)] p-2 flex flex-col gap-2"
    >
      {children}
    </Card>
  );
}

export default ConversationContainer;

// css:
// 2xl:max-w-[73%]
// xl:max-w-[67%]
// lg:max-w-[60%]

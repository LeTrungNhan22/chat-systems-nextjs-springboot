"use client";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
let stompClient: any;

type Props = {};

const DMConversations = (props: Props) => {
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(() => socket);

    stompClient.connect({}, () => {
      stompClient.subscribe("/topic/greetings", (message: any) => {
        console.log("Message from API:", message.body); // In trực tiếp message.body
      });

      const testMessage = { message: "Hello from test!" };
      stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify(testMessage),
      });
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);
  return (
    <Link href={"/conversations/1"} className="w-full">
      <Card className="p-2 flex flex-row items-center gap-4 truncate">
        <div className="flex flex-row gap-4 items-center truncate">
          <Avatar>
            <AvatarImage src="https://github.com/shadcn.png" />
            <AvatarFallback>CN</AvatarFallback>
          </Avatar>
          <div className="flex flex-col truncate">
            <h4 className="truncate">Username</h4>
            <p className="text-sm text-muted-foreground truncate">
              Nhắn tin ngay
            </p>
          </div>
        </div>
      </Card>
    </Link>
  );
};

export default DMConversations;

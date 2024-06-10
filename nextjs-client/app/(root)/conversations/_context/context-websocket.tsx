"use client";
import { MessageRequest } from "@/app/(root)/conversations/_types/MessageRequest";
import { ACCESS_TOKEN, WEBSOCKET_ENDPOINT } from "@/constants";
import { createContext, useContext, useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { useCookies } from "next-client-cookies";

interface WebSocketContextValue {
  messages: MessageRequest[];
  sendMessage: (message: MessageRequest, conversationId: string) => void;
  setChatId: (chatId: string) => void;
}

const WebSocketContext = createContext<WebSocketContextValue | undefined>(
  undefined
);

export const WebSocketProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [messages, setMessages] = useState<MessageRequest[]>([]);
  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [chatId, setChatId] = useState<string | null>(null);

  const storage = useCookies();

  useEffect(() => {
    const socket = new SockJS(WEBSOCKET_ENDPOINT);
    const client = new Client({
      brokerURL: WEBSOCKET_ENDPOINT,
      webSocketFactory: () => socket,
      debug: (str) => {
        console.log(str);
      },
      heartbeatIncoming: 30000,
      heartbeatOutgoing: 30000,
    });

    client.onConnect = (frame) => {
      console.log("Connected to STOMP " + frame);
      if (stompClient && chatId) {
        client.subscribe(`/topic/chats/${chatId}`, (message) => {
          const newMessageResponse = JSON.parse(message.body);
          setMessages((prev) => [...prev, newMessageResponse]);
        });
      }
    };

    client.onStompError = (frame) => {
      console.error("Broker reported error: " + frame.headers["message"]);
      console.error("Additional details: " + frame.body);
    };

    client.activate(); // Kích hoạt kết nối Stomp

    setStompClient(client);

    return () => {
      if (client) {
        client.deactivate(); // Ngắt kết nối Stomp khi component unmount
      }
    };
  }, [chatId]);

  console.log("chatId", chatId);

  const sendMessage = (messageRequest: MessageRequest, chatId: string) => {
    if (stompClient) {
      const token = storage.get(ACCESS_TOKEN);
      stompClient.publish({
        destination: `/app/chats/${chatId}/sendMessage`,
        body: JSON.stringify(messageRequest),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("message sent", messageRequest);
      console.log("chatId", chatId);
    }
  };

  return (
    <WebSocketContext.Provider value={{ messages, setChatId, sendMessage }}>
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => {
  const context = useContext(WebSocketContext);
  if (!context) {
    throw new Error("useWebSocket must be used within a WebSocketProvider");
  }
  return context;
};

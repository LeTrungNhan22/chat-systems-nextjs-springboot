"use client";
import { createContext, useContext, useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { useCookies } from "next-client-cookies";

import getMessageById from "@/hooks/api/getMessageById";
import { ACCESS_TOKEN, WEBSOCKET_ENDPOINT } from "@/constants";

interface WebSocketContextValue {
  messages: any[];
  setMessages: (messages: any[]) => void;
  sendMessage: (message: any, conversationId: string) => void;
  setChatId: (chatId: string) => void;
  reconnectWs: () => void;
}

const WebSocketContext = createContext<WebSocketContextValue | undefined>(
  undefined
);

export const WebSocketProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [messages, setMessages] = useState<any[]>([]);
  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [chatId, setChatId] = useState<string | null>(null);

  const storage = useCookies();

  useEffect(() => {
    const socket = new SockJS(WEBSOCKET_ENDPOINT);
    const client = new Client({
      brokerURL: WEBSOCKET_ENDPOINT,
      webSocketFactory: () => socket,
      connectHeaders: {
        Authorization: `Bearer ${storage.get(ACCESS_TOKEN)}`,
      },
      debug: (str) => {
        console.log(str);
      },
      heartbeatIncoming: 20000,
      heartbeatOutgoing: 20000,
      maxWebSocketChunkSize: 1024 * 1024 * 10, // 10MB
      reconnectDelay: 3000,
      splitLargeFrames: true,

    });

    client.onConnect = (frame) => {
      console.log("Connected to STOMP " + frame);
      setMessages([]); // Reset messages khi kết nối lại WebSocket
      if (stompClient && chatId) {
        client.subscribe(`/topic/chats/${chatId}`, (message) => {
          const newMessageResponse = JSON.parse(message.body);
          console.log("Received message: ", newMessageResponse);
          fetchAndAddMessage(newMessageResponse.messageId);
        });
      }
    };

    client.onStompError = (frame) => {
      if (frame.headers['message'] === 'Payload exceeded') {
        // Xử lý khi tin nhắn vượt quá giới hạn
        console.error('Message size exceeded limit');
      } else {
        // Xử lý các lỗi khác
        console.error("Broker reported error: " + frame.headers["message"]);
        console.error("Additional details: " + frame.body);
      }
    };

    client.activate(); // Kích hoạt kết nối Stomp

    setStompClient(client);

    return () => {
      if (client) {
        client.deactivate(); // Ngắt kết nối Stomp khi component unmount
      }
    };
  }, [chatId]);

  async function fetchAndAddMessage(messageId: string) {
    try {
      const messageFromApi = await getMessageById(messageId); // Hàm này sẽ gọi API của bạn để lấy chi tiết tin nhắn
      setMessages((prevMessages) => {
        return [...prevMessages, messageFromApi];
      });
    } catch (error) {
      console.error("Error fetching message:", error); // Xử lý lỗi nếu có
    }
  }

  const sendMessage = (messageRequest: any, chatId: string) => {
    if (stompClient) {
      console.info("send to chat id :", chatId);
      const token = storage.get(ACCESS_TOKEN);
      stompClient.publish({
        destination: `/app/chats/${chatId}/sendMessage`,
        body: JSON.stringify(messageRequest),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }

      );
    }
  };

  const reconnectWs = () => {
    if (stompClient) {
      stompClient.deactivate();
      stompClient.activate();
    }
  };

  return (
    <WebSocketContext.Provider
      value={{ messages, setChatId, sendMessage, setMessages, reconnectWs }}
    >
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

"use client";
import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { useCookies } from "next-client-cookies";

import { ACCESS_TOKEN, WEBSOCKET_ENDPOINT } from "@/constants";
import getMessageById from "@/hooks/api/getMessageById";

const WebSocketClient: React.FC<{
  conversationId: string | string[];
  setMessages: React.Dispatch<React.SetStateAction<any[]>>;
}> = ({ conversationId, setMessages }) => {
  const [stompClient, setStompClient] = useState<Client | null>(null);
  const storage = useCookies();

  useEffect(() => {
    const socket = new SockJS(WEBSOCKET_ENDPOINT);
    const client = new Client({
      brokerURL: WEBSOCKET_ENDPOINT,
      webSocketFactory: () => socket,
      debug: (str) => console.log(str),
      heartbeatIncoming: 30000,
      heartbeatOutgoing: 30000,
      connectHeaders: {
        Authorization: `Bearer ${storage.get(ACCESS_TOKEN)}`,
      },
    });

    client.onConnect = (frame) => {
      console.log("Connected to STOMP " + frame);
      client.subscribe(`/topic/chats/${conversationId}`, (message) => {
        const newMessageResponse: { messageId: string } = JSON.parse(
          message.body
        );
        fetchAndAddMessage(newMessageResponse.messageId);
      });
    };

    client.onStompError = (frame) => {
      console.error("Broker reported error: " + frame.headers["message"]);
      console.error("Additional details: " + frame.body);
    };

    client.activate();
    setStompClient(client);

    return () => {
      if (client) {
        client.deactivate();
      }
    };
  }, [conversationId]);

  const fetchAndAddMessage = async (messageId: string) => {
    try {
      const messageFromApi = await getMessageById(messageId);
      setMessages((prev) => [messageFromApi, ...prev]);
    } catch (error) {
      console.error("Error fetching message:", error);
    }
  };

  const sendMessage = (messageRequest: any, chatId: string) => {
    if (stompClient) {
      const token = storage.get(ACCESS_TOKEN);
      stompClient.publish({
        destination: `/app/chats/${chatId}/sendMessage`,
        body: JSON.stringify(messageRequest),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
  };

  return null;
};

export default WebSocketClient;

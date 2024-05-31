"use client";
import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  useRef,
} from "react";
import { Client, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { WEBSOCKET_ENDPOINT } from "@/constants";
import { MessageDto } from "@/utils/types/messages/messageDto";

interface WebSocketContextValue {
  sendMessage: (chatId: string, messageDto: MessageDto) => void;
  // forwardMessage: (chatId: string, messageDto: ChatDto.MessageDto) => void;
  // markMessageAsRead: (chatId: string, messageId: string) => void;
}

const WebSocketContext = createContext<WebSocketContextValue>({
  sendMessage: () => {},
  // forwardMessage: () => {},
  // markMessageAsRead: () => {},
});

let stompClient: any;

export const WebSocketProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const [isConnected, setIsConnected] = useState(false); // flag to check

  useEffect(() => {
    const socket = new SockJS(WEBSOCKET_ENDPOINT);
    stompClient = Stomp.over(() => socket);

    stompClient.connect({}, () => {
      setIsConnected(true);
    });

    return () => {
      stompClient.disconnect();
      setIsConnected(false);
    };
  }, []);

  const sendMessage = (chatId: string, messageDto: MessageDto) => {
    if (isConnected) {
      stompClient.send(
        `/app/${chatId}/sendMessage`,
        {},
        JSON.stringify(messageDto)
      );
    }
  };

  // const forwardMessage = (chatId: string, messageDto: ChatDto.MessageDto) => {
  //   if (stompClientRef.current?.connected) {
  //     stompClientRef.current.send(`/app/chat/${chatId}/forwardMessage`, {}, JSON.stringify(messageDto));
  //   }
  // };

  // const markMessageAsRead = (chatId: string, messageId: string) => {
  //   if (stompClientRef.current?.connected) {
  //     stompClientRef.current.send(`/app/chat/${chatId}/messages/${messageId}/read`);
  //   }
  // };

  return (
    <WebSocketContext.Provider
      value={{
        sendMessage,
        //  forwardMessage,
        //   markMessageAsRead,
      }}
    >
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => {
  return useContext(WebSocketContext);
};

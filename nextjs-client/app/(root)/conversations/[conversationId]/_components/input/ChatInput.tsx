"use client";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form";
import { SendHorizonal } from "lucide-react";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import TextareaAutosize from "react-textarea-autosize";

type Props = {};

const ChatInput = (props: Props) => {
  const form = useForm();
  const { isLoading } = form.formState;
  const [count, setCount] = React.useState(0);

  //handler websocket
  const [messages, setMessages] = useState<string[]>([]);
  const [messageInput, setMessageInput] = useState<string>("");
  const [ws, setWs] = useState<WebSocket | null>(null);

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:8080/ws/websocket");
    setWs(ws);

    ws.onopen = () => {
      console.log("Connected to WebSocket server");
    };

    ws.onmessage = (event) => {
      const newMessage = event.data;
      setMessages((prevMessages) => [...prevMessages, newMessage]);
    };

    ws.onclose = (event) => {
      console.error("WebSocket connection closed:", event);
    };

    ws.onerror = (event) => {
      console.error("WebSocket error:", event);
    };

    return () => {
      ws.close();
    };
  }, []);
  // end

  const sendMessage = () => {
    if (ws && ws.readyState === WebSocket.OPEN) {
      ws.send(messageInput);
      setMessageInput("");
    } else {
      console.error("WebSocket connection is not open yet.");
    }
  };
  const fetchStomp = (e: any) => {
    e.preventDefault();
    sendMessage();
    setCount(count + 1);
    console.log("clicked", count);
  };

  return (
    <Card className="w-full p-2 rounded-lg relative">
      <div className="flex gap-2 items-end w-full">
        <Form {...form}>
          <form className="flex gap-2 items-end w-full">
            <FormField
              name="content"
              control={form.control}
              render={({ field }) => {
                return (
                  <FormItem className="h-full w-full">
                    <FormControl>
                      <TextareaAutosize
                        className="min-h-full w-full border-0 outline-0 resize-none bg-card text-card-foreground placeholder:text-muted-foreground p-1.5"
                        rows={1}
                        maxRows={3}
                        {...field}
                        placeholder="Type a message..."
                      />
                    </FormControl>
                  </FormItem>
                );
              }}
            />
            <Button onClick={fetchStomp} size="icon">
              <SendHorizonal />
            </Button>
          </form>
        </Form>
      </div>
    </Card>
  );
};

export default ChatInput;

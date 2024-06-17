"use client";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form";
import { SendHorizonal } from "lucide-react";
import React from "react";
import { useForm } from "react-hook-form";
import TextareaAutosize from "react-textarea-autosize";
import { MessageRequest } from "../../../_types/MessageTypes";
import { useWebSocket } from "../../../_context/context-websocket";
import { useListConversationsByUseId } from "@/hooks/swr/conversation-api/useListConversationByUserId";

type Props = {
  handleSendMessage: (message: MessageRequest, conversationId: string) => void; // Thêm handleSendMessage vào Props
  conversationId: string | string[];
  currentUserId: string | undefined;
};

const ChatInput = ({
  handleSendMessage,
  conversationId,
  currentUserId,
}: Props) => {
  const form = useForm();
  const { mutateListConversations } =
    useListConversationsByUseId(currentUserId);

  const onSubmit = (data: any) => {
    const messageRequest: MessageRequest = {
      content: data?.content,
      chatId: conversationId,
      mediaBase64: "",
      messageType: "TEXT",
      keywords: [],
    };
    mutateListConversations();
    handleSendMessage(messageRequest, conversationId as string);
    form.reset();
    form.setValue("content", "");
  };
  return (
    <Card className="w-full p-2 rounded-lg relative">
      <div className="flex gap-2 items-end w-full">
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="flex gap-2 items-end w-full"
          >
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
            <Button size="icon">
              <SendHorizonal />
            </Button>
          </form>
        </Form>
      </div>
    </Card>
  );
};

export default ChatInput;

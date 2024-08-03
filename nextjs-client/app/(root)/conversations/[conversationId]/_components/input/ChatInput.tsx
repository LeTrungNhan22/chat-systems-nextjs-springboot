"use client";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { ImageIcon, SendHorizonal } from "lucide-react";
import TextareaAutosize from "react-textarea-autosize";

import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { usePostImage } from "@/hooks/swr/message-api/usePostImage";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form";
import { useListConversationsByUseId } from "@/hooks/swr/conversation-api/useListConversationByUserId";
import { MessageRequestWithText } from "../../../_types/MessageTypes";
import UploadImage from "./UploadImage";
import axios from "axios";
import { API_BASE_URL } from "@/constants";

type Props = {
  handleSendMessage: (message: MessageRequestWithText, conversationId: string) => void | any; // Thêm handleSendMessage vào Props
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
  const [selectedImages, setSelectedImages] = useState<any[]>([]); // Thêm state để lưu ảnh đã chọn
  const [messageContent, setMessageContent] = useState(""); // Thêm state để lưu nội dung tin nhắn
  const {
    data: imageData,
    progress,
    isLoading,
    uploadImage: uploadImageFunc } = usePostImage();

  const onSubmit = async () => {
    let messageType = "TEXT";

    if (selectedImages.length > 0) {
      messageType = "IMAGE";
      const messageRequestWithImage = {
        content: messageContent,
        chatId: conversationId,
        mediaUrl: selectedImages.map((img) => img.url),
        messageType: messageType,
        keywords: []
      };
      console.log("messageRequestWithImage", messageRequestWithImage);
      
      handleSendMessage(messageRequestWithImage, conversationId as string);
    }

    console.log("messageType", messageType);

    if (messageType === 'TEXT') {
      const messageRequest: MessageRequestWithText = {
        content: messageContent,
        chatId: conversationId,
        mediaUrl: [],
        messageType: messageType,
        keywords: []
      };
      handleSendMessage(messageRequest, conversationId as string);
    }
    mutateListConversations();
    form.reset();
    setMessageContent("");
    setSelectedImages([]);
  };

  const handleImageSelect = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files && files.length > 0) {
      // Convert FileList to File[]
      const fileListArray = Array.from(files);
      const res = await uploadImageFunc(fileListArray);
    }

    event.target.value = ""; // Reset input file

  };

  const handleRemoveImageUploaded = async (id: string) => {
    try {
      const res = await axios.delete(`${API_BASE_URL}/images/${id}`)
      setSelectedImages((prevImages) => prevImages.filter((img) => img.id !== id));
      console.log(res)
    } catch (error) {
      console.log(error)
    }

  }

  useEffect(() => {
    if (imageData) {
      setSelectedImages(prevImages => {
        const existingImageIds = new Set(prevImages.map(img => img.id)); // Tạo Set để lưu ID ảnh đã có
        const newImages = imageData.filter((img: any) => !existingImageIds.has(img.id)); // Lọc ra ảnh mới
        return [...prevImages, ...newImages]; // Trả về mảng mới kết hợp
      });
    }
  }, [imageData]);

  console.log("selectedImages", selectedImages);


  return (
    <Card className="w-full flex flex-col p-2 rounded-lg relative">
      <div className="flex flex-row gap-2">
        {selectedImages &&
          selectedImages.map((item) => (
            <UploadImage
              key={item?.id}
              imageId={item?.id}
              data={item?.url}
              isLoading={isLoading}
              alt="image"
              progress={progress}
              handleRemoveImageUploaded={handleRemoveImageUploaded}
            />
          ))
        }
      </div>

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
                        placeholder={
                          selectedImages.length > 0
                            ? "Add a caption..."
                            : "Type a message..."
                        }
                        value={messageContent} // Sử dụng state messageContent
                        onChange={(e) => setMessageContent(e.target.value)} // Cập nhật state messageContent
                      />
                    </FormControl>
                  </FormItem>
                );
              }}
            />
            <input
              type="file"
              accept="image/*"
              multiple
              onChange={handleImageSelect}
              className="hidden"
              id="image-input"
            />
            <label htmlFor="image-input">
              <Button
                onClick={() => {
                  document.getElementById("image-input")?.click();
                }}
                type="button"
                size="icon"
                variant="outline"
              >
                <ImageIcon />
              </Button>
            </label>

            <Button
              type="submit"
              size="icon"
              disabled={
                (messageContent === "" && selectedImages.length <= 0) ||
                form.formState.isSubmitting
              } // Disable button khi messageContent và selectedImage đều rỗng
            >
              <SendHorizonal />
            </Button>
          </form>
        </Form>
      </div>
    </Card>
  );
};

export default ChatInput;

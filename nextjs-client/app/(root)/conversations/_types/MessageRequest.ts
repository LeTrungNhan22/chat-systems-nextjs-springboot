// message request

export type MessageRequest = {
  chatId: string | string[];
  content: string;
  messageType: "TEXT" | "IMAGE" | "VIDEO" | "FILE";
  mediaBase64: string;
  keywords: string[];
};

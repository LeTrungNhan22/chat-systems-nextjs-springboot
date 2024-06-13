// message request

interface MessageRequest {
  chatId: string | string[];
  content: string;
  messageType: "TEXT" | "IMAGE" | "VIDEO" | "FILE";
  mediaBase64: string;
  keywords: string[];
}

interface MessageSocketResponse {
  messageId: string;
  senderId: string;
  messageContent: string;
  mediaUrl: string;
  timestamp: string;
  status: string;
  messageType: string;
}

export type { MessageSocketResponse, MessageRequest };

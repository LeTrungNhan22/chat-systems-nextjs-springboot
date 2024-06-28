// message request

interface MessageRequestWithText {
  chatId: string | string[];
  content: string;
  messageType: string;
  mediaUrl: string[];
  keywords: string[];
}

interface MessageRequestWithImage {
  chatId: string | string[];
  content: string;
  messageType: string;
  chunkIndex: number;
  totalChunks: number;
  mediaUrl: string[];
  keywords: string[];
  messageId: string;
}

interface MessageSocketResponse {
  messageId: string;
  senderId: string;
  messageContent: string;
  mediaUrl: string[];
  timestamp: string;
  status: string;
  messageType: string;
}

export type {
  MessageSocketResponse,
  MessageRequestWithImage,
  MessageRequestWithText,
};

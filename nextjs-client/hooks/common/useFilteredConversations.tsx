import { useState, useEffect } from "react";

function useFilteredConversations(
  conversationsList: any[],
  currentUserId: string | undefined
) {
  const [filteredConversations, setFilteredConversations] = useState<any[]>([]);

  useEffect(() => {
    const filtered = conversationsList.map((conversation: any) => ({
      ...conversation,
      participants: conversation.participants.filter(
        (participant: any) => participant.id !== currentUserId
      ),
    }));
    setFilteredConversations(filtered);
  }, [conversationsList, currentUserId]);

  return filteredConversations;
}

export default useFilteredConversations;

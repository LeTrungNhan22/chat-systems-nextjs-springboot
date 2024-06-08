import { useParams } from "next/navigation";
import { useMemo } from "react";

export const useConversation = () => {
  const params = useParams();

  const conservationId = useMemo(
    () => params?.conversationId || ("" as string),
    [params?.conversationId]
  );

  const isActive = useMemo(() => !!conservationId, [conservationId]);

  return { conservationId, isActive };
};

import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";
import React from "react";

const SkeletonMessageItems = () => {
  return (
    <div className="flex items-end animate-pulse">
      <div
        className={cn("flex flex-col space-y-2 w-full", {
          "order-1 items-end": true, // Tạm thời giả định tin nhắn từ người dùng
        })}
      >
        <div className="bg-slate-200 w-2/3 h-8 rounded-md"></div> 
        <div className="bg-slate-200 w-1/4 h-4 rounded-md"></div> 
      </div>

      <Avatar className="relative w-8 h-8">
        <AvatarFallback> </AvatarFallback>
      </Avatar>
    </div>
  );
};

export default SkeletonMessageItems;

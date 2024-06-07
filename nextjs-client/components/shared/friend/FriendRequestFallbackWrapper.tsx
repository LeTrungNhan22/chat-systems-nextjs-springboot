import React from "react";

const FriendRequestFallbackWrapper = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="lg:flex-none flex-1 h-full w-full"> {/* Áp dụng lại các lớp CSS cần thiết */}
      {children}
    </div>
  );
};

export default FriendRequestFallbackWrapper;
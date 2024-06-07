"use client";
import { Card } from "@/components/ui/card";
import React from "react";

type Props = React.PropsWithChildren<{}>;

function RequestFriendContainer({ children }: Props) {
  return (
    <div className="flex flex-col lg:flex-row h-full w-full gap-4">
      {React.Children.map(children, (child, index) => (
        <div
          key={index}
          className={`lg:flex-none flex-1 h-full max-w-full ${
            index === 1 ? "lg:flex-grow" : ""
          }`}
        >
          {child}
        </div>
      ))}
    </div>
  );
}

export default RequestFriendContainer;

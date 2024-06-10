import React from "react";
import MessageItems from "./MessageItems";

type Props = {};

const Body = (props: Props) => {
  return (
    <div className="flex-1 flex-col-reverse flex w-full overflow-y-scroll gap-2 p-3 no-scrollbar">
      <MessageItems />
    </div>
  );
};

export default Body;

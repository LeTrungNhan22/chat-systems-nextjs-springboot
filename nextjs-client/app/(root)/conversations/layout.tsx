import ItemList from "@/components/item-list/ItemList";
import React from "react";

type Props = React.PropsWithChildren<{}>;

const ConversationLayout = ({ children }: Props) => {
  return (
    <>
      <ItemList title="Conversations">Conversations Page</ItemList>
      {children}
    </>
  );
};

export default ConversationLayout;

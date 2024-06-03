import SidebarWrapper from "@/components/shared/sidebar/SidebarWrapper";
import React from "react";
import { AuthProvider } from "../(authentication)/_context/context-auth";

type Props = React.PropsWithChildren<{}>;

const RootLayout = ({ children }: Props) => {
  return <SidebarWrapper>{children}</SidebarWrapper>;
};

export default RootLayout;

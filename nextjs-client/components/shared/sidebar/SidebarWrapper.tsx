"use client";
import React, { useContext } from "react";
import DesktopNavigation from "./nav/DesktopNavigation";
import MobileNavigation from "./nav/MobileNavigation";
import { AuthContext } from "@/context/auth/context-auth";

type Props = React.PropsWithChildren<{}>;

function SidebarWrapper({ children }: Props) {
  const { user, isAuthenticated, logOutUser, isLoading } =
    useContext(AuthContext);

  return (
    <div className="h-full w-full p-4 flex flex-col lg:flex-row gap-4">
      <MobileNavigation
        user={user}
        isAuthenticated={isAuthenticated}
        logOutUser={logOutUser}
        isLoading={isLoading}
      />
      <DesktopNavigation
        user={user}
        isAuthenticated={isAuthenticated}
        logOutUser={logOutUser}
        isLoading={isLoading}
      />
      <main className="h-[calc(100%-80px)] lg:h-full w-full flex gap-4">
        {children}
      </main>
    </div>
  );
}

export default SidebarWrapper;

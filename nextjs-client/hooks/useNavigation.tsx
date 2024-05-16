import { MessageSquare, Users } from "lucide-react";
import { usePathname } from "next/navigation";
import { useMemo } from "react";

export const useNavigation = () => {
  const pathName = usePathname();
  const paths = useMemo(
    () => [
      {
        name: "Conversations",
        path: "/conversations",
        icon: <MessageSquare />,
        active: pathName.startsWith("/conversations"),
      },
      {
        name: "Friends",
        path: "/friends",
        icon: <Users />,
        active: pathName.startsWith("/friends"),
      },
    ],
    [pathName]
  );

  return paths; //
};

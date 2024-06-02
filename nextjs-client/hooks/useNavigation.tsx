import { MessageSquare, UserPlus, Users } from "lucide-react";
import { usePathname } from "next/navigation";
import { useMemo } from "react";

export const useNavigation = () => {
  const pathName = usePathname();
  const paths = useMemo(
    () => [
      {
        name: "Cuộc trò chuyện",
        path: "/conversations",
        icon: <MessageSquare />,
        active: pathName.startsWith("/conversations"),
      },
      {
        name: "Danh sách bạn bè",
        path: "/friends-list",
        icon: <Users />,
        active: pathName.startsWith("/friends-list"),
      },
      {
        name: "Thêm bạn bè",
        path: "/friends-add",
        icon: <UserPlus />,
        active: pathName.startsWith("/friends-add"),
      },
    ],
    [pathName]
  );

  return paths; //
};

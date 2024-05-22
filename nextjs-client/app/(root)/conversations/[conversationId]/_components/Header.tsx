import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";
import { Circle, CircleArrowLeft } from "lucide-react";
import Link from "next/link";
import React from "react";

type Props = {
  name: string;
  imageUrl: string;
};

const Header = ({ name, imageUrl }: Props) => {
  return (
    <Card className="w-full rounded-lg flex items-center p-2 justify-between">
      <div className="flex items-center gap-2">
        <Link href="/conversations" className="block lg:hidden">
          <CircleArrowLeft />
        </Link>
        <Avatar className="w-8 h-8">
          <AvatarImage src="https://github.com/shadcn.png" />
          <AvatarFallback>CN</AvatarFallback>
        </Avatar>
        <h2 className="font-semibold">
          Username
        </h2>
      </div>
    </Card>
  );
};

export default Header;

import React from "react";
import Image from "next/image";

type Props = {
  size?: number;
};

const LoadingLogo = ({ size = 100 }: Props) => {
  return (
    <div className="w-full h-full flex justify-center items-center">
      <Image
        src="/logo.svg"
        width="0"
        height="0"
        sizes="100vw"
        priority
        alt="web-logo"
        className="w-[100px] h-auto animate-pulse duration-700 object-contain"
      />
    </div>
  );
};

export default LoadingLogo;

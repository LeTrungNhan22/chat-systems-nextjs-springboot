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
        width={size}
        height={size}
        alt="web-logo"
        className="animate-pulse duration-700"
      />
    </div>
  );
};

export default LoadingLogo;

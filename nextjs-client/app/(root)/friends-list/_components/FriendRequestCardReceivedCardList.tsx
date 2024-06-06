import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Image from "next/image";
import React from "react";

type Props = {};

const FriendRequestCardReceivedCardList = (props: Props) => {
  return (
    <div
      className="flex flex-row gap-3 overflow-x-auto w-full h-full no-scrollbar 
      lg:grid lg:grid-cols-5 lg:overflow-y-auto
    "
    >
      {" "}
      {/* Thêm overflow-x-auto */}
      {/* Lặp lại phần tử Card cho mỗi hình ảnh */}
      {[...Array(16)].map((_, index) => (
        <Card key={index} className="rounded-lg shadow-md w-full h-full">
          <img
            src={`https://source.unsplash.com/random/200x200?sig=${index}`} // Tạo URL ảnh ngẫu nhiên với sig để tránh cache
            alt={`Image ${index}`}
            className="w-full h-[110px] object-cover rounded-t-lg" // Giữ tỷ lệ ảnh và đảm bảo chiều cao cố định
          />
          <div className="p-2 text-surface dark:text-white">
            <h5 className="my-2 text font-medium leading-tight">
              Lê Trung Nhân
            </h5>
            <p className="mb-4 text-base text-gray-700 dark:text-gray-400 truncate">
              example@gmail.com
            </p>
            <div className="flex flex-wrap justify-between gap-4">
              {" "}
              {/* Nút nằm ngang */}
              <Button size={"sm"} variant="default">
                Chấp nhận
              </Button>
              <Button size={"sm"} variant="destructive">
                Từ chối
              </Button>
            </div>
          </div>
        </Card>
      ))}
    </div>
  );
};

export default FriendRequestCardReceivedCardList;

"use client";

import Link from "next/link";
import { UserAuthForm } from "@/components/auth/use-auth-form";
import { useSearchParams } from "next/navigation";
import { useEffect } from "react";
export default function LoginPage() {
  return (
    <>
      <div className="container relative h-[800px] flex items-center justify-center md:grid lg:max-w-none lg:grid-cols-1 lg:px-0">
        <div className="lg:p-8">
          <div className="mx-auto flex w-full flex-col justify-center space-y-6 sm:w-[350px]">
            <div className="flex flex-col space-y-2 text-center">
              <h1 className="text-2xl font-semibold tracking-tight">
                Đăng nhập tài khoản
              </h1>
              <p className="text-sm text-muted-foreground">
                Vui lòng nhập đăng nhập để vào hệ thốngs
              </p>
            </div>

            <UserAuthForm />
            <p className="px-8 text-center text-sm text-muted-foreground">
              Bằng cách đăng nhập, bạn đồng ý với{" "}
              <Link
                href="/"
                className="underline underline-offset-4 hover:text-primary"
              >
                Điều khoản dịch vụ
              </Link>{" "}
              và{" "}
              <Link
                href="/"
                className="underline underline-offset-4 hover:text-primary"
              >
                Chính sách bảo mật
              </Link>
              .
            </p>
          </div>
        </div>
      </div>
    </>
  );
}

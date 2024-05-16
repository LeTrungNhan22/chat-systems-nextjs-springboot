// middleware.ts

import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { ACCESS_TOKEN } from "./constants";

export function middleware(request: NextRequest) {
  const url = new URL(request.url);
  // Đọc token từ localStorage
  const token = request.cookies.get(ACCESS_TOKEN)?.value;
  console.log(!token);

  // Nếu không có token thì chuyển hướng về trang login
  if (!token) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }
}

export const config = {
  matcher: ["/conversations/:path*", "/friends"],
};

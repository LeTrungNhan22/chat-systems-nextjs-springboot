// middleware.ts

import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { ACCESS_TOKEN } from "./constants";

export function middleware(request: NextRequest) {
  const token = request.cookies.get(ACCESS_TOKEN)?.value; // Đọc token từ localStorage

  if (!token) {
    const loginUrl = new URL("/login", request.url); // Nếu không có token thì chuyển hướng về trang login
    return NextResponse.redirect(loginUrl);
  }

  const response = NextResponse.next();
  response.headers.set("Authorization", `Bearer ${token}`);
  return response;
}

export const config = {
  matcher: ["/conversations/:path*", "/friends"],
};

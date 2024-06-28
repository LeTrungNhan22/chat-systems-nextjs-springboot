// middleware.ts

import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { ACCESS_TOKEN } from "./constants";

export function middleware(request: NextRequest) {
  const token = request.cookies.get(ACCESS_TOKEN)?.value; // Đọc token từ localStorage

  // 2. Kiểm tra nếu request là yêu cầu đến API
  if (request.nextUrl.pathname.startsWith("/api")) {
    // 3. Nếu có token, thêm vào header
    if (token) {
      request.headers.set("Authorization", `Bearer ${token}`);
    }
    // 4. Nếu không có token, có thể redirect đến trang đăng nhập hoặc trả về lỗi
    // return NextResponse.redirect(new URL('/login', request.url));
  }

  // 5. Cho phép các request khác đi qua
  return NextResponse.next();
}
export const config = {
  matcher: [
    /*
     * Match all request paths except for the ones starting with:
     * - api (API routes)
     * - _next/static (static files)
     * - _next/image (image optimization files)
     * - favicon.ico (favicon file)
     */
    "/((?!api|_next/static|_next/image|favicon.ico).*)",
  ],
};

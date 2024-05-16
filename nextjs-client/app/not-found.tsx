"use client";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import { API_BASE_URL, ACCESS_TOKEN } from "@/constants";
import { getUrlParameter } from "@/utils/common/getParameterUrl";
import { useEffect } from "react";
import { useCookies } from "next-client-cookies";
// Not found Default page
const NotFoundDefaultPage = () => {
  const paramError = useSearchParams().get("error");
  return (
    <div className="flex flex-col items-center justify-center h-screen space-y-4">
      <h1 className="text-4xl font-bold">{`404 - ${paramError}`}</h1>
      <Link href="/">
        <span className="text-blue-500">Go back home</span>
      </Link>
    </div>
  );
};

// Not found with token url
const NotFoundTokenPage = () => {
  const router = useRouter();
  const cookiesStorage = useCookies();


  useEffect(() => {
    const search = window.location.search;
    const token = getUrlParameter("token", search);
    const error = getUrlParameter("error", search);
    if (token) {
      cookiesStorage.set(ACCESS_TOKEN, token, {
        expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 7),
        secure: true,
      });
    } else {
      router.push(`/login?error=${error}`);
    }
  }, [router]);

  return null;
};

export default function NotFound() {
  const getTokenParam = useSearchParams().get("token");
  return getTokenParam ? <NotFoundTokenPage /> : <NotFoundDefaultPage />;
}

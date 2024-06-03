"use client";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import { API_BASE_URL, ACCESS_TOKEN } from "@/constants";
import { getUrlParameter } from "@/utils/common/getParameterUrl";
import { useEffect } from "react";
import { useCookies } from "next-client-cookies";
const Oauth2RedirectPage = () => {
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
}

export default Oauth2RedirectPage
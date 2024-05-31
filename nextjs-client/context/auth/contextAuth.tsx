"use client";
import { ReactNode, createContext, useEffect, useMemo, useState } from "react";

import { AuthUser } from "@/utils/types/users/auth";
import { ACCESS_TOKEN, API_BASE_URL } from "@/constants";
import { useRouter } from "next-nprogress-bar";
import { useCookies } from "next-client-cookies";

interface TAuthContext {
  user: AuthUser | null;
  setUser: (user: AuthUser | null) => void;
  isAuthenticated: boolean;
  isLoading: boolean;
  logOutUser?: () => void;
}

export const AuthContext = createContext<TAuthContext>({
  user: null,
  setUser: () => {},
  isAuthenticated: false,
  isLoading: true, // Initial loading state
  logOutUser: () => {},
});

interface Props {
  children: ReactNode;
}

export const AuthProvider = ({ children }: Props) => {
  const [user, setUser] = useState<AuthUser | null>(null);
  const isAuthenticated = user !== null;
  const [isLoading, setIsLoading] = useState(true); // Initial loading state
  const router = useRouter();
  const cookies = useCookies();

  const fetchUserProfile = async (token: string) => {
    try {
      setIsLoading(true); // Start loading
      const response = await fetch(`${API_BASE_URL}/api/user/profile`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Failed to fetch user profile");
      }
      const userProfile = await response.json();
      setUser({
        isAuthenticated: true,
        token,
        user: userProfile,
      });
      router.push("/conversations");
    } catch (error) {
      console.error(error);
      router.push("/login?error=Failed to fetch user profile");
    } finally {
      setIsLoading(false); // Finish loading in all cases
    }
  };
  useEffect(() => {
    // Use useEffect to avoid calling twice on the client
    const token = cookies.get(ACCESS_TOKEN);
    if (token) {
      fetchUserProfile(token);
    } else {
      setIsLoading(false);
    }
  }, []);
  // console.table({ user, isAuthenticated, isLoading });

  // Log out user
  const logOutUser = () => {
    setUser(null);
    cookies.remove(ACCESS_TOKEN);
    router.push("/login");
  };

  return (
    <AuthContext.Provider
      value={{ user, setUser, isAuthenticated, isLoading, logOutUser }}
    >
      {children}
    </AuthContext.Provider>
  );
};

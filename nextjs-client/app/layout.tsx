import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { TooltipProvider } from "@/components/ui/tooltip";
import { ThemeProvider } from "@/components/ui/theme/theme-provider";
import { AuthProvider } from "@/app/(authentication)/_context/context-auth";
import { CookiesProvider } from "next-client-cookies/server";
import ProcessBarProvider from "@/provider/ProcessBarProvider";
import { Toaster } from "@/components/ui/toaster";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Chatterbox Systems",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className={inter.className}>
        <ThemeProvider
          attribute="class"
          defaultTheme="system"
          enableSystem
          disableTransitionOnChange
        >
          <TooltipProvider>
            <CookiesProvider>
              <AuthProvider>
                <ProcessBarProvider>
                  {children}
                  <Toaster />
                </ProcessBarProvider>
              </AuthProvider>
            </CookiesProvider>
          </TooltipProvider>
        </ThemeProvider>
      </body>
    </html>
  );
}

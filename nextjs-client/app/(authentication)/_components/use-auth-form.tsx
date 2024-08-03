"use client";

import * as React from "react";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons/icon";
import { Button } from "@/components/ui/button";
import { GOOGLE_AUTH_URL } from "@/constants";
import { useSearchParams } from "next/navigation";
import { useToast } from "@/components/ui/use-toast";
import { ToastAction } from "@radix-ui/react-toast";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";

interface UserAuthFormProps extends React.HTMLAttributes<HTMLDivElement> {
  className?: string;
}

export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
  const [isLoading, setIsLoading] = React.useState<boolean>(false);
  const params = useSearchParams();
  const { toast } = useToast();

  async function onClick(event: React.SyntheticEvent) {
    event.preventDefault();
    setIsLoading(true);
    window.location.href = GOOGLE_AUTH_URL;
  }

  React.useEffect(() => {
    if (params.get("error")) {
      () =>
        toast({
          variant: "default",
          title: "Đã xảy ra lỗi",
          description: "Vui lòng thử lại",
        });
      setIsLoading(false);
    }
  }, []);

  return (
    <div className={cn("grid gap-6", className)} {...props}>
      <form>
        <div className="grid gap-4">
          <div className="grid gap-1">
            <Label className="sr-only" htmlFor="email">
              Email
            </Label>
            <Input
              id="email"
              placeholder="email@gmail.com"
              type="email"
              autoCapitalize="none"
              autoComplete="email"
              autoCorrect="off"
              disabled={isLoading}
            />
          </div>
          <Button disabled={true}>
            {isLoading && (
              <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
            )}
            Đăng nhập
          </Button>
          <div className="relative">
            <div className="absolute inset-0 flex items-center">
              <span className="w-full border-t" />

            </div>

            <div className="relative flex justify-center text-xs uppercase">
              <span className="bg-background px-2 text-muted-foreground">
                hoặc
              </span>
            </div>
          </div>
          <Button variant="outline" onClick={onClick} disabled={isLoading}>
            {isLoading ? (
              <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
            ) : (
              <Icons.google className="mr-2 h-4 w-4" />
            )}{" "}
            Đăng nhập với Google
          </Button>
        </div>
      </form>
    </div>
  );
}

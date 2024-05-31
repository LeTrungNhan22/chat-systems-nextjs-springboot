"use client";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { TUser } from "@/utils/types/users/auth";
import { DialogTrigger } from "@radix-ui/react-dialog";
import axios from "axios";
import { UserPlus } from "lucide-react";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";

import { useDebounce } from "use-debounce";

type Props = {};

const AddFriendDialog = (props: Props) => {
  const [query, setQuery] = useState("");
  const [users, setUsers] = useState<TUser[]>([]);

  const debouncedQuery = useDebounce(query, 300);

  useEffect(() => {
    const fetchUsers = async () => {
      if (debouncedQuery.length > 2) {
        // Chỉ tìm kiếm khi có ít nhất 3 ký tự
        const res = await axios.get(
          `/api/users/search?query=${debouncedQuery}`
        );
        setUsers(res.data);
      } else {
        setUsers([]);
      }
    };
    fetchUsers();
  }, [debouncedQuery]);
  const form = useForm();

  const onHandleSubmit = (data: any) => {
    console.log(data);
  };

  return (
    <Dialog>
      <Tooltip>
        <TooltipTrigger>
          <Button asChild size={"icon"} className="p-2" variant={"outline"}>
            <DialogTrigger asChild>
              <UserPlus />
            </DialogTrigger>
          </Button>
        </TooltipTrigger>
        <TooltipContent side="right">
          <p>Thêm bạn</p>
        </TooltipContent>
      </Tooltip>

      <DialogContent>
        <DialogHeader>
          <DialogTitle>Thêm bạn</DialogTitle>
          <DialogDescription>
            Gửi lời mời kết bạn đến người khác
          </DialogDescription>
        </DialogHeader>
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onHandleSubmit)}
            className="space-y-8"
          >
            <FormField
              control={form.control}
              name={`email`}
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email:</FormLabel>
                  <FormControl>
                    <Input
                      placeholder="Email/UserId"
                      value={field.value || ""}
                      onChange={field.onChange}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <DialogFooter>
              <Button disabled={false} type="submit">
                Gửi yêu cầu
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

export default AddFriendDialog;

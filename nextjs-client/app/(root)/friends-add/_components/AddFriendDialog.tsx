"use client";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";

import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { Input } from "@/components/ui/input";
import { UserPlus } from "lucide-react";
import React, { useContext, useEffect, useState } from "react";
import { useForm } from "react-hook-form";

import useUserSearch from "@/hooks/swr/useUserSearchMutation";
import UserSearchItem from "./UserSearchItem";
import useUserSearchMutation from "@/hooks/swr/useUserSearchMutation";
import { TUser } from "@/utils/types/users/auth";
import { AuthContext } from "@/app/(authentication)/_context/context-auth";

const AddFriendDialog = () => {
  const form = useForm({
    defaultValues: {
      text: "",
    },
  });
  const [searchResults, setSearchResults] = useState<TUser[] | null>(null);
  const { searchUsers, isMutating, error } = useUserSearchMutation();
  const { user } = useContext(AuthContext);

  const onHandleSubmit = async (data: any) => {
    try {
      const results = await searchUsers(data.text);
      setSearchResults(results);
    } catch (error) {
      console.error(error);
    }
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
            <div className="flex flex-row  gap-3">
              <FormField
                control={form.control}
                name="text"
                rules={{ required: "Vui lòng nhập email hoặc ID" }} // Thêm validation rule
                render={({ field }) => (
                  <FormItem className="w-full">
                    <FormControl>
                      <Input placeholder="Email/Id" {...field} />
                    </FormControl>
                    <FormMessage>
                      {isMutating && <span>Đang tìm kiếm...</span>}
                      {searchResults?.length === 0 && !isMutating && (
                        <span>Không tìm thấy kết quả</span>
                      )}
                      {error && <span>{error.message}</span>}
                      {/* check user exist in searchResult*/}
                      {searchResults?.find(
                        (searchUser) => searchUser.id === user?.user.id
                      ) && <span>Không thể kết bạn với bản thân </span>}
                    </FormMessage>
                  </FormItem>
                )}
              />
              <Button size={"default"} type="submit" disabled={isMutating}>
                {isMutating ? "Đang tìm kiếm..." : "Tìm kiếm"}
              </Button>
            </div>
          </form>
        </Form>
        {searchResults && (
          <div className="space-y-4">
            {searchResults
              .filter((searchUser) => searchUser.id !== user?.user.id)
              .map((user) => (
                <UserSearchItem key={user.id} userSearchItem={user} />
              ))}
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default AddFriendDialog;

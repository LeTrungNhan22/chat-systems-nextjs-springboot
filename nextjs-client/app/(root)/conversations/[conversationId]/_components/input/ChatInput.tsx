import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form";
import { SendHorizonal } from "lucide-react";
import React from "react";
import { useForm } from "react-hook-form";
import TextareaAutosize from "react-textarea-autosize";
type Props = {};

const ChatInput = (props: Props) => {
  const form = useForm();
  const { isLoading } = form.formState;
  const onHandleSubmit = (data: any) => {
    console.log(data);
  };

  const handleInputOnchange = (e: any) => {
    console.log(e.target.value);
  };

  return (
    <Card className="w-full p-2 rounded-lg relative">
      <div className="flex gap-2 items-end w-full">
        <Form {...form}>
          <form
            onSubmit={onHandleSubmit}
            className="flex gap-2 items-end w-full"
          >
            <FormField
              name="content"
              control={form.control}
              render={({ field }) => {
                return <FormItem className="h-full w-full">
                  <FormControl>
                    <TextareaAutosize
                    onKeyDown={(e) => {
                      async function handleKeyDown(e: any) {
                        if (e.key === "Enter" && !e.shiftKey) {
                          e.preventDefault();
                          await form.handleSubmit(onHandleSubmit)();
                        }
                      }

                    }}
                      className="min-h-full w-full border-0 outline-0 resize-none bg-card text-card-foreground placeholder:text-muted-foreground p-1.5"
                      rows={1}
                      maxRows={3}
                      {...field}
                      onChange={handleInputOnchange}
                      placeholder="Type a message..."
                    />
                  </FormControl>
                </FormItem>;
              }}
            />
            <Button disabled={isLoading} type="submit" size="icon">
                <SendHorizonal/>
              </Button>
          </form>
        </Form>
      </div>
    </Card>
  );
};

export default ChatInput;

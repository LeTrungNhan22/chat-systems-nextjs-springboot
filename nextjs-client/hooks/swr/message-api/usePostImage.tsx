// usePostImage.ts
import axios, { AxiosProgressEvent } from "axios";
import { useState } from "react";

import { API_BASE_URL } from "@/constants";

export const usePostImage = () => {
  const [progress, setProgress] = useState(0);
  const [imageData, setImageData] = useState<any>(null);
  const [isLoading, setIsLoading] = useState(false); // Thêm state isLoading

  const uploadImage = async (images: File[]) => {
    const formData = new FormData();
    images.forEach((image) => {
      formData.append("images", image);
    });

    setIsLoading(true); // Bắt đầu tải lên

    try {
      const response = await axios.post(`${API_BASE_URL}/images/upload`, formData, {
        onUploadProgress: (progressEvent: AxiosProgressEvent) => {
          const progressLoaded = progressEvent.loaded;
          const progressTotal = progressEvent.total;
          if (progressTotal && progressLoaded) {
            const percentCompleted = Math.round((progressLoaded / progressTotal) * 100);
            setProgress(percentCompleted);
          }
        },
      });

      setImageData(response.data);
      return response.data;
    } catch (error) {
      console.error(error);
    } finally {
      setProgress(0);
      setIsLoading(false); // Kết thúc tải lên
    }
  };

  return {
    data: imageData,
    isLoading, // Trả về isLoading
    progress,
    uploadImage,
  };
};

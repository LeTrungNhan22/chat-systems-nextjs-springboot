
import { API_BASE_URL } from "@/constants";
import axios from "axios";
import useSWR from "swr";
import { useCookies } from "next-client-cookies";
import { useState } from "react";



const fetcher = async (url: string, formData: FormData) => {
	const response = await axios(url, {
		method: "POST",
		headers: {
			"Content-Type": "multipart/form-data",
		},
		data: formData
	})
	return response.data;
}

export const usePostImage = () => {
	const [progress, setProgress] = useState<number>(0);
	const { data, error, mutate, isLoading } = useSWR(
		[`${API_BASE_URL}/images/upload`], // Key bao gồm URL và dữ liệu ban đầu là null
		fetcher,
		{
			revalidateOnFocus: false, // Không tự động revalidate khi focus vào window
			revalidateOnReconnect: false, // Không tự động revalidate khi kết nối lại
			shouldRetryOnError: false, // Không tự động retry khi có lỗi
			
		}
	);

	const uploadImage = async (images: File[]) => {
		const formData = new FormData();
		images.forEach((image) => {
			formData.append("images", image); 
		});

		try {
			await mutate(
				async () => {
					const result = await fetcher(`${API_BASE_URL}/images/upload`, 
						formData,
					
					);
					return result; // Cập nhật data với kết quả mới
				},
				{
					optimisticData: data, // Dữ liệu tạm thời trong khi chờ kết quả thực
					rollbackOnError: true, // Quay lại dữ liệu cũ nếu có lỗi
				},
			);
		} catch (error) {
			// Xử lý lỗi ở đây
			console.error(error);
		}
	};

	return {
		data,
		error,
		isLoading,
		uploadImage,
	};
};

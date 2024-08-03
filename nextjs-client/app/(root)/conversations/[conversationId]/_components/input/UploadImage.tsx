'use client'
import Image from "next/image"
import React, { useEffect, useState } from 'react'
import { Progress } from "@/components/ui/progress"
import { API_BASE_URL } from "@/constants"


type Props = {
	data: string | undefined;
	alt: string
	imageId: string
	handleRemoveImageUploaded: (imageId: string) => void
	progress: number,
	isLoading: boolean
}


const UploadImage = ({ data, alt, imageId, progress, handleRemoveImageUploaded, isLoading }: Props) => {

	useEffect(() => {
		console.log("is uploading image:", isLoading);
	}, [isLoading]); // Log mỗi khi progress thay đổi

	return (
		<div className="relative h-[100px] w-[100px]">
			<Image
				src={`${API_BASE_URL}${data}`}
				alt={alt}
				fill
				className="object-cover rounded-md"
				placeholder="blur"
				sizes="100px"
				blurDataURL={`${API_BASE_URL}${data}`}
			/>
			<button
				onClick={() => handleRemoveImageUploaded(imageId)}
			>
				<span
					className="absolute
							top-0
							right-0
							cursor-pointer
							h-6 w-6 flex items-center justify-center
							bg-slate-600 shadow-md rounded-md m-0.5
							hover:bg-red-500 hover:text-white"
				>
					x
				</span>
			</button>

			{/* Assuming the progress is between 0 and 100 */}
			<Progress
				value={progress * 100}  // Assuming the progress is between 0 and 1
				className="w-full absolute bottom-0 h-1"
			/>
		</div>
	)
}

export default UploadImage
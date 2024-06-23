import Image from "next/image"
import React from 'react'
import ImageProcessBarUpload from "./ImageProcessBarUpload"
import { Progress } from "@/components/ui/progress"
import { API_BASE_URL } from "@/constants"

type Props = {
	data: string | undefined;
	alt: string
	imageLoadingProgress?: number | undefined
}

const UploadImage = ({ data, alt, imageLoadingProgress }: Props) => {
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
			<Progress
				value={imageLoadingProgress}
				className="w-full absolute bottom-0 h-1"
			/>
		</div>
	)
}

export default UploadImage
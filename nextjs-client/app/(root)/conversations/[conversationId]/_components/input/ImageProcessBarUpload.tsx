import React from 'react'

type Props = {
	progress: number | undefined
}

const ImageProcessBarUpload = ({ progress }: Props) => (
	<div className="h-2 bg-gray-200 rounded-full overflow-hidden">
		<div className="h-full bg-blue-500" style={{ width: `${progress}%` }} />
	</div>
)

export default ImageProcessBarUpload
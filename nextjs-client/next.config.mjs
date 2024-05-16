/** @type {import('next').NextConfig} */
const nextConfig = {
	redirects: async () => {
		return [
			{
				source: '/',
				destination: '/login',
				permanent: true,
			},
		];
	},
	rewrites: async () => {
		return [
			{
				source: "/oauth2/redirect", // Đường dẫn ban đầu
				destination: "/", // Đường dẫn mới
			},
		];
	}

};

export default nextConfig;

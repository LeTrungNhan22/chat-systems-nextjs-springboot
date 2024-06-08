/** @type {import('next').NextConfig} */
const nextConfig = {
	reactStrictMode: false,
	redirects: async () => {
		return [
			{
				source: '/',
				destination: '/conversations',
				permanent: true,
			},
		];
	},
	images: {
		remotePatterns: [
			{
				protocol: 'https',
				hostname: 'lh3.googleusercontent.com',
			},
		],
	},
};

export default nextConfig;

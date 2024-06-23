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
			{
				protocol: 'http',
				hostname: 'localhost',
				port: "8080",
				pathname: '/chatterbox-system/**',
			}
		],
	},
};

export default nextConfig;

/** @type {import('next').NextConfig} */
const nextConfig = {

	redirects: async () => {
		return [
			{
				source: '/',
				destination: '/conversations',
				permanent: true,
			},
		];
	},

};

export default nextConfig;

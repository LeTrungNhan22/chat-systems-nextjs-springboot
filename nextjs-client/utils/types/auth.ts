// /utils/types/auth.d.ts

export type TUser = {
  id: string;
  email: string;
  username: string;
  providerType: string;
  providerId: string;
  imageUrl: string;
  createdAt: string;
  updatedAt: string;
};

export type AuthUser = {
  isAuthenticated: boolean;
  token: string;
  user: TUser;
};

export type AuthResponse = {
  message: string;
  data?: AuthUser;
  success?: boolean;
};

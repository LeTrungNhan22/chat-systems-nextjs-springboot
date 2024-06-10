const keys = require("../config/keys");

//  api
export const API_BASE_URL = keys.apiBaseUrl;
export const API_GOOGLE_URL = keys.googleUrl;
export const OAUTH2_REDIRECT_URI = keys.redirectUri;
export const ACCESS_TOKEN = "accessToken";
export const GOOGLE_AUTH_URL =
API_GOOGLE_URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;
export const WEBSOCKET_ENDPOINT = keys.socketUrl;

// handle text client

export const HANDLE_PENDING = "Đang chờ xử lý...";
export const HANDLE_SUCCESS = "Xử lý thành công!";
export const HANDLE_ERROR = "Có lỗi xảy ra trong quá trình xử lý!";

const keys = require("../config/keys");

//  api
export const API_BASE_URL = keys.apiBaseUrl;
export const OAUTH2_REDIRECT_URI = keys.redirectUri;
export const ACCESS_TOKEN = "accessToken";
export const GOOGLE_AUTH_URL =API_BASE_URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;
export const WEBSOCKET_ENDPOINT = "http://localhost:8080/ws";

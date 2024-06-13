

import axios from "axios";

import { API_BASE_URL } from "@/constants";

const getMessageById = async (id: string) => {
  const res = await axios.get(`${API_BASE_URL}/messages/${id}`);
  return res.data;
}

export default getMessageById;
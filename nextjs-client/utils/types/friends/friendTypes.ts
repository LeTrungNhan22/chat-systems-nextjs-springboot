export interface FriendRequest {
  id: string;
  currentUserId: string;
  requestFriendId: string;
  status: string;
}
export interface FriendRequestDto {
  friendId: string;
}

export interface FriendStatusUpdateResponseDto {
  currentUserId: string;
  requestFriendId: string;
  status: string;
}

export interface Friend {
  id: string;
  currentUserId: string;
  requestFriendId: string;
  status: string;
}

package com.snow.oauth2.socialoauth2.model.user;


import com.snow.oauth2.socialoauth2.model.friend.FriendShip;
import lombok.Data;

import java.util.List;

@Data
public class UserDetails {
    private User user;
    private List<FriendShip> friendShips;
}

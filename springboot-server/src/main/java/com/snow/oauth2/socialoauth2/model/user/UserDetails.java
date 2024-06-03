package com.snow.oauth2.socialoauth2.model.user;


import com.snow.oauth2.socialoauth2.model.friend.Friend;
import lombok.Data;

import java.util.List;

@Data
public class UserDetails {
    private User user;
    private List<Friend> friends;
}

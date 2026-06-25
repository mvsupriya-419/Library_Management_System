package com.supriya.LMS.events;

import com.supriya.LMS.model.User;
import lombok.Data;

@Data
public class UserLoggedInEvent {


    private final User user;
}

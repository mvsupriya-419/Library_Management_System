package com.supriya.LMS.events;

import com.supriya.LMS.model.User;
import lombok.Data;

@Data
public class UserDeletedEvent {


    private final User user;
}

package com.supriya.LMS.event;

import com.supriya.LMS.Entity.Users;
import org.springframework.context.ApplicationEvent;

public class RegistrationCompleteEvent extends ApplicationEvent {

    private final Users user;

    public RegistrationCompleteEvent(Users user) {
        super(user);
        this.user = user;
    }

    public Users getUser() {
        return user;
    }
}
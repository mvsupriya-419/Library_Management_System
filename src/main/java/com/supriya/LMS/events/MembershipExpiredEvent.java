package com.supriya.LMS.events;

import com.supriya.LMS.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MembershipExpiredEvent {

    private final User user;
}

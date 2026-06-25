package com.supriya.LMS.listners;

import com.supriya.LMS.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
public class UserEventListeners {

    // user.registered
    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {

        System.out.println("USER REGISTERED , Welcome email sent to: " + event.getUser().getEmail());
        System.out.println(" Library card generated for: " + event.getUser().getName());
    }

    // user.updated
    @EventListener
    public void handleUserUpdated(UserUpdatedEvent event) {
        System.out.println(" USER UPDATED Search index updated for: "+ event.getUser().getName());
    }


    // user.deleted
    @EventListener
    public void handleUserDeleted(UserDeletedEvent event) {
        System.out.println(" USER DELETED , Removed user data for ID: "+ event.getUser().getId());
    }


    // user.logged_in
    @EventListener
    public void handleUserLoggedIn(UserLoggedInEvent event) {
        System.out.println(" USER LOGIN, User logged in: " + event.getUser().getName());
    }


    // membership.renewed
    @EventListener
    public void handleMembershipRenewed(MembershipRenewedEvent event) {
        System.out.println(" MEMBERSHIP RENEWED , User: "+ event.getUser().getName());
        System.out.println("Expiry Date: " + event.getExpiryDate());
    }


    // membership.expired
    @EventListener
    public void handleMembershipExpired(MembershipExpiredEvent event) {
        System.out.println(" MEMBERSHIP EXPIRED , Borrowing access suspended for: "+ event.getUser().getName());
    }
}

package com.supriya.LMS.service;

import com.supriya.LMS.dto.MembershipRequestDto;
import com.supriya.LMS.dto.UserRequestDto;
import com.supriya.LMS.events.*;
import com.supriya.LMS.model.User;
import com.supriya.LMS.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher publisher;

    public UserService(PasswordEncoder passwordEncoder, ApplicationEventPublisher publisher,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;

        this.publisher = publisher;
        this.userRepository = userRepository;
    }

    public void registerUser(UserRequestDto request) {

        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        System.out.println("User saved to database");
        publisher.publishEvent(new UserRegisteredEvent(user));
    }

    public void updateUser(UserRequestDto request) {
        User user = new User(request.getEmail(), request.getPassword());
        userRepository.save(user);
        System.out.println("User updated");
        publisher.publishEvent(new UserUpdatedEvent(user));
    }

//    public void deleteUser(UserRequestDto request) {
//        userRepository.deleteById(request.getId());
//        User user = new User(request.getId(), request.getName(), request.getEmail());
//        System.out.println("User deleted");
//        publisher.publishEvent(new UserDeletedEvent(user));
//    }

    public void loginUser(UserRequestDto request) {

        String email = request.getEmail();
        String password = request.getPassword();

        User userByPassword = userRepository.findByPassword(password);
        User userByEmail = userRepository.findByEmail(email);

        if (userByPassword != null && userByEmail != null) {
            System.out.println("User logged in");
            publisher.publishEvent(new UserLoggedInEvent(userByPassword));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void renewMembership(MembershipRequestDto request) {
        User user = new User(request.getName(), request.getEmail());
        System.out.println("Membership renewed");
        publisher.publishEvent(new MembershipRenewedEvent(user, request.getExpiryDate()));
    }

    public void expireMembership(UserRequestDto request) {
        User user = new User(request.getEmail(), request.getPassword());
        System.out.println("Membership expired");
        publisher.publishEvent(new MembershipExpiredEvent(user));
    }
}
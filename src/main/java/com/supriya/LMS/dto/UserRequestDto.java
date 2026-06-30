package com.supriya.LMS.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto{

    private String email;
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;


}
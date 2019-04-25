package me.wordwizard.backend.api.model.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthEmailRequest {
    private String email;
    private String password;
}

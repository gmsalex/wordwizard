package me.wordwizard.backend.model.api.auth;

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

package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.user.UserAuthEmail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
@Validated
public interface UserWithEmailRepository extends CrudRepository<UserAuthEmail, Long> {
    Optional<UserAuthEmail> findByEmail(@NotBlank @Email String email);
}

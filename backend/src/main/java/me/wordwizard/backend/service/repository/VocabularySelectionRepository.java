package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.vocabulary.VocabularySelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface VocabularySelectionRepository extends JpaRepository<VocabularySelection, Long> {
    /**
     * Get all vocabulary selections owned by user
     *
     * @param userId User id
     * @return List of vocabulary selections
     */
    List<VocabularySelection> findAllByUserId(@NotNull Long userId);


    /**
     * Get vocabulary selection owned by user and with specified name
     *
     * @param userId User id
     * @param name   Vocabulary selection name
     * @return Optional vocabulary selection
     */
    Optional<VocabularySelection> findByUserIdAndName(@NotNull Long userId, String name);

    /**
     * Get vocabulary selection owned by user and by id
     *
     * @param id     Vocabulary selection id
     * @param userId User id
     * @return Optional vocabulary selection
     */
    VocabularySelection findByIdAndUserId(@NotNull Long id, Long userId);

}

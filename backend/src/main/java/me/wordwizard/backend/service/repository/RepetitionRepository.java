package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface RepetitionRepository extends JpaRepository<Repetition, Long> {
    Stream<Repetition> findByEntryIdIsInAndSelectionId(Set<Long> veIds, long vsId);

    Stream<Repetition> findByIdInAndSelectionUserId(Set<Long> repIds, long userId);

    List<Repetition> findBySelectionIdAndSelectionUserIdOrderByCreatedDesc(long vsId, long userId);

    Integer countByEntryId(long veId);
}

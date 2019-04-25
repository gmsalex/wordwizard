package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.vocabulary.VocabularyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface VocabularyEntryRepository extends JpaRepository<VocabularyEntry, Long> {
    Integer countByIdInAndUserId(Set<Long> ids, long userId);

    Stream<VocabularyEntry> findAllByIdInAndUserId(Set<Long> veIds, long userId);
}

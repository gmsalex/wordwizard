package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface RepetitionRepository extends JpaRepository<Repetition, Long> {
    Stream<Repetition> findByEntryId(long veId);
}

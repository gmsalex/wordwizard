package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepetitionRepository extends JpaRepository<Repetition, Long> {
    List<Repetition> findBySelectionId(long vsId);
}

package me.wordwizard.backend.service.repository;

import me.wordwizard.backend.model.entity.vocabulary.VocabularyTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TagRepository extends CrudRepository<VocabularyTag, Long> {
    Stream<VocabularyTag> findAllByUserId(long userId);

    VocabularyTag findByIdAndUserId(long id, long userId);
}

package me.wordwizard.backend.service;

import me.wordwizard.backend.api.model.vocabulary.tag.VocabularyTagDTO;
import me.wordwizard.backend.model.entity.vocabulary.VocabularyTag;
import me.wordwizard.backend.security.auth.util.AuthUtil;
import me.wordwizard.backend.service.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static me.wordwizard.backend.api.mapper.MappingConfiguration.FROM_API_MAPPER;
import static me.wordwizard.backend.api.mapper.MappingConfiguration.TO_API_MAPPER;

@Service
@Validated
@RolesAllowed("ROLE_USER")
public class VocabularyTagService {
    private final ModelMapper fromApiMapper;
    private final ModelMapper toApiMapper;
    private final AuthUtil authUtil;
    private final TagRepository tagRepository;

    @Autowired
    public VocabularyTagService(TagRepository tagRepository, AuthUtil authUtil,
                                @Qualifier(FROM_API_MAPPER) ModelMapper fromApiMapper,
                                @Qualifier(TO_API_MAPPER) ModelMapper toApiMapper) {
        this.tagRepository = tagRepository;
        this.authUtil = authUtil;
        this.fromApiMapper = fromApiMapper;
        this.toApiMapper = toApiMapper;
    }

    /**
     * Persists new vocabulary tags associating them with the authenticated user.
     * If tag is not new tries to load them from the database.
     *
     * @param tagList List of dto objects
     * @return List of all tags entities loaded from the database
     */
    public List<VocabularyTag> createMissingTags(@NotNull List<VocabularyTagDTO> tagList) {
        var user = authUtil.getUserDetails().getUser();
        return tagList
                .stream()
                .map(tagDTO -> {
                    var tagEntity = fromApiMapper.map(tagDTO, VocabularyTag.class);
                    if (tagEntity.getId() == null) {
                        tagEntity.setUser(user);
                        tagRepository.save(tagEntity);
                    } else {
                        tagEntity = tagRepository.findByIdAndUserId(tagDTO.getId(), user.getId());
                    }
                    return tagEntity;
                }).collect(Collectors.toList());
    }
}

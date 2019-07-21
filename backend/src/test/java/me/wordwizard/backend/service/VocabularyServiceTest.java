package me.wordwizard.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableSet;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.wordwizard.backend.api.model.vocabulary.entry.VECreationDTO;
import me.wordwizard.backend.api.model.vocabulary.selection.VSCreationDTO;
import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import me.wordwizard.backend.model.entity.vocabulary.VocabularyEntry;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserEmailPrincipal;
import me.wordwizard.backend.security.auth.util.AuthUtil;
import me.wordwizard.backend.security.auth.util.InMemoryUserDetails;
import me.wordwizard.backend.security.auth.util.JsonSupport;
import me.wordwizard.backend.service.repository.RepetitionRepository;
import me.wordwizard.backend.service.repository.TestRepetitionRepository;
import me.wordwizard.backend.service.repository.VocabularyEntryRepository;
import me.wordwizard.backend.service.repository.VocabularySelectionRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@SpringBootTest
@DirtiesContext
@ContextConfiguration(classes = VocabularyServiceTest.Config.class)
public class VocabularyServiceTest extends JsonSupport {
    @Autowired
    private VocabularyService service;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private VocabularySelectionRepository vsRepository;
    @Autowired
    private VocabularyEntryRepository veRepository;
    @Autowired
    private RepetitionRepository repRepository;
    @Autowired
    private TestRepetitionRepository testRepRepository;

    @Before
    public void setUp() throws Exception {
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void getSelectionList() {
        assertThat(service.getSelectionList())
                .hasSize(1)
                .first()
                .extracting(v -> tuple(v.getName(), v.getUser().getId()))
                .as("Selection name/user id")
                .isEqualTo(tuple("Good", 100L));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void getRepetitionList() {
        assertThat(service.getRepetitionList(101))
                .hasSize(2)
                .extracting(Repetition::getId)
                .containsExactlyInAnyOrder(3L, 1L);
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails(value = "noselection@gmail.com")
    @Test
    public void getSelectionListEmptyResult() {
        assertThat(service.getSelectionList()).hasSize(0);
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void createSelection() throws IOException {
        var creation = getDataFromJsonFileSource("creationSelection.json", VSCreationDTO.class);
        var vs = service.createSelection(creation);

        assertThat(vs.getId()).isNotNull();
        var vsFetched = vsRepository.findById(vs.getId()).orElseThrow(() -> new IllegalArgumentException("Not found"));
        assertThat(vsFetched).extracting(v -> tuple(v.getName(), v.getUser().getId()))
                .as("selection_name/user_id")
                .isEqualTo(tuple(vs.getName(), authUtil.getUserId()));
    }


    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void addEntry() throws IOException {
        var dto = getDataFromJsonFileSource("addEntry.json", VECreationDTO.class);
        var repetition = service.addEntry(dto.getSelectionId(), dto.getEntry());
        assertThat(repetition.getId()).isNotNull();
        var repFetched = repRepository.findById(repetition.getId()).orElseThrow(() -> new IllegalArgumentException("Repetition not found"));
        var veFetched = veRepository.findById(repFetched.getEntry().getId()).orElseThrow(() -> new IllegalArgumentException("VE not found"));

        assertThat(veFetched).extracting(v -> tuple(v.getTerm(), v.getLanguage(), v.getUser().getId()))
                .as("term/language/meta/user_id")
                .isEqualTo(tuple(dto.getEntry().getTerm(), dto.getEntry().getLanguage(), authUtil.getUserId()));

        assertThat(repFetched).extracting(v -> tuple(v.getSelection().getId(), v.getEntry().getId()))
                .as("selection_id/entry_id")
                .isEqualTo(tuple(dto.getSelectionId(), veFetched.getId()));
    }


    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Test(expected = EmptyResultDataAccessException.class)
    public void addEntryUnknownSelectionId() throws IOException {
        var dto = getDataFromJsonFileSource("addEntry.json", VECreationDTO.class);
        service.addEntry(-1, dto.getEntry());
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Test(expected = EmptyResultDataAccessException.class)
    public void addEntrySelectionIdNotOwnedByUser() throws IOException {
        var dto = getDataFromJsonFileSource("addEntry.json", VECreationDTO.class);
        service.addEntry(300, dto.getEntry());
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Test(expected = IllegalArgumentException.class)
    public void createSelectionAlreadyExists() throws IOException {
        service.createSelection(new VSCreationDTO("Good"));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/add"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void addRepetition() {
        assertThat(testRepRepository.findBySelectionId(101)).isEmpty();
        service.addRepetition(101, ImmutableSet.of(100L, 101L));

        assertThat(testRepRepository.findBySelectionId(101))
                .extracting(v -> tuple(v.getEntry().getId(), v.getSelection().getId()))
                .as("entry_id/selection_id")
                .containsExactlyInAnyOrder(tuple(100L, 101L), tuple(101L, 101L));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/add"})
    @WithUserDetails("test@gmail.com")
    @Test(expected = IllegalArgumentException.class)
    public void addRepetitionUnknownVeId() {
        assertThat(testRepRepository.findBySelectionId(101)).isEmpty();
        service.addRepetition(101, ImmutableSet.of(-1L));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/add"})
    @WithUserDetails("test@gmail.com")
    @Test(expected = EmptyResultDataAccessException.class)
    public void addRepetitionWrongSelectionId() {
        service.addRepetition(200, ImmutableSet.of(100L));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/add"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void addRepetitionSkipAlreadyCreated() {
        repRepository.save(new Repetition(veRepository.getOne(100L), vsRepository.getOne(101L)));
        assertThat(testRepRepository.findBySelectionId(101))
                .hasSize(1)
                .extracting(v -> tuple(v.getEntry().getId(), v.getSelection().getId()))
                .as("entry_id/selection_id")
                .contains(tuple(100L, 101L));

        service.addRepetition(101, ImmutableSet.of(100L, 101L));

        assertThat(testRepRepository.findBySelectionId(101))
                .hasSize(2)
                .extracting(v -> tuple(v.getEntry().getId(), v.getSelection().getId()))
                .as("entry_id/selection_id")
                .containsExactlyInAnyOrder(tuple(100L, 101L), tuple(101L, 101L));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void removeRepetition() {
        assertThat(repRepository.findById(3L))
                .isPresent()
                .get()
                .extracting(v -> tuple(v.getEntry().getId(), v.getSelection().getId()))
                .as("entry_id/selection_id")
                .isEqualTo(tuple(102L, 101L));

        service.removeRepetition(ImmutableSet.of(3L));
        assertThat(veRepository.findById(100L)).isPresent();
        assertThat(repRepository.findById(3L)).isEmpty();
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void removeRepetitionWithRemovalUnknownVE() {
        assertThat(repRepository.findById(2L))
                .isPresent()
                .get()
                .extracting(v -> tuple(v.getEntry().getId(), v.getSelection().getId()))
                .as("entry_id/selection_id")
                .isEqualTo(tuple(101L, 102L));

        service.removeRepetition(ImmutableSet.of(2L));

        assertThat(veRepository.findById(101L)).isEmpty();
        assertThat(repRepository.findById(2L)).isEmpty();
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void removeEntry() {
        assertThat(veRepository.findAll()).isNotEmpty();
        assertThat(repRepository.findAll()).isNotEmpty();

        service.removeEntry(ImmutableSet.of(100L, 101L, 102L));

        assertThat(veRepository.findAll()).isEmpty();
        assertThat(repRepository.findAll()).isEmpty();
    }


    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("noselection@gmail.com")
    @Test
    public void removeEntryAnotherUser() {
        var veList = veRepository.findAll();
        assertThat(veList).isNotEmpty();
        var veIds = (Long[]) veList.stream().map(VocabularyEntry::getId).toArray(Long[]::new);
        service.removeEntry(ImmutableSet.of(100L, 101L));
        assertThat(veRepository.findAll())
                .extracting(VocabularyEntry::getId)
                .containsExactlyInAnyOrder(veIds);
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("test@gmail.com")
    @Test
    public void removeSelection() {
        assertThat(vsRepository.findAll()).isNotEmpty();
        assertThat(repRepository.findAll()).isNotEmpty();

        service.removeSelection(100L);
        service.removeSelection(101L);
        service.removeSelection(102L);

        assertThat(vsRepository.findAll()).isEmpty();
        assertThat(repRepository.findAll()).isEmpty();
    }


    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base", "/VocabularyServiceTest/db/repetition/remove"})
    @WithUserDetails("noselection@gmail.com")
    @Test(expected = EmptyResultDataAccessException.class)
    public void removeSelectionAnotherUser() {
        service.removeSelection(101L);
    }

    @TestConfiguration
    public static class Config extends JsonSupport {
        @Override
        public String getJsonDir() {
            return VocabularyServiceTest.class.getSimpleName();
        }

        @Primary
        @Bean
        public UserDetailsService inMemory() throws IOException {
            var users = this.getDataFromJsonFileSource("user.json", new TypeReference<List<WWUserEmailPrincipal>>() {
            }).stream().map(v -> (UserDetails) v).collect(Collectors.toList());
            return new InMemoryUserDetails(users);
        }
    }
}

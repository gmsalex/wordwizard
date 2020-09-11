package me.wordwizard.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableSet;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.wordwizard.backend.api.model.vocabulary.entry.VECreateDTO;
import me.wordwizard.backend.model.entity.vocabulary.VocabularyEntry;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserEmailPrincipal;
import me.wordwizard.backend.security.auth.util.AuthUtil;
import me.wordwizard.backend.security.auth.util.InMemoryUserDetails;
import me.wordwizard.backend.security.auth.util.JsonSupport;
import me.wordwizard.backend.service.repository.RepetitionRepository;
import me.wordwizard.backend.service.repository.VocabularyEntryRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    private VocabularyEntryRepository veRepository;
    @Autowired
    private RepetitionRepository repRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void name() {

    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/VocabularyServiceTest/db/base"})
    @WithUserDetails("test@gmail.com")
    @Transactional
    @Test
    public void addEntry() throws IOException {
        var dto = getDataFromJsonFileSource("addEntry.json", VECreateDTO.class);
        var persistedDto = service.addEntry(dto);
        assertThat(persistedDto.getId()).isNotNull();

        repRepository.findByEntryId(persistedDto.getId()).findFirst().orElseThrow(() -> new IllegalArgumentException("Repetition not found"));
        var veFetched = veRepository.findById(persistedDto.getId()).orElseThrow(() -> new IllegalArgumentException("VE not found"));

        assertThat(veFetched).extracting(v -> tuple(v.getTerm(), v.getLanguage(), v.getUser().getId()))
                .as("term/language/meta/user_id")
                .isEqualTo(tuple(dto.getEntry().getTerm(), dto.getEntry().getLanguage(), authUtil.getUserId()));
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
    @WithUserDetails("test@gmail.com")
    @Test
    public void getVocabularySummary() {
        var summary = service.getVocabularySummary();
        assertThat(summary.getTagList())
                .extracting(v -> tuple(v.getId(), v.getName()))
                .as("id/name")
                .containsExactlyInAnyOrder(tuple(101L, "Tag 101"), tuple(102L, "Tag 102"));

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

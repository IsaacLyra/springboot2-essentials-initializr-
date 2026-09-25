package academy.devisaac.springboot2.repository;

import academy.devisaac.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Log4j2
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true"
})
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists creates anime when successful")
    void save_PersistAnime_WhenSuccessful(){ //Metodo que ta sendo testado e o que o metodo tem que fazer
        Anime  animeTobeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeTobeSaved);

        Assertions.assertThat(savedAnime).isNotNull(); // verifique que o anime salvo não seja nulo
        Assertions.assertThat(savedAnime.getId()).isNotNull(); // verifique que o id de anime salvo não seja nulo
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeTobeSaved.getName()); // Verifica se o valor a ser salvo foi igual o pedido a ser salvo
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void UpdatesAnime_WhenSuccessful(){
        Anime  animeTobeSaved = createAnime();


        Anime animeSaved = this.animeRepository.save(animeTobeSaved);

        animeSaved.setName("Overlord");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();

        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());


    }


    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime  animeTobeSaved = createAnime();


        Anime animeSaved = this.animeRepository.save(animeTobeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();

    }

    @Test
    @DisplayName("Find by Name returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime  animeTobeSaved = createAnime();


        Anime animeSaved = this.animeRepository.save(animeTobeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotEmpty();

        Assertions.assertThat(animes).contains(animeSaved);


    }


    @Test
    @DisplayName("Find by Name returns empty list when no animes is found")
    void findByName_ReturnsEmptyList_When_AnimeIsNotFound(){
        List<Anime> animes = this.animeRepository.findByName("xaxa");

        Assertions.assertThat(animes).isEmpty();



    }



    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }


}
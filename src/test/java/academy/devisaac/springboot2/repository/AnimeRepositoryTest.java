package academy.devisaac.springboot2.repository;

import academy.devisaac.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
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
    @DisplayName("Save creates anime when successful")
    void save_PersistAnime_WhenSuccessful(){ //Metodo que ta sendo testado e o que o metodo tem que fazer
        Anime  animeTobeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeTobeSaved);

        Assertions.assertThat(savedAnime).isNotNull(); // verifique que o anime salvo não seja nulo
        Assertions.assertThat(savedAnime.getId()).isNotNull(); // verifique que o id de anime salvo não seja nulo
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeTobeSaved.getName()); // Verifica se o valor a ser salvo foi igual o pedido a ser salvo



    }

    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }


}
package academy.devisaac.springboot2.repository;

import academy.devisaac.springboot2.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimeRepository extends JpaRepository<Anime, Long> {




    //conexão com o banco
}

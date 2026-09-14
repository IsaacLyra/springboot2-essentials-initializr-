package academy.devisaac.springboot2.repository;

import academy.devisaac.springboot2.domain.Anime;

import java.util.List;

public interface AnimeRepository {

    List<Anime> listAll();

    //conexão com o banco
}

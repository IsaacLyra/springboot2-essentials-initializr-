package academy.devisaac.springboot2.service;

import academy.devisaac.springboot2.domain.Anime;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AnimeService{
    //private final AnimeRepository animeRepository
    public List<Anime> listAll(){
        return List.of(new Anime(1L,"Boku no hero"), new Anime(2L, "Naruto"));
    }
    // regras do produto
}

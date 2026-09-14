package academy.devisaac.springboot2.controller;



import academy.devisaac.springboot2.domain.Anime;
import academy.devisaac.springboot2.service.AnimeService;
import academy.devisaac.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final DateUtil dateutil; //(Melhor para fazer testes unitarios do que o autowierd)
    private final  AnimeService animeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Anime> list(){
        log.info(dateutil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    return animeService.listAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Anime findById(@PathVariable long id){
        return animeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        public Anime save(@RequestBody Anime anime){
       return animeService.save(anime);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        animeService.delete(id);
    }


    //end point

}

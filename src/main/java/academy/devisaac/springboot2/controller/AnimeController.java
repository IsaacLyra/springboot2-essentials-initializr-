package academy.devisaac.springboot2.controller;



import academy.devisaac.springboot2.domain.Anime;
import academy.devisaac.springboot2.rquests.AnimePostRequestBody;
import academy.devisaac.springboot2.rquests.AnimePutRequestBody;
import academy.devisaac.springboot2.service.AnimeService;
import academy.devisaac.springboot2.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public Page<Anime> list(Pageable pageable){
        log.info(dateutil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    return animeService.listAll(pageable);

    }
    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Anime> listAll(){
        log.info(dateutil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return animeService.listAllNonPageable();
    }


    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Anime findById(@PathVariable long id){
        return animeService.findByIdOrThrowBadRequestException(id);
    }



    @GetMapping(path = "/find/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Anime> findByName(@PathVariable String name){
        return animeService.findByName(name);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        public Anime save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
       return animeService.save(animePostRequestBody);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        animeService.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void replace(@RequestBody @Valid AnimePutRequestBody animePutRequestBody){ // Update
        animeService.replace(animePutRequestBody);
    }



    //end point

}

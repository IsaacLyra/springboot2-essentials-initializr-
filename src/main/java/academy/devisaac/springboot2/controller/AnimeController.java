package academy.devisaac.springboot2.controller;



import academy.devisaac.springboot2.domain.Anime;
import academy.devisaac.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("anime")
public class AnimeController {
    private final DateUtil dateutil; //(Melhor para fazer testes unitarios do que o autowierd)

    @GetMapping(path = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<Anime> list(){
        log.info(dateutil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return List.of(new Anime("Boku no hero"), new Anime("Naruto"));
    };


}

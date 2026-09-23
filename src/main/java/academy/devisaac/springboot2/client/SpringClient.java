package academy.devisaac.springboot2.client;

import academy.devisaac.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {


        ResponseEntity<Anime> entitty = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);// requisição que retorna um JSON
        log.info(entitty);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object); //Retorna sem o status

    }

}

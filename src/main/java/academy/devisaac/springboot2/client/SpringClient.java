package academy.devisaac.springboot2.client;

import academy.devisaac.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {


        ResponseEntity<Anime> entitty = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);// requisição que retorna um JSON
        log.info(entitty);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object); //Retorna sem o status

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class); //Array para receber uma lista
        log.info(Arrays.toString(animes)); // Jeito velho


        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>() {});//Pode passar diretamente a lista
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("kindom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
//
//        log.info("saved anime {}", kingdomSaved);


        Anime samuraiChamploo = Anime.builder().name("Samurai champloo").build();
        ResponseEntity<Anime> samuraChamplooSaved = new RestTemplate().exchange(
                "http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(samuraiChamploo, createJsonHeader()), Anime.class); // Metodo Post

        log.info("saved anime {}", samuraChamplooSaved, createJsonHeader());

        Anime animeTobeUpdated = samuraChamplooSaved.getBody();
        animeTobeUpdated.setName("Samurai champloo2");

        ResponseEntity<Void> samuraChamplooUpdated = new RestTemplate().exchange("http://localhost:8080/animes",// Metodo Put
                HttpMethod.PUT,
                new HttpEntity<>(animeTobeUpdated, createJsonHeader()),
                Void.class);

        log.info(samuraChamplooUpdated);


        ResponseEntity<Void> samuraChamplooUpdatedDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}",// Metodo Put
                HttpMethod.DELETE,
               null,        //Metodo delete
                Void.class,
                animeTobeUpdated.getId());

        log.info(samuraChamplooUpdatedDelete);


    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}

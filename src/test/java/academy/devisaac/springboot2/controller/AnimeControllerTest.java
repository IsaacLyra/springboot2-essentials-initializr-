package academy.devisaac.springboot2.controller;

import academy.devisaac.springboot2.domain.Anime;
import academy.devisaac.springboot2.rquests.AnimePostRequestBody;
import academy.devisaac.springboot2.rquests.AnimePutRequestBody;
import academy.devisaac.springboot2.service.AnimeService;
import academy.devisaac.springboot2.util.AnimeCreator;
import academy.devisaac.springboot2.util.AnimePostRequestBodyCreator;
import academy.devisaac.springboot2.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {
   @InjectMocks //Quando for testada a classe em si

    private  AnimeController animeController;

    @Mock // Para todas as classes que estão sendo utilizadas detro do AnimeController
    private AnimeService animeServiceMock;

    @BeforeEach // Antes de cada um dos testes
    void Setup() {
     PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
     BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
             .thenReturn(animePage);//não importa o que este list all receba


     BDDMockito.when(animeServiceMock.listAllNonPageable())
             .thenReturn(List.of(AnimeCreator.createValidAnime()));


     BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong())) // anyLong qualquer valor
             .thenReturn(AnimeCreator.createValidAnime());



     BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString())) // anyString qualquer String
             .thenReturn(List.of(AnimeCreator.createValidAnime()));


     BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class))) // anyString qualquer Striing
             .thenReturn(AnimeCreator.createValidAnime());


     BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

     BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());


    }






    @Test
    @DisplayName("list returns of anime inside page object when successful")
    void list_returnsListOfAnimesInsidePageObject_WhenSuccessful(){
     String expecetdname = AnimeCreator.createValidAnime().getName();
     Page<Anime> animePage = animeController.list(null);

     Assertions.assertThat(animePage).isNotNull();

     Assertions.assertThat(animePage.toList()).isNotEmpty();

     Assertions.assertThat(animePage.toList())
             .isNotEmpty()
             .hasSize(1);

     Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expecetdname);


    }


 @Test
 @DisplayName("list returns of anime inside page object when successful")
 void listAll_returnsListOfAnimes_WhenSuccessful(){
  String expecetdname = AnimeCreator.createValidAnime().getName();
  List<Anime> animes = animeController.listAll();

  Assertions.assertThat(animes)
          .isNotNull()
          .isNotEmpty()
          .hasSize(1);

  Assertions.assertThat(animes.get(0).getName()).isEqualTo(expecetdname);

 }

 @Test
 @DisplayName("findById returns  anime when successful")
 void findById_returnsListOfAnimes_WhenSuccessful(){
  Long expecetedId = AnimeCreator.createValidAnime().getId();

 Anime anime = animeController.findById(1);

  Assertions.assertThat(anime).isNotNull();

  Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expecetedId);
 }


 @Test
 @DisplayName("findByName returns list of anime when successful")
 void findByName_returnsListOfAnimes_WhenSuccessful(){
  String expectedName = AnimeCreator.createValidAnime().getName();

  List<Anime> anime = animeController.findByName("anime");

  Assertions.assertThat(anime).isNotNull()
          .isNotEmpty()
          .hasSize(1)
  ;


  Assertions.assertThat(anime.get(0).getName()).isEqualTo(expectedName);
 }



 @Test
 @DisplayName("findByName returns list empty list of anime when anime is not found")
 void findByName_returnsListOfAnimes_WhenAnimeIsNotFound(){


  BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString())) // anyLong qualquer valor
          .thenReturn(Collections.emptyList());

  List<Anime> animes = animeController.findByName("anime");

  Assertions.assertThat(animes)
          .isNotNull()
          .isEmpty();
 }


 @Test
 @DisplayName("save returns anime when successful")
 void save_returnsAnimes_WhenSuccessful(){

  Long expecetedId = AnimeCreator.createValidAnime().getId();

  Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

  Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());


 }

 @Test
 @DisplayName("replace update anime when successful")
 void replace_returnsAnime_WhenSuccessful(){


  Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

  ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

   Assertions.assertThat(entity).isNotNull();

   Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

 }

 @Test
 @DisplayName("delete removes anime when successful")
 void delete_RemovesAnime_WhenSuccessful(){


  Assertions.assertThatCode(() -> animeController.delete(1))
          .doesNotThrowAnyException();

  ResponseEntity<Void> entity = animeController.delete(1);

  Assertions.assertThat(entity).isNotNull();

  Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

 }


}

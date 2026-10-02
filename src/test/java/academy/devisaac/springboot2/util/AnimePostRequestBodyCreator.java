package academy.devisaac.springboot2.util;

import academy.devisaac.springboot2.domain.Anime;
import academy.devisaac.springboot2.rquests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeTobeSaved().getName())
                .build();
    }
}
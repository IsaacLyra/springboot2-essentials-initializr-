package academy.devisaac.springboot2.mapper;

import academy.devisaac.springboot2.domain.Anime;
import academy.devisaac.springboot2.rquests.AnimePostRequestBody;
import academy.devisaac.springboot2.rquests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  "spring")
public abstract class AnimeMapper {

    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);


    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);



}

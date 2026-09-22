package academy.devisaac.springboot2.rquests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AnimePostRequestBody {
   @NotEmpty(message = "The anime cannot be empty")
   private String name;

   @URL(message = "The URL is not valid")
   private String url;

}

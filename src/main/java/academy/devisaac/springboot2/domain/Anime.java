package academy.devisaac.springboot2.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Anime {
    @GeneratedValue
    @Id
    private Long id;
    private String name;

    //O que tem do banco de dados
}

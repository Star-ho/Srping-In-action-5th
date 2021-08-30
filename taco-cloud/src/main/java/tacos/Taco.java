package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;


@Data
@Entity
@RestResource(rel="tacos",path = "tacos")
public class Taco {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date CreatedAt;

    @NotNull
    @Size(min=5,message="Name must be at least 5 character long")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min=1,message="You must choose at list 1 ingredient")
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt(){
        this.CreatedAt=new Date();
    }


}

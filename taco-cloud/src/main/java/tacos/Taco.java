package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.rest.core.annotation.RestResource;

import com.datastax.dri

@Data
@Table("tacos")
@RestResource(rel="tacos",path = "tacos")
public class Taco {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id=UUIDs.timeBased();


    @NotNull
    @Size(min=5,message="Name must be at least 5 character long")
    private String name;

    @Size(min=1,message="You must choose at list 1 ingredient")
    private List<Ingredient> ingredients;

    private Date CreatedAt;

    @PrePersist
    void createdAt(){
        this.CreatedAt=new Date();
    }


}

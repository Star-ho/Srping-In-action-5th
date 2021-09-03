package tacos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.datastax.oss.driver.api.core.uuid.Uuids; //version up
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@RestResource(rel = "tacos",path = "tacos")
@Table("tacos")
public class Taco {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id= Uuids.timeBased();


    @NotNull
    @Size(min=5,message="Name must be at least 5 character long")
    private String name;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING)
    private Date CreatedAt=new Date();

    @Size(min=1,message = "You must choose at least 1 ingredient")
    @Column
    private List<IngredientUDT> ingredients;


}

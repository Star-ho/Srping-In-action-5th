package tacos;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table("tacoders")
@Data
public class Order implements Serializable {

    private static final long serialVersionUID=1L;

    @PrimaryKey
    private UUID id= Uuids.timeBased();
    private Date createdAt;

    @Column("tacos")
    private List<TacoUDT> tacos=new ArrayList<>();

    @Column("user")
    private UserUDT user;

    public void addDesign(TacoUDT design){
        this.tacos.add(design);
    }

}

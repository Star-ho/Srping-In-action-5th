package tacos.web.api;


//hateOs변경
//ResourceSupport changed to RepresentationModel


import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import tacos.Ingredient;
import tacos.Taco;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Relation(value = "taco",collectionRelation = "tacos")
public class TacoResource extends RepresentationModel<TacoResource> {

    private static final IngredientResourceAssembler ingredientAssembler=new IngredientResourceAssembler();

    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    public TacoResource(Taco taco){
        this.name=taco.getName();
        this.createdAt=taco.getCreatedAt();
    }
}

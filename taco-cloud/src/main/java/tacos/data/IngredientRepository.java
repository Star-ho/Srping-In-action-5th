package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.Ingredient;

import javax.persistence.criteria.CriteriaBuilder;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {

}

package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

import javax.persistence.criteria.CriteriaBuilder;

public interface IngredientRepository extends CrudRepository<Ingredient,String> {

}

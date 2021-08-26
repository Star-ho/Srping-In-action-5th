package tacos.web;


import java.util.Arrays;
import java.util.List;
import tacos.Ingredient;
import tacos.Ingredient.Type;


public class DesignTacoController {
    List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("FLTO","Flour Toritilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient()

    );
}

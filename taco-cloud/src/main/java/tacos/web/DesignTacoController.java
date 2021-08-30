package tacos.web;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.Errors;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,TacoRepository tacoRepo){
        this.tacoRepo=tacoRepo;
        this.ingredientRepo=ingredientRepo;
    }

    @ModelAttribute(name="order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String ShowDesignForm(Model model){

        List<Ingredient> ingredients=new ArrayList<>();
        ingredientRepo.findAll().forEach(i->ingredients.add(i));
        Type[] types=Ingredient.Type.values();
        for(Type type:types){
            model.addAttribute(type.toString().toLowerCase(),
                    fiterByType(ingredients,type));
        }

        model.addAttribute("taco",new Taco());

        return "design";
    }

    private List<Ingredient> fiterByType(List<Ingredient> ingredients, Type type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design,Errors errors,@ModelAttribute Order order){
        if(errors.hasErrors()){
            return "design";
        }

        Taco saved=tacoRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}

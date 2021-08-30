package tacos.web;


import java.security.Principal;
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
import tacos.User;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")//이 클래스에서는 세션에 있는 order객체를받아서 사용가능하다
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;//왜 얘만 final로 받는지...
    private TacoRepository tacoRepo;
    private UserRepository userRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo, UserRepository userRepo){
        this.tacoRepo=tacoRepo;
        this.ingredientRepo=ingredientRepo;
        this.userRepo=userRepo;
    }

    @ModelAttribute(name="order")//모델 객체에서 order를 가져다 사용
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String ShowDesignForm(Model model, Principal principal){

        List<Ingredient> ingredients=new ArrayList<>();
        ingredientRepo.findAll().forEach(i->ingredients.add(i));
        Type[] types=Ingredient.Type.values();
        for(Type type:types){
            model.addAttribute(type.toString().toLowerCase(),
                    fiterByType(ingredients,type));
        }
        String username=principal.getName();
        User user=userRepo.findByUsername(username);
        model.addAttribute("user",user);
//        model.addAttribute("taco",new Taco());

        return "design";
    }

    private List<Ingredient> fiterByType(List<Ingredient> ingredients, Type type){// enum에 있는거만 쓰려고 받은듯?
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design,Errors errors,@ModelAttribute Order order){
        System.out.println("complete make taco");
        if(errors.hasErrors()){
            return "design";
        }
        Taco saved=tacoRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }
}

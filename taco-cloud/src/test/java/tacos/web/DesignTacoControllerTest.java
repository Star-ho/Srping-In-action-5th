package tacos.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.TacoRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import tacos.web.DesignTacoController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class DesignTacoControllerTest {

    @Test
    public void sholdReturnRecentTacos(){
        Taco[] tacos={
                testTaco(1L),testTaco(2L),testTaco(3L),testTaco(4L),
                testTaco(5L),testTaco(6L),testTaco(7L),testTaco(8L),
                testTaco(9L),testTaco(10L),testTaco(11L),testTaco(12L),
                testTaco(13L),testTaco(14L),testTaco(15L),testTaco(16L),
        };
        Flux<Taco> tacoFlux= Flux.just(tacos);
        TacoRepository tacoRepo= Mockito.mock(TacoRepository.class);

        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient=WebTestClient.bindToController(new DesignTacoController(tacoRepo)).build();

        testClient.get().uri("/design/recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString());



    }

    private Taco testTaco(Long number){
        Taco taco=new Taco();
        taco.setId(UUID.randomUUID());
        taco.setName("Taco "+number);
        List<Ingredient> ingredients= new ArrayList<>();
        ingredients.add(new Ingredient("INGA","Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient("INGB","Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }


}
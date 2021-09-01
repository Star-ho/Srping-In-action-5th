package tacos.web;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import tacos.web.api.TacoResource;
//import tacos.web.api.TacoResourceAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//hateOS관련 부분 바뀐게 있음! 자세한것은 아래 사이트 참고
//ResourceSupport is now RepresentationModel
//Resource is now EntityModel
//Resources is now CollectionModel
//PagedResources is now PagedModel
//Resource was replaced by EntityModel, and ControllerLinkBuilder was replaced by WebMvcLinkBuilder
//https://honeyinfo7.tistory.com/276
//https://newbedev.com/resource-and-controllerlinkbuilder-not-found-and-deprecated

@Slf4j
@RestController
@RequestMapping(path="/design",produces = "application/json")
@CrossOrigin(origins="*")
public class DesignTacoController {

    private TacoRepository tacoRepo;
    private UserRepository userRepo;

    public DesignTacoController(TacoRepository tacoRepo){
        this.tacoRepo=tacoRepo;
    }

    @GetMapping("/recent")
    public Flux<Taco> recentTacos(){
        return tacoRepo.findAll().take(12);
    }


    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") Long id){
        return tacoRepo.findById(id);
    }

    @PostMapping(consumes ="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono){
        return tacoRepo.saveAll(tacoMono).next();
    }

}

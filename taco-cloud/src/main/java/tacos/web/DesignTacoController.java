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
import tacos.Taco;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import tacos.web.api.TacoResource;
import tacos.web.api.TacoResourceAssembler;

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
    public CollectionModel<TacoResource> recentTacos(){
        PageRequest page=PageRequest.of(0,12, Sort.by("createdAt").descending());
        List<Taco> tacos= tacoRepo.findAll(page).getContent();

        //taco리스트를 받아서 TacoResource컬렉션모델을 리턴
        CollectionModel<TacoResource> recentResource=new TacoResourceAssembler().toCollectionModel(tacos);


//        recentResource.add(new Link("http://127.0.0.1:8000/desing/recent","recents"));0
        recentResource.add(
                linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos()).withRel("recents"));

        return recentResource;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco=tacoRepo.findById(id);
        if(optTaco.isPresent()){
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes ="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepo.save(taco);
    }

}

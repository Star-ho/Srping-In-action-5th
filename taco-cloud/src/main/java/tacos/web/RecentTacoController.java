package tacos.web;


import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import tacos.Taco;
import tacos.data.TacoRepository;
import tacos.web.api.TacoResource;
import tacos.web.api.TacoResourceAssembler;
import  org.springframework.hateoas.server.mvc.ControllerLinkRelationProvider.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class RecentTacoController {
    private TacoRepository tacoRepo;

    public RecentTacoController(TacoRepository tacoRepo){
        this.tacoRepo=tacoRepo;
    }

    @GetMapping(path = "/tacos/recent",produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> recentTacos(){
        PageRequest page=PageRequest.of(0,12, Sort.by("createdAt").descending());

        List<Taco> tacos=tacoRepo.findAll(page).getContent();
        CollectionModel<TacoResource> recentResources=new TacoResourceAssembler().toCollectionModel(tacos);

        recentResources.add(
                linkTo(methodOn(RecentTacoController.class).recentTacos()).withRel("recents")
        );
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>> tacoProcesser(EntityLinks links){
        return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>() {
            @Override
            public PagedModel<EntityModel<Taco>> process(PagedModel<EntityModel<Taco>> model) {
                model.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents")
                )
                return model;
            }
        }
    }
}

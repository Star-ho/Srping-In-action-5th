package tacos.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.util.Optional;

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
    public Iterable<Taco> recentTacos(){
        Pageable page=PageRequest.of(0,12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page);
    }

    @GetMapping("/{id}")
    public Taco tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco=tacoRepo.findById(id);
        if(optTaco.isPresent()){
            return optTaco.get();
        }
        return null;
    }


}

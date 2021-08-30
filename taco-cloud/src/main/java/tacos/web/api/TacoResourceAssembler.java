package tacos.web.api;

//hateOs변경사항
///ResourceAssembler changed to RepresentationModelAssembler

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import tacos.Taco;
import tacos.web.DesignTacoController;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco,TacoResource> {

    public TacoResourceAssembler(){
        super(DesignTacoController.class, TacoResource.class);
    }

    @Override
    protected TacoResource instantiateModel(Taco taco){
        return new TacoResource(taco);
    }
    @Override
    public TacoResource toModel(Taco taco){
        return createModelWithId(taco.getId(),taco);
    }
}

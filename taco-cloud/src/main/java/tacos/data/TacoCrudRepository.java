package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import tacos.Taco;

public interface TacoCrudRepository extends CrudRepository<Taco,Long> {

}
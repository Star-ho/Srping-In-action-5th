package tacos.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.Order;
import tacos.User;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends ReactiveCassandraRepository<Order, UUID> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
package tacos.web;

//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
import org.aspectj.weaver.ast.Or;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.data.OrderRepository;
import tacos.User;

@Slf4j//로깅
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")//order 객체를 주입 후 세션이 끝날때까지 사용
//@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    private int pageSize=20;

//    public void setPageSize(int pageSize){
//        this.pageSize=pageSize;
//    }

    private OrderProps props;

    private OrderRepository orderRepo;
    public OrderController(OrderRepository orderRepo,OrderProps props){
        this.props=props;
        this.orderRepo=orderRepo;
    }

    @GetMapping
    public String ordersFroUser(@AuthenticationPrincipal User user, Model model){
        Pageable pageable= PageRequest.of(0,props.getPageSize());
        model.addAttribute("orders",orderRepo.findByUserOrderByCreatedAtDesc(user));

        return "orderList";
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order){
        if(order.getDeliveryCity() == null){
            order.setDeliveryCity(user.getCity());
        }
        if(order.getDeliveryStreet() == null){
            order.setDeliveryStreet(user.getStreet());
        }
        if(order.getDeliveryName() == null){
            order.setDeliveryName(user.getFullname());
        }
        if(order.getDeliveryZip() == null){
            order.setDeliveryZip(user.getZip());
        }
        if(order.getDeliveryState() == null){
            order.setDeliveryState(user.getState());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "orderForm";
        }
        order.setUser(user);
//        System.out.println(order.toString());
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PutMapping("/{orderId}")
    public Order putOrder(@RequestBody Order order){
        return orderRepo.save(order);
    }

    @PatchMapping(path = "/{orderId}",consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId,@RequestBody Order patch){
        Order order =orderRepo.findById(orderId).get();
        if(patch.getDeliveryState()!=null){
            order.setDeliveryState(patch.getDeliveryState());
        }//... 이후 if절 몇개 나옴

        return orderRepo.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId){
        try {
            orderRepo.deleteById(orderId);
        }catch (EmptyResultDataAccessException e){}
    }
}

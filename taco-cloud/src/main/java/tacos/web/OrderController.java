package tacos.web;

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
public class OrderController {

    private OrderRepository orderRepo;
    public OrderController(OrderRepository orderRepo){
        this.orderRepo=orderRepo;
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
}

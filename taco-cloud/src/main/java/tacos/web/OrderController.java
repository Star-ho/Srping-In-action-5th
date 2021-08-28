package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;
    public OrderController(OrderRepository orderRepo){
        this.orderRepo=orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
//        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus){
        if(errors.hasErrors()){
            return "orderForm";
        }
//        System.out.println(order.toString());
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}

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

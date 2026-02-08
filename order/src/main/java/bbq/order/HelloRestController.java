package bbq.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.core.KafkaTemplate;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;

import bbq.order.model.Cart;
import bbq.order.model.CartItem;
import bbq.order.model.Order;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class HelloRestController {

    // private final RabbitTemplate rabbitTemplate;
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final HumanReadableIdGenerator idGenerator = new PermutationBasedHumanReadableIdGenerator();

    @GetMapping
    public String get() {
        return "Hello Order!";
    }

    @GetMapping("/send")
    public String send() {
        var order = new Order();
        order.setId(idGenerator.generate());
        var cart = new Cart();
        cart.setTotal(BigDecimal.TEN);
        var cartItem = new CartItem();
        cartItem.setItemTitle("Burger");
        cartItem.setItemPrice(BigDecimal.TEN);
        cart.setItems(List.of(cartItem));
        order.setCart(cart);
        kafkaTemplate.send("orders", order);
        return "Done";
    }

}
package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.request.OrderRequest;
import kg.dev_abe.ecommerce.dto.request.OrderRequestFromCart;
import kg.dev_abe.ecommerce.dto.response.OrderResponse;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.exceptions.NotFoundException;
import kg.dev_abe.ecommerce.mappers.OrderItemMapper;
import kg.dev_abe.ecommerce.mappers.OrderMapper;
import kg.dev_abe.ecommerce.models.Order;
import kg.dev_abe.ecommerce.models.OrderItem;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import kg.dev_abe.ecommerce.repositories.OrderItemRepository;
import kg.dev_abe.ecommerce.repositories.OrderRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import kg.dev_abe.ecommerce.util.InvoiceGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private UserService userService;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;

    private CartService cartService;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

    private EmailService emailService;
    private InvoiceGenerator invoiceGenerator;

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toOrderResponse);
    }

    public List<OrderResponse> getUserOrders(Principal principal) {
        return orderRepository.findAllByUserEmail(principal.getName())
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow();
    }


    private void saveOrder(User user, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.AWAITING)
                .orderDate(LocalDate.now())
                .orderItems(orderItems)
                .totalPrice(orderItems.stream()
                        .mapToDouble(OrderItem::getTotalPrice)
                        .sum())
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepository.save(order);
    }


    @Async
    public void placeOrder(Principal principal, OrderRequestFromCart orderRequest) {
        User user = userService.findUserByEmail(principal.getName());


        List<OrderItem> orderItems = orderRequest.getCartItemIds().stream()
                .map(id -> cartService.getById(id))
                .map(orderItemMapper::toOrderItem)
                .toList();
        saveOrder(user, orderItems);
        cartService.clearCart(principal);

//        emailService.sendEmail(user.getEmail(), "Order placing", "Your order placed successfully");

    }


    public void createOrder(OrderRequest orderRequest, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItem -> {
                    Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(NotFoundException::new);
                    if (product.getAmount() < orderItem.getQuantity())
                        throw new ECommerceException("Not enough product in stock");
                    return OrderItem.builder()
                            .product(product)
                            .quantity(orderItem.getQuantity())
                            .totalPrice(product.getPrice() * orderItem.getQuantity())
                            .build();
                }).toList();


        saveOrder(user, orderItems);


    }

    @Async
    public void confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            throw new ECommerceException("Order already completed or canceled");

        soldProducts(order.getOrderItems(),true);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);


//        emailService.sendEmail(order.getUser().getEmail(), "Order confirmation", "Your order confirmed successfully");

    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Async
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);

        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            throw new ECommerceException("Order already completed or canceled");
        soldProducts(order.getOrderItems(),false);
        order.setOrderStatus(OrderStatus.CANCELED);

        orderRepository.save(order);
//        emailService.sendEmail(order.getUser().getEmail(), "Order cancellation", "Your order canceled successfully");
    }

    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            throw new ECommerceException("Order already completed or canceled");

        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

    }


    public ResponseEntity<byte[]> generateInvoice(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        byte[] pdfBytes = invoiceGenerator.generateInvoice(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "order-invoice.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    public void soldProducts(List<OrderItem> orderItems, boolean toSell) {
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();

            int quantity = orderItem.getQuantity();
            int sold = product.getSold();
            int amount = product.getAmount();

            if (toSell) {
                product.setAmount(amount - quantity);
                product.setSold(sold + quantity);
            } else {
                product.setAmount(amount + quantity);
                product.setSold(sold - quantity);
            }

        }
    }

}





package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
@NamedEntityGraph(
        name = "order-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("user")
        }
)


public class Order {
    private static final String SEQ_NAME = "order_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    private LocalDate orderDate;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}

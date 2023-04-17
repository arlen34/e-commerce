package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class OrderItem {
    private static final String SEQ_NAME = "order_item_seq";

    @Id
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 4)
    private Long orderItemId;
    private int quantity;
    @ManyToOne
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Order order;
    private double totalPrice;
}

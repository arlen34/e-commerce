package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
@Builder
public class CartItem {
    private static final String SEQ_NAME = "cart_item_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 4)
    private Long id;
    private Integer quantity;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Cart cart;
}

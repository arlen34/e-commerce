package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@NamedEntityGraph(
        name = "order-item-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "product",subgraph = "product-subgraph"),
                @NamedAttributeNode(value = "order",subgraph = "order-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(name = "product-subgraph",attributeNodes = {@NamedAttributeNode("imageList")} ),
                @NamedSubgraph(name = "order-subgraph",attributeNodes = {@NamedAttributeNode("user")} )

        }
)
public class OrderItem {
    private static final String SEQ_NAME = "order_item_seq";

    @Id
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 4)
    private Long orderItemId;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn
    private Order order;
    private double totalPrice;
}

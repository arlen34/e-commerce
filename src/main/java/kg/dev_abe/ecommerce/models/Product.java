package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import kg.dev_abe.ecommerce.dto.request.ProductCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@NamedEntityGraph(
        name = "product-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("imageList"),
                @NamedAttributeNode(value = "reviews", subgraph = "review-subgraph"),
                @NamedAttributeNode(value = "category",subgraph = "category-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "review-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("user")
                        }

                ),
                @NamedSubgraph(name = "category-subgraph",
                attributeNodes = {
                        @NamedAttributeNode("image")
                })

        }
)

public class Product {
    private static final String SEQ_NAME = "product_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 10)
    private long id;
    private double price;
    private int amount;
    @Column(name = "sold",columnDefinition = "int default 0")
    private int sold;

    private String productName;
    private String description;

    @Column(name = "receipt_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate receiptDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public Product(ProductCreateRequest request) {
        this.productName = request.getProductName();
        this.description = request.getDescription();
        this.price = request.getPrice();
        this.amount = request.getAmount();
        this.receiptDate = LocalDate.now();
    }
}



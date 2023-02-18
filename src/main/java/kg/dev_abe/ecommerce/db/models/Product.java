package kg.dev_abe.ecommerce.db.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    private static final String SEQ_NAME = "product_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME)
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer quantity;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();
}

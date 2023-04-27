package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Builder
public class Category {
    private static final String SEQ_NAME = "category_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 9)
    private Long id;

    private String categoryName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "category", orphanRemoval = true)
    private List<Product> products;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category parentCategory;

    @OneToMany(mappedBy ="parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;
}

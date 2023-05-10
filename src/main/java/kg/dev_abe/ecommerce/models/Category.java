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
@NamedEntityGraph(
        name = "category-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("image"),
        }
)
@NamedEntityGraph(
        name = "category-entity-graph-with-subcategories",
        attributeNodes = {
                @NamedAttributeNode("image"),
                @NamedAttributeNode("parentCategory"),
                @NamedAttributeNode(value = "categories", subgraph = "subcategory-subgraph")
        }, subgraphs = {
            @NamedSubgraph(
                    name = "subcategory-subgraph",
                    attributeNodes = {
                            @NamedAttributeNode("image")
                    }
            )
        }
)


public class Category {
    private static final String SEQ_NAME = "category_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 9)
    private Long id;

    private String categoryName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}

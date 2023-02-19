package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reviews")
public class Review {
    private static final String SEQ_NAME = "review_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME)
    private Long id;
    private String text;
    private LocalDate date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
}

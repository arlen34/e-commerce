package kg.dev_abe.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private String text;
    private String fullName;
    private LocalDate date;

}

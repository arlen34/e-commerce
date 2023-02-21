package kg.dev_abe.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String text;
    private String fullName;
    private LocalDate date;

}

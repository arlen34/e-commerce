package kg.dev_abe.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SimpleResponse {

    private String message;
    private String status;
}

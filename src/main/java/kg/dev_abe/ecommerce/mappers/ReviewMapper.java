package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ReviewResponse;
import kg.dev_abe.ecommerce.models.Review;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReviewMapper implements Function<Review,ReviewResponse> {

    @Override
    public ReviewResponse apply(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getText(),
                review.getUser().getName() + " " +review.getUser().getSurname(),
                review.getDate());
    }
}

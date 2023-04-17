package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ReviewResponse;
import kg.dev_abe.ecommerce.models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "fullName", expression = "java(review.getUser().getName() + \" \" + review.getUser().getSurname())")
    ReviewResponse toReviewResponse(Review review);

    @Mapping(source = "fullName", target = "user.name")
    @Mapping(expression = "java(\"\")", target = "user.surname")
    Review toReview(ReviewResponse reviewDTO);

}

package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.request.ReviewRequest;
import kg.dev_abe.ecommerce.dto.response.ReviewResponse;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.Review;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import kg.dev_abe.ecommerce.repositories.ReviewRepository;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void createFeedback(ReviewRequest request, Principal principal) {
        Optional<Product> product = productRepository.findById(request.getProductId());
        Optional<User> user = userRepository.findByEmail(principal.getName());
        reviewRepository.save(Review.builder()
                .text(request.getText())
                .date(LocalDate.now())
                .user(user.orElseThrow())
                .product(product.orElseThrow())
                .build());
    }

    public List<ReviewResponse> delete(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        reviewRepository.delete(review);
        return reviewRepository.findAllByProductId(review.getProduct().getId()).stream().map(r -> new ReviewResponse(r.getId(), r.getText(),
                r.getUser().getName() + " " + r.getUser().getSurname(), r.getDate())).collect(Collectors.toList());
    }
}

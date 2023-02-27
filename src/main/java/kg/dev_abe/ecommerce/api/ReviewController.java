package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.request.ReviewRequest;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.dto.response.ReviewResponse;
import kg.dev_abe.ecommerce.services.ProductService;
import kg.dev_abe.ecommerce.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Review API", description = "The reviews API")
public class ReviewController {
    private final ReviewService reviewService;
    private final ProductService productService;

    @Operation(summary = "Post the new feedback",
            description = "This endpoint returns a new created feedback with all feedbacks")
    @PostMapping
    public ProductResponse addFeedback(@RequestBody ReviewRequest request, Principal principal){
        reviewService.createFeedback(request, principal);
        return productService.getProductById(request.getProductId());
    }

    @Operation(summary = "Delete the feedback", description = "This endpoint returns a deleted feedback")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public List<ReviewResponse> delete(@PathVariable Long id){
        return reviewService.delete(id);
    }
}

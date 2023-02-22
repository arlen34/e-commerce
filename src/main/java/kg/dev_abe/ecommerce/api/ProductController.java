package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.request.ProductCreateRequest;
import kg.dev_abe.ecommerce.dto.request.ProductUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product API",description = "The products API for all ")
public class ProductController {
    private final ProductService productService;
    @Operation(summary = "Post the new product",
            description = "This endpoint returns a new created product with all products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public List<ProductResponse> addProduct(ProductCreateRequest request) {
        return productService.create(request);
    }


    @Operation(summary = "Get all products",
            description = "This endpoint returns all products")
    @GetMapping("/get-all/{categoryId}")
    public List<ProductResponse> getProductsByCategoryId(@PathVariable Long categoryId) {
        return productService.getAllProductsByCategoryId(categoryId);
    }
    @Operation(summary = "Get a product by id",
            description = "This endpoint returns product by product id")
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @Operation(summary = "Update the new product",
            description = "This endpoint returns a updated product with all products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping
    public ProductResponse updateProductById(@RequestBody ProductUpdateRequest request) {
        return productService.updateById(request);
    }

    @Operation(summary = "Delete the product",
            description = "This endpoint returns a deleted product with all products")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public List<ProductResponse> deleteProductById(@PathVariable Long id) {
        return productService.deleteById(id);
    }
}

package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.request.ProductCreateRequest;
import kg.dev_abe.ecommerce.dto.request.ProductUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Product API", description = "The products API for all ")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Post the new product",
            description = "This endpoint returns a new created product with all products")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PostMapping(path = "/add")
    public SimpleResponse addProduct(
            @RequestBody ProductCreateRequest request) {
        return productService.create(request);
    }

    @Operation(summary = "Add image to product",
            description = "This endpoint returns a new created product with all products")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PostMapping(path = "{id}/add-image/", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponse> addImage(@PathVariable Long id, @RequestParam("file") MultipartFile[] files) {
        return new ResponseEntity<>(productService.addImages(id, files), HttpStatus.OK);
    }

    @Operation(summary = "Delete image from product",description = "This endpoint delete image from product")
    @PreAuthorize("hasAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping(path ="{id}/delete/" )
    public ResponseEntity<ProductResponse> deleteImage(@PathVariable Long productId,Long imageId) {
        return new ResponseEntity<>(productService.deleteImage(productId,imageId),HttpStatus.OK);
    }


    @Operation(summary = "Get all products",
            description = "This endpoint returns all products")
    @GetMapping("/get-all/{categoryId}")
    public Page<ProductResponse> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 2) Pageable pageable
    ) {
        return productService.getAllProductsByCategoryId(categoryId, pageable);
    }

    @Operation(summary = "Get a product by id",
            description = "This endpoint returns product by product id")
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @Operation(summary = "Update the new product",
            description = "This endpoint returns a updated product with all products")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PatchMapping
    public ProductResponse updateProductById(@RequestBody ProductUpdateRequest request) {
        return productService.updateById(request);
    }

    @Operation(summary = "Delete the product",
            description = "This endpoint returns a deleted product with all products")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public Page<ProductResponse> deleteProductById(@PathVariable Long id) {
        return productService.deleteById(id);
    }

    @Operation(summary = "Search by product name",
            description = "This endpoint returns a searched products")
    @GetMapping("/search")
    public Page<ProductResponse> searchProduct(
            @RequestParam String name,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 2) Pageable pageable
    ) {
        return productService.searchProduct(name, pageable);
    }
}

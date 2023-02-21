package kg.dev_abe.ecommerce.api;

import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/all")
    public List<ProductResponse> getProducts() {
        return productService.findAll();
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);

    }
    @PostMapping("/add")
    public Product addProduct(Product product) {
        return productService.save(product);
    }
    @PutMapping("/update/{id}")
    public void updateProductById(@PathVariable Long id, Product product) {
        productService.updateById(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.response.ProductImageResponse;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.dto.response.ReviewResponse;
import kg.dev_abe.ecommerce.mappers.ProductResponseMapper;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(productResponseMapper).collect(Collectors.toList());
    }


    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    //
    public void updateById(Long id, Product product) {
        product.setId(id);
        productRepository.save(product);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product was not found"));
        return productResponseMapper.apply(product);
    }
}

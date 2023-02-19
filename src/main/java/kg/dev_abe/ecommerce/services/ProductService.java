package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ECommerceException("Product not found"));
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
}

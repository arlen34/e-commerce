package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.ProductCreateRequest;
import kg.dev_abe.ecommerce.dto.request.ProductUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.dto.response.ProductResponses;
import kg.dev_abe.ecommerce.mappers.ProductResponseMapper;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.ProductImage;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.CategoryRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;
    private final CategoryRepository categoryRepository;

    private final CartItemRepository cartItemRepository;

    public List<ProductResponses> create(ProductCreateRequest request) {
        Product product = new Product(request);
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("The category not found"));
        product.setCategory(category);
        product.setImageList(request.getImageRequests()
                .stream()
                .map(i -> new ProductImage(i.getImageUrl(), product))
                .collect(Collectors.toList()));
        productRepository.save(product);
        return getAllProductsByCategoryId(request.getCategoryId());
    }

    public List<ProductResponses> getAllProductsByCategoryId(Long categoryId) {
        List<ProductResponses> responses = new ArrayList<>();
        for (Product p : productRepository.getProductsByCategoryId(categoryId)){
            ProductResponses response = new ProductResponses(p.getId(), p.getProductName(), p.getDescription(),
                    p.getPrice(), p.getCategory().getCategoryName(), p.getReviews().size(), p.getImageList().get(0).getImageUrl());
            responses.add(response);
        }
        return responses;
    }


    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product was not found"));
        return productResponseMapper.apply(product);
    }

    @Transactional
    public ProductResponse updateById(ProductUpdateRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()->  new NotFoundException("Product with id=" + request.getProductId() + "not found"));
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setAmount(request.getAmount());
        product.setPrice(request.getPrice());
        return getProductById(product.getId());
    }

    public List<ProductResponses> deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Not found"));
        for (CartItem c : product.getCartItems()){
            cartItemRepository.updateForDelete(c.getId());
        }
        productRepository.delete(product);
        return getAllProductsByCategoryId(product.getCategory().getId());
    }
}

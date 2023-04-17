package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.ProductCreateRequest;
import kg.dev_abe.ecommerce.dto.request.ProductUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.ProductDetailsResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.mappers.ProductDetailsResponseMapper;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.ProductImage;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.CategoryRepository;
import kg.dev_abe.ecommerce.repositories.ProductImageRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailsResponseMapper productDetailsResponseMapper;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductImageRepository productImageRepository;

    public SimpleResponse create(ProductCreateRequest request) {
        Product product = new Product(request);
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("The category not found"));
        product.setCategory(category);
        productRepository.save(product);
        return new SimpleResponse("Successfully added", "SAVE");
    }



    public Page<ProductDetailsResponse> getAllProductsByCategoryId(Long categoryId, Pageable pageable){

        return productRepository.findByCategoryId(categoryId, pageable).map(productDetailsResponseMapper::toProductResponse);
    }


    public ProductDetailsResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product was not found"));
        return productDetailsResponseMapper.toProductResponse(product);
    }

    @Transactional
    public ProductDetailsResponse updateById(ProductUpdateRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()->  new NotFoundException("Product with id=" + request.getProductId() + "not found"));
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setAmount(request.getAmount());
        product.setPrice(request.getPrice());
        return getProductById(product.getId());
    }

    public Page<ProductDetailsResponse> deleteById(Long id) {
        Pageable pageable = Pageable.unpaged();
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Not found"));
        for (CartItem c : product.getCartItems()){
            cartItemRepository.updateForDelete(c.getId());
        }
        product.getCartItems().forEach(c -> cartItemRepository.updateForDelete(c.getId()));
        productRepository.delete(product);
        return getAllProductsByCategoryId(product.getCategory().getId(),pageable);
    }


    public ProductDetailsResponse addImages(Long id, MultipartFile[] files) {
            Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Not found"));
            List<ProductImage> productImages = new ArrayList<>();

            try {
                ProductImage productImage;
                for (MultipartFile file: files) {
                    productImage = new ProductImage();
                    productImage.setFileType(file.getContentType());
                    productImage.setImageData(file.getBytes());
                    productImage.setProduct(product);
                    productImages.add(productImage);
                }
                product.getImageList().addAll(productImages);
            } catch (IOException e) {
                e.printStackTrace();
            }
            productRepository.save(product);
            return productDetailsResponseMapper.toProductResponse(product);

    }

    public Page<ProductDetailsResponse> searchProduct(String name, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, name, pageable).map(productDetailsResponseMapper::toProductResponse);
    }

    public ProductDetailsResponse deleteImage(Long productId, Long imageId) {
        Product product = productRepository.findById(productId).get();
        product.getImageList().remove(productImageRepository.findById(imageId).get());
        productRepository.save(product);
        return productDetailsResponseMapper.toProductResponse(product);
    }
}

package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.ProductCreateRequest;
import kg.dev_abe.ecommerce.dto.request.ProductUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.ProductDetailsResponse;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.mappers.ProductMapper;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.models.Image;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductImageRepository productImageRepository;

    public SimpleResponse create(ProductCreateRequest request) {
        Product product = new Product(request);
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("The category not found"));
        product.setCategory(category);
        productRepository.save(product);
        return new SimpleResponse("Successfully added", "SAVE");
    }


    public Page<ProductResponse> getAllProductsByCategoryId(Long categoryId, Pageable pageable) {

        return productRepository.findAllByCategoryIdOrderByReceiptDateDesc(categoryId, pageable).map(productMapper::toProductResponse);
    }

    public ProductDetailsResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product was not found"));
        return productMapper.toProductDetailsResponse(product);
    }

    public ProductDetailsResponse updateById(ProductUpdateRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NotFoundException("Product with id=" + request.getProductId() + "not found"));
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setAmount(request.getAmount());
        product.setPrice(request.getPrice());
        return getProductById(product.getId());
    }

    public Page<ProductResponse> deleteById(Long id) {
        Pageable pageable = Pageable.unpaged();
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));

        cartItemRepository.deleteByProductId(id);
        orderItemRepository.deleteByProductId(id);

        productRepository.delete(product);
        return getAllProductsByCategoryId(product.getCategory().getId(), pageable);
    }


    public ProductDetailsResponse addImages(Long id, MultipartFile[] files) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));

        List<Image> images = Arrays.stream(files).map(Image::parseImage).toList();
        product.getImageList().addAll(images);

        productRepository.save(product);
        return productMapper.toProductDetailsResponse(product);

    }




    public ProductDetailsResponse deleteImage(Long productId, Long imageId) {
        Product product = productRepository.findById(productId).get();
        product.getImageList().remove(productImageRepository.findById(imageId).get());
        productRepository.save(product);
        return productMapper.toProductDetailsResponse(product);
    }


    public List<ProductResponse> getTopSoldProducts() {
        return productRepository
                .findTopBySoldOrderBySoldDesc()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getLatestProducts() {
        return productRepository.findLatestProducts()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public Map<String, List<ProductResponse>> getMainPage() {

        Map<String, List<ProductResponse>> body = new HashMap<>();
        List<ProductResponse> topSold = getTopSoldProducts();
        List<ProductResponse> latest = getLatestProducts();

        body.put("topSold", topSold);
        body.put("latest", latest);
        return body;
    }

    public Page<ProductResponse> searchProduct(String name, Long categoryId, Pageable pageable) {
        if (categoryId == null) {
            return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, name, pageable).map(productMapper::toProductResponse);
        } else {
            return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategoryId(name, name, categoryId, pageable).map(productMapper::toProductResponse);
        }
    }
}

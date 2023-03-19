package kg.dev_abe.ecommerce.mappers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersConfiguration {
    @Bean
    public ReviewMapper reviewMapper() {
        return ReviewMapper.INSTANCE;
    }
    @Bean
    public ProductImageMapper productImageMapper() {
        return ProductImageMapper.INSTANCE;
    }
    @Bean
    public ProductResponseMapper productResponseMapper() {
        return ProductResponseMapper.INSTANCE;
    }
}

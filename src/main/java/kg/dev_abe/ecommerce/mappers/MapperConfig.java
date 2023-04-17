package kg.dev_abe.ecommerce.mappers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ProductDetailsResponseMapper productDetailsResponseMapper() {
        return ProductDetailsResponseMapper.INSTANCE;
    }
}

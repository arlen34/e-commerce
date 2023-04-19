package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ImageDto;
import kg.dev_abe.ecommerce.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageMapper {

	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    @Mapping(source = "imageData", target = "data")
	ImageDto toImageDto(Image image);
}

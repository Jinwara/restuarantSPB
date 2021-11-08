package th.or.orcsofts.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import th.or.orcsofts.training.entity.ProductEntity;
import th.or.orcsofts.training.model.response.ProductResponse;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse entityToResponse(ProductEntity productEntity);

}

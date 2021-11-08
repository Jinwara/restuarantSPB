package th.or.orcsofts.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import th.or.orcsofts.training.entity.OrderItemEntity;
import th.or.orcsofts.training.model.response.OrderItemResponse;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemResponse entityToResponse(OrderItemEntity orderItemEntityrderEntity);

}

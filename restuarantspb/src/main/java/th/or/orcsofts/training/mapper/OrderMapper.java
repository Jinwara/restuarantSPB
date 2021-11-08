package th.or.orcsofts.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import th.or.orcsofts.training.entity.OrderEntity;
import th.or.orcsofts.training.model.response.OrderResponse;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponse entityToResponse(OrderEntity orderEntity);

}

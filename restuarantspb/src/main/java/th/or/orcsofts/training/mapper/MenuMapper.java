package th.or.orcsofts.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import th.or.orcsofts.training.entity.MenuEntity;
import th.or.orcsofts.training.model.response.MenuResponse;

@Mapper
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    MenuResponse entityToResponse(MenuEntity menuEntity);

}

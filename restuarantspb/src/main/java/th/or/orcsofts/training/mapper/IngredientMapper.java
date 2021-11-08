package th.or.orcsofts.training.mapper;

import th.or.orcsofts.training.model.response.IngredientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import th.or.orcsofts.training.entity.IngredientEntity;

@Mapper
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientResponse entityToResponse(IngredientEntity ingredientEntity);

}

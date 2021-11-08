package th.or.orcsofts.training.model.request;

import lombok.Data;
import th.or.orcsofts.training.entity.IngredientEntity;

import java.util.List;

@Data
public class MenuRequest {

    private String menuName;

    private List<IngredientEntity> ingredientList;

    private Double sellingPrice;

    private  String image;

}

package th.or.orcsofts.training.model.response;

import lombok.Data;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import th.or.orcsofts.training.entity.IngredientEntity;

import java.util.List;

@Data
public class MenuResponse {

    private String menuName;

    private Double sellingPrice;

    private Double cost;

    private Double profit;

    private String image;

    private List<IngredientEntity> ingredientList;

}

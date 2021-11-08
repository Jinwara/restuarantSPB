package th.or.orcsofts.training.model.response;

import lombok.Data;

@Data
public class IngredientResponse {

    private String ingredientName;

    private String unitType;

    private Integer quantity;

    private Double cost;

    private Double totalCost;
}

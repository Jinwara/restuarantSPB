package th.or.orcsofts.training.model.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class IngredientRequest {

    private String ingredientName;

    private Integer productID;

    private Integer quantity;

}

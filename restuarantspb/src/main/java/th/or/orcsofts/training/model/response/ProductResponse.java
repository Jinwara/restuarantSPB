package th.or.orcsofts.training.model.response;

import lombok.Data;

@Data
public class ProductResponse {

    private String productName;

    private String brand;

    private String unitType;

    private Double cost;

    private Double unitCost;

    private Double productWeight;
}

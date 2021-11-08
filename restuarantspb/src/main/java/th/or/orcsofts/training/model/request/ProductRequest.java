package th.or.orcsofts.training.model.request;

import lombok.Data;

@Data
public class ProductRequest {

    private String productName;

    private String brand;

    private String unitType;

    private Double cost;

    private Double productWeight;

}

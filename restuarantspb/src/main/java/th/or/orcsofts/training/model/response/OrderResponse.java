package th.or.orcsofts.training.model.response;

import lombok.Data;
import th.or.orcsofts.training.entity.OrderItemEntity;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {

    private Date date;

    private Double totalPrice;

    private Double totalProfit;

    private Double totalCost;

    private List<OrderItemEntity> orderitemList;

}

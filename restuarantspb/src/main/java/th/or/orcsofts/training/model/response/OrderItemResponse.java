package th.or.orcsofts.training.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import th.or.orcsofts.training.entity.OrderEntity;
import th.or.orcsofts.training.entity.OrderItemEntity;
import th.or.orcsofts.training.entity.ProductEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class OrderItemResponse {

    private ProductEntity menuID;

    private Integer quantity;

    private Double totalPrice;

    private String menuName;

    private Double  unitPrice;

    private OrderEntity orderID;

}

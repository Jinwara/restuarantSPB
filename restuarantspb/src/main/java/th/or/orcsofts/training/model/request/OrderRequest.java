package th.or.orcsofts.training.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import th.or.orcsofts.training.entity.IngredientEntity;
import th.or.orcsofts.training.entity.OrderItemEntity;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {

    private List<OrderItemEntity> orderitemList;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

}

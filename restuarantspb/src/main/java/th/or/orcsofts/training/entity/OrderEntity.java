package th.or.orcsofts.training.entity;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "orderitem_list")
    @OneToMany(mappedBy = "orderID")
    private List<OrderItemEntity> orderitemList;

    private Date date;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_profit")
    private Double totalProfit;

    @Column(name = "total_cost")
    private Double totalCost;


}

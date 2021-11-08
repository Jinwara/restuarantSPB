package th.or.orcsofts.training.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Data
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
@Table(name = "orderitem")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private MenuEntity menuID;

    @Column(name = "menu_name")
    private String menuName;

    private Integer quantity;

    @Column(name = " unit_price")
    private Double  unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    @JsonIgnore
    private OrderEntity orderID;

}

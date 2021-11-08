package th.or.orcsofts.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ingredient_name")
    private String ingredientName;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menu_id")
    @JsonIgnore
    private MenuEntity menuID;

    @Column(name = "unit_type")
    private String unitType;

    private Integer quantity;

    private Double cost;

    @Column(name = "total_cost")
    private Double totalCost;

}

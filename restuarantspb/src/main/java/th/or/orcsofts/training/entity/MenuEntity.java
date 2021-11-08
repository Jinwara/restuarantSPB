package th.or.orcsofts.training.entity;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "selling_price")
    private Double sellingPrice;

    private Double cost;

    private Double profit;

    @Column(name = "ingredient_list")
    @OneToMany(mappedBy = "menuID")
    private List<IngredientEntity> ingredientList;

    private String image;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(name = "imagedata", columnDefinition="BLOB")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] imagedata;

    @Transient
    public String getPhotosImagePath() {
        if (image == null || id == null) return null;

        return "/menu-photos/" + id + "/" + image;
    }

}

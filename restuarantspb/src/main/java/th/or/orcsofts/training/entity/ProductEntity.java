package th.or.orcsofts.training.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    private String brand;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "unit_cost")
    private Double unitCost;

    private Double cost;

    @Column(name = "product_weight")
    private Double productWeight;

    private String image;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(name = "imagedata", columnDefinition="BLOB")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] imagedata;

}

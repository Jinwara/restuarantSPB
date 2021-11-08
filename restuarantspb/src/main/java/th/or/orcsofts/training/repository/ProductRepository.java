package th.or.orcsofts.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import th.or.orcsofts.training.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT p FROM ProductEntity p WHERE lower(p.productName) LIKE lower(concat('%',?1,'%'))"
            + " OR lower(p.brand) LIKE lower(concat('%',?1,'%'))"
            + " OR lower(p.unitType) LIKE lower(concat('%',?1,'%'))"
            + " OR CONCAT(p.cost, '') LIKE %?1%")
    public List<ProductEntity> search(String keyword);
}

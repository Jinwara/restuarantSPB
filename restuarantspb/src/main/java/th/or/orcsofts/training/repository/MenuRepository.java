package th.or.orcsofts.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.or.orcsofts.training.entity.MenuEntity;


@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
}

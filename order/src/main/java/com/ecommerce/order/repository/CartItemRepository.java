package com.ecommerce.order.repository;

import com.ecommerce.order.model.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<Cartitem, Long> {

    Cartitem findByUserIdAndProductId(String userId, String productId);

    void deleteByUserIdAndProductId(String userId, String productId);

    List<Cartitem> findByUserId(String userId);

    void deleteByUserId(String userId);
}

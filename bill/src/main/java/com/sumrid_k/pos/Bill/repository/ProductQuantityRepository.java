package com.sumrid_k.pos.Bill.repository;

import com.sumrid_k.pos.Bill.model.ProductQuantity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuantityRepository extends CrudRepository<ProductQuantity, Long> {
}

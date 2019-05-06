package com.sumrid_k.pos.Report.repository;

import com.sumrid_k.pos.Report.model.ProductQuantity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuantityRepository extends CrudRepository<ProductQuantity, Long> {
}

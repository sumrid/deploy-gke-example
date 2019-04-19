package com.sumrid_k.pos.Bill.repository;

import com.sumrid_k.pos.Bill.model.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {
    ArrayList<Bill> findAll();
    Bill findById(long id);
}

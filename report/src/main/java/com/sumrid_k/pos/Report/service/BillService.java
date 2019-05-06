package com.sumrid_k.pos.Report.service;

import com.sumrid_k.pos.Report.model.Bill;
import com.sumrid_k.pos.Report.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BillService {
    @Autowired
    private BillRepository repository;

    public Bill save(Bill bill) {
        return repository.save(bill);
    }

    public ArrayList<Bill> findAll() {
        return (ArrayList<Bill>) repository.findAll();
    }
}

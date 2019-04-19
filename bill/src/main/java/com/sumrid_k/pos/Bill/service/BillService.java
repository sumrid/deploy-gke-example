package com.sumrid_k.pos.Bill.service;

import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // get by id
    public Bill getBill(long id) {
        return billRepository.findById(id);
    }

    // get all bills
    public ArrayList<Bill> getAll() {
        return billRepository.findAll();
    }

    // save
    public void saveBill(Bill bill) {
        billRepository.save(bill);
    }

    // delete
    public boolean deleteBill(long id) {
        Bill bill = billRepository.findById(id);

        if (bill != null) {
            billRepository.delete(bill);
            return true;
        } else {
            return false;
        }
    }

    // update
    public boolean updateBill(long id, Bill bill) {
        Bill result = billRepository.findById(id);

        if (result != null) {
            bill.setId(id);
            billRepository.save(bill);
            return true;
        } else {
            return false;
        }
    }
}

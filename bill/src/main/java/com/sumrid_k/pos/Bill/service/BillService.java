package com.sumrid_k.pos.Bill.service;

import com.sumrid_k.pos.Bill.model.Bill;
import com.sumrid_k.pos.Bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // Get by id
    public Bill getBill(long id) {
        return billRepository.findById(id);
    }

    // Get all bills
    public ArrayList<Bill> getAll() {
        return billRepository.findAll();
    }

    // Save
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    // Delete
    public boolean deleteBill(long id) {
        Bill bill = billRepository.findById(id);

        if (bill != null) {
            billRepository.delete(bill);
            return true;
        } else {
            return false;
        }
    }

    // Update
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

    public ArrayList<Bill> getByName(String name) {
        return billRepository.findAllByUserNameContains(name);
    }

    public ArrayList<Bill> getByDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(dateStr.equals("today")){
            return getByDateToday();
        } else {
            try {
                Date date = format.parse(dateStr);
                System.out.println(date.toString());
                return billRepository.findAllByDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    public ArrayList<Bill> getByDateToday() {
        return billRepository.findAllByDate(new Date());
    }

    public ArrayList<Bill> getByCompanyName(String companyName) {
        return billRepository.findAllByCompanyNameContains(companyName);
    }
}

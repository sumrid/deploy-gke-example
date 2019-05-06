package com.sumrid_k.pos.Report.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sumrid_k.pos.Report.ReportApplication;
import com.sumrid_k.pos.Report.model.*;
import com.sumrid_k.pos.Report.repository.BillRepository;
import com.sumrid_k.pos.Report.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class ReportService {

    private Logger logger = LoggerFactory.getLogger(ReportApplication.class);

    @Autowired
    private RestTemplate restTemplate;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    @Autowired
    private ReportRepository reportRepository;

    public void getDataAllServices() {
        // Get data from each service
        ResponseEntity responseBills = restTemplate.getForEntity("http://bill-service/bills", ArrayList.class);
//        ResponseEntity responseProducts = restTemplate.getForEntity("http://product-service/product", ArrayList.class);
//        ResponseEntity responseStocks = restTemplate.getForEntity("http://stock-service/stocks", ArrayList.class);

        logger.info(gson.toJson(responseBills.getBody()));
//        logger.info(gson.toJson(responseProducts.getBody()));
//        logger.info(gson.toJson(responseStocks.getBody()));

        // Convert json to object
        Type typeOfBill = new TypeToken<ArrayList<Bill>>() {}.getType();
        Type typeOfStock = new TypeToken<ArrayList<Stock>>() {}.getType();
//        Type typeOfProdoct = new TypeToken<List<Product>>() {}.getType();

        String billJson = gson.toJson(responseBills.getBody());
//        String stockJson = gson.toJson(responseStocks.getBody());
//        String productJson = gson.toJson(responseProducts.getBody());

        ArrayList<Bill> bills = gson.fromJson(billJson, typeOfBill);
//        ArrayList<Stock> stocks = gson.fromJson(stockJson, typeOfStock);
//        List<Product> products = gson.fromJson(productJson, typeOfProdoct);


        // Create new report
        Report report = new Report();
        report.setDate(new Date());
        report.setBestseller(gson.toJson(findBestSeller(bills)));
//        report.setLowInventory(gson.toJson(findLowInventory(stocks)));
        report.setIncome(calculateIncome(bills));
        report.setProfit(calculateProfit());

        // Save to database
        reportRepository.save(report);
    }

    public List<Report> getReports() {
        logger.debug("get report from database");
        return reportRepository.findAll();
    }

    public Product findBestSeller(ArrayList<Bill> bills) {
        Long bestSellerProduct;
        Map<Long, Integer> productCount = new HashMap<>();

        for(Bill bill : bills) {
            for(ProductQuantity productQuantity : bill.getProductQuantities()) {
                if(productQuantity != null) {
                    if(!productCount.containsKey(productQuantity.getProductId())) productCount.put(productQuantity.getProductId(), productQuantity.getQuantity());

                    int currentQuantity = productCount.get(productQuantity.getProductId()) + productQuantity.getQuantity();
                    productCount.put(productQuantity.getProductId(), currentQuantity);
                }
            }
        }
        bestSellerProduct = Collections.max(productCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        return  restTemplate.getForObject("http://product-service/"+bestSellerProduct, Product.class);
    }

    public Stock findLowInventory(ArrayList<Stock> stocks) {
        Stock tempProduct = new Stock();
        int tmpInventory = Integer.MAX_VALUE;

        for (Stock stock : stocks) {
            if (stock.getQuantity() < tmpInventory) {
                tempProduct = stock;
            }
        }

        return tempProduct;
    }

    public double calculateIncome(ArrayList<Bill> bills) {
        double totalIncome = 0;
        for(Bill bill : bills) {
            totalIncome += bill.getTotalPrice();
        }
        return totalIncome;
    }

    public double calculateProfit() {
        return 50000.5;
    }
}

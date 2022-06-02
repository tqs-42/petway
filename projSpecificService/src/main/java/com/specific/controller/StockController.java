package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.Stock;
import com.specific.service.StockService;

@RestController
@RequestMapping("/api/specific")
public class StockController {
    @Autowired
    private StockService service;

    @PostMapping("/addStock")
    public Stock addStock(@RequestBody Stock stock) {
        return service.saveStock(stock);
    }

    @PostMapping("/addStocks")
    public List<Stock> addStocks(@RequestBody List<Stock> stocks) {
        return service.saveStocks(stocks);
    }

    @GetMapping("/stocks")
    public List<Stock> findAllStocks() {
        return service.getStocks();
    }

    @GetMapping("/stockById/{id}")
    public Stock findStockById(@PathVariable int id) {
        return service.getStockById(id);
    }

    @PutMapping("/updateStock")
    public Stock updateStock(@RequestBody Stock stock) {
        return service.updateStock(stock);
    }

    @DeleteMapping("/deleteStock/{id}")
    public String deleteStock(@PathVariable int id) {
        return service.deleteStock(id);
    }
}

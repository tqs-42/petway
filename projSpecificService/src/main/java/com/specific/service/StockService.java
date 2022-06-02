package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Stock;
import com.specific.repository.StockRepository;

@Service
public class StockService {
    @Autowired
    private StockRepository repository;

    public Stock saveStock(Stock stock) {
        return repository.save(stock);
    }

    public List<Stock> saveStocks(List<Stock> stocks) {
        return repository.saveAll(stocks);
    }

    public List<Stock> getStocks() {
        return repository.findAll();
    }

    public Stock getStockById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteStock(long id) {
        repository.deleteById(id);
        return "stock removed !! " + id;
    }

    public Stock updateStock(Stock stock) {
        Stock existingStock = repository.findById(stock.getId()).orElse(null);
        existingStock.setAmount(stock.getAmount());
        existingStock.setProduct(stock.getProduct());
        return repository.save(existingStock);
    }
}

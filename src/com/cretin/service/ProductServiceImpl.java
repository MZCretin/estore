package com.cretin.service;

import com.cretin.dao.ProductDao;
import com.cretin.domain.Product;
import com.cretin.factory.BasicFactory;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = BasicFactory.getFactory().getDao(ProductDao.class);

    @Override
    public void addProduct(Product prod) {
        String id = UUID.randomUUID().toString();
        prod.setId(id);

        productDao.addProduct(prod);
    }

    /**
     * 获取所有的产品
     *
     * @return
     */
    @Override
    public List<Product> getProductLists() {
        return productDao.getProductLists();
    }

    @Override
    public Product getProductById(String id) {
        return productDao.getProductById(id);
    }
}

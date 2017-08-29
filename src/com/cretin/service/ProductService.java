package com.cretin.service;

import com.cretin.domain.Product;

import java.util.List;

public interface ProductService {
    /**
     * 添加产品
     * @param prod 产品信息bean
     */
    void addProduct(Product prod);

    /**
     * 获取所有的产品
     * @return
     */
    List<Product> getProductLists();

    /**
     * 根据id获取商品
     * @param id
     * @return
     */
    Product getProductById(String id);

}

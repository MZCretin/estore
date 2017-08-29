package com.cretin.dao;

import com.cretin.domain.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends Dao{
    /**
     * 添加商品
     *
     * @param prod 被添加的商品bean
     */
    void addProduct(Product prod);

    /**
     * 获取所有产品
     *
     * @return
     */
    List<Product> getProductLists();

    /**
     * 根据id获取商品信息
     *
     * @param id
     * @return
     */
    Product getProductById(String id);

    /**
     * 扣除商品数量
     *
     * @param connection
     * @param product_id 商品id
     * @param buynum     被扣除的数量
     */
    void deleNum(Connection connection, String product_id, int buynum) throws SQLException;
}

package com.cretin.dao;

import com.cretin.domain.Product;
import com.cretin.util.DaoUtils;
import com.cretin.util.TransactionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public void addProduct(Product prod) {
        String sql = "insert into products values(?,?,?,?,?,?,?)";
        try {
            QueryRunner queryRunner = new QueryRunner(DaoUtils.getSource());
            queryRunner.update(sql, prod.getId(), prod.getName(), prod.getPrice(), prod.getCategory(),
                    prod.getPnum(), prod.getImgurl(), prod.getDescription());
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getProductLists() {
        String sql = "select * from products";
        try {
            QueryRunner queryRunner = new QueryRunner(DaoUtils.getSource());
            return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductById(String id) {
        String sql = "select * from products where id=?";
        try {
            QueryRunner queryRunner = new QueryRunner(DaoUtils.getSource());
            return queryRunner.query(sql, new BeanHandler<Product>(Product.class), id);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleNum(String product_id, int buynum) throws SQLException {
        String sql = "update products set pnum=pnum-? where id=? and pnum-?>=0";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
        int count = queryRunner.update(sql, buynum, product_id, buynum);
        if ( count <= 0 ) {
            throw new SQLException("库存不足");
        }
    }

    @Override
    public void addPnum(String product_id, int buynum) {
        try {
            String sql = "update products set pnum=pnum+? where id=?";
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, buynum, product_id);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

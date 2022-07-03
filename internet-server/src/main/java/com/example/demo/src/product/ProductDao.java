package com.example.demo.src.product;


import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetProductRes getProductsByIdx(int proIdx){
        String getProductsByIdxQuery = "select proIdx,proImg from Product where proIdx=?";
        int getProductsByIdxParams = proIdx;
        return this.jdbcTemplate.queryForObject(getProductsByIdxQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("postIdx"),
                        rs.getString("proImg")),
                        getProductsByIdxParams);
    }






}
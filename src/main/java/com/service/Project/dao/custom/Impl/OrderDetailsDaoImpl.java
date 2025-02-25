package com.service.Project.dao.custom.Impl;


import com.service.Project.dao.custom.OrderDetailsDao;
import com.service.Project.entity.OrderDetails;
import com.service.Project.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 4:10 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(OrderDetails dto) throws SQLException {
        return CrudUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                dto.getOrderId(),
                dto.getItemId(),
                dto.getQuantity(),
                dto.getPrice()
        );
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }
}

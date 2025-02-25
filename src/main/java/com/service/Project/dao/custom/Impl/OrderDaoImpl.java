package com.service.Project.dao.custom.Impl;


import com.service.Project.dao.custom.OrderDao;
import com.service.Project.entity.Order;
import com.service.Project.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 12:15 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class OrderDaoImpl implements OrderDao {


    /*public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()) {

            String lastId = rst.getString(1); // e.g., "O002"

            String substring = lastId.substring(1); // e.g., "002"

            int i = Integer.parseInt(substring); // 2

            int newIdIndex = i + 1; // 3

            return String.format("O%03d", newIdIndex);
        }

        return "O001";
    }*/

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()){
            return rst.getString(1);
        }

        return null;
    }

    @Override
    public boolean save(Order dto) throws SQLException {
         return CrudUtil.execute("insert into orders values (?,?,?)",
                 dto.getOrderId(),
                 dto.getCustomerId(),
                 dto.getOrderDate()
         );
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }


//    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
//
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//
//            connection.setAutoCommit(false); // 1
//
//            boolean isOrderSaved = CrudUtil.execute(
//                    "insert into orders values (?,?,?)",
//                    orderDTO.getOrderId(),
//                    orderDTO.getCustomerId(),
//                    orderDTO.getOrderDate()
//            );
//
//            if (isOrderSaved) {
//
//                boolean isOrderDetailListSaved = orderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
//                if (isOrderDetailListSaved) {
//
//                    connection.commit(); // 2
//                    return true;
//                }
//            }
//
//            connection.rollback(); // 3
//            return false;
//        } catch (Exception e) {
//
//            connection.rollback();
//            return false;
//        } finally {
//
//            connection.setAutoCommit(true); // 4
//        }
//    }
}


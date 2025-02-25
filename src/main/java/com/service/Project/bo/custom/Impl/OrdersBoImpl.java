package com.service.Project.bo.custom.Impl;


import com.service.Project.bo.BOFactory;
import com.service.Project.bo.custom.ItemBo;
import com.service.Project.bo.custom.OrderDetailBo;
import com.service.Project.bo.custom.OrdersBo;
import com.service.Project.dao.DAOFactory;
import com.service.Project.dao.custom.OrderDao;
import com.service.Project.db.DBConnection;
import com.service.Project.entity.Order;
import com.service.Project.model.OrderDTO;
import com.service.Project.model.OrderDetailsDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBoImpl implements OrdersBo {

    OrderDao orderDao = (OrderDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailBo orderDetailBo = (OrderDetailBo) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERDETAIL);
    ItemBo itemBo = (ItemBo) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    public String getNextId() throws SQLException {
        String id = orderDao.getNextId();

        if (id != null) {

            String substring = id.substring(1); // e.g., "002"

            int i = Integer.parseInt(substring); // 2

            int newIdIndex = i + 1; // 3

            return String.format("O%03d", newIdIndex);
        }

        return "O001";
    }

    public boolean save(OrderDTO orderDTO) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        try {

            connection.setAutoCommit(false); // 1

//            boolean isOrderSaved = CrudUtil.execute(
//                    "insert into orders values (?,?,?)",
//                    orderDTO.getOrderId(),
//                    orderDTO.getCustomerId(),
//                    orderDTO.getOrderDate()
//            );

            Order order = new Order();
            order.setOrderId(orderDTO.getOrderId());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setCustomerId(orderDTO.getCustomerId());

            boolean isOrderSaved = orderDao.save(order);

            if (isOrderSaved) {

                boolean isOrderDetailListSaved = saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {

                    connection.commit(); // 2
                    return true;
                }
            }

            connection.rollback(); // 3
            return false;
        } catch (Exception e) {

            connection.rollback();
            return false;
        } finally {

            connection.setAutoCommit(true); // 4
        }
    }

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {

        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {

            boolean isOrderDetailsSaved = orderDetailBo.save(orderDetailsDTO);
            if (!isOrderDetailsSaved) {

                return false;
            }

            boolean isItemUpdated = itemBo.reduceQty(orderDetailsDTO);
            if (!isItemUpdated) {

                return false;
            }
        }

        return true;
    }
}

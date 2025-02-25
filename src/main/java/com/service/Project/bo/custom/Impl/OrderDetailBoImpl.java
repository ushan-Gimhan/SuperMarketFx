package com.service.Project.bo.custom.Impl;


import com.service.Project.bo.custom.OrderDetailBo;
import com.service.Project.dao.DAOFactory;
import com.service.Project.dao.custom.OrderDetailsDao;
import com.service.Project.entity.OrderDetails;
import com.service.Project.model.OrderDetailsDTO;

import java.sql.SQLException;

public class OrderDetailBoImpl implements OrderDetailBo {

    OrderDetailsDao orderDetailsDao = (OrderDetailsDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);

    @Override
    public boolean save(OrderDetailsDTO orderDetailsDTO) throws SQLException{

        OrderDetails dto = new OrderDetails();
        dto.setOrderId(orderDetailsDTO.getOrderId());
        dto.setItemId(orderDetailsDTO.getItemId());
        dto.setQuantity(orderDetailsDTO.getQuantity());
        dto.setPrice(orderDetailsDTO.getPrice());

        return orderDetailsDao.save(dto);
    }
}

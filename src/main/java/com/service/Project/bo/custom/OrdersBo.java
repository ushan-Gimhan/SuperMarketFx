package com.service.Project.bo.custom;


import com.service.Project.bo.SuperBO;
import com.service.Project.model.OrderDTO;
import com.service.Project.model.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBo extends SuperBO {
    String getNextId() throws SQLException;
    boolean save(OrderDTO orderDTO) throws SQLException;
    boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException;
}

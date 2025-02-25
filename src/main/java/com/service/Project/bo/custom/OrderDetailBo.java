package com.service.Project.bo.custom;


import com.service.Project.bo.SuperBO;
import com.service.Project.model.OrderDetailsDTO;

import java.sql.SQLException;

public interface OrderDetailBo extends SuperBO {
    boolean save(OrderDetailsDTO dto) throws SQLException;
}

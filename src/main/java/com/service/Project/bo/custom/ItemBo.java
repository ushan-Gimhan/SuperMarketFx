package com.service.Project.bo.custom;

import com.service.Project.bo.SuperBO;
import com.service.Project.model.ItemDTO;
import com.service.Project.model.OrderDetailsDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBo extends SuperBO {
    boolean save(ItemDTO item) throws SQLException;
    boolean update(ItemDTO item) throws SQLException;
    boolean delete(String id) throws SQLException;
    ArrayList<ItemDTO> getAll() throws SQLException;
    String getNextId() throws SQLException;
    ArrayList<String> getAllItemIds() throws SQLException;
    ItemDTO findById(String selectedItemId) throws SQLException;
    boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException;
}

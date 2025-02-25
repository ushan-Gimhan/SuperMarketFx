package com.service.Project.dao.custom;

import com.service.Project.dao.CrudDao;
import com.service.Project.entity.Item;
import com.service.Project.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDao extends CrudDao<Item> {
    ArrayList<String> getAllItemIds() throws SQLException;
    Item findById(String selectedItemId) throws SQLException;
    boolean reduceQty(OrderDetails orderDetails) throws SQLException;
}

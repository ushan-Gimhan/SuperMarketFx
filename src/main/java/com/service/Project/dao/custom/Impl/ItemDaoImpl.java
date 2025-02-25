package com.service.Project.dao.custom.Impl;


import com.service.Project.dao.custom.ItemDao;
import com.service.Project.entity.Item;
import com.service.Project.entity.OrderDetails;
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
 * Created: 10/9/2024 1:38 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(Item item) throws SQLException {
        return CrudUtil.execute(
                "insert into item values(?,?,?,?)",
                item.getItemId(),
                item.getItemName(),
                item.getQuantity(),
                item.getPrice()
        );
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return CrudUtil.execute(
                "update item set name = ?, quantity = ?, price = ? where item_id = ?",
                item.getItemName(),
                item.getQuantity(),
                item.getPrice(),
                item.getItemId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute(
                "delete from item where item_id = ?",
                id
        );
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item");

        ArrayList<Item> allItems = new ArrayList<>();

        while (rst.next()) {
            allItems.add(new Item(
                    rst.getString(1),  // Item ID
                    rst.getString(2),  // Item Name
                    rst.getInt(3),     // Item Quantity
                    rst.getDouble(4)   // Item Price
            ));
        }

        return allItems;
    }


    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from item order by item_id desc limit 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllItemIds() throws SQLException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("select item_id from item");


        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }


    @Override
    public Item findById(String selectedItemId) throws SQLException {

        ResultSet rst = CrudUtil.execute("select * from item where item_id=?", selectedItemId);

        if (rst.next()) {
            return new Item(
                    rst.getString(1),  // Item ID
                    rst.getString(2),  // Item Name
                    rst.getInt(3),     // Item Quantity
                    rst.getDouble(4)   // Item Price
            );
        }

        return null;
    }

    @Override
    public boolean reduceQty(OrderDetails orderDetails) throws SQLException {

        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                orderDetails.getQuantity(),   // Quantity to reduce
                orderDetails.getItemId()      // Item ID
        );
    }
}


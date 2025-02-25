package com.service.Project.bo.custom.Impl;

import com.service.Project.bo.custom.ItemBo;
import com.service.Project.dao.DAOFactory;
import com.service.Project.dao.custom.ItemDao;
import com.service.Project.entity.Item;
import com.service.Project.entity.OrderDetails;
import com.service.Project.model.ItemDTO;
import com.service.Project.model.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBo {

    ItemDao itemDao = (ItemDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean save(ItemDTO dto) throws SQLException {
        Item item = new Item();
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());

        return itemDao.save(item);
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException {
        Item item = new Item();
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());

        return itemDao.update(item);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return itemDao.delete(id);
    }

    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException {
        ArrayList<Item> items = itemDao.getAll();

        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item : items) {
            itemDTOS.add(new ItemDTO(item.getItemId(), item.getItemName(), item.getQuantity(), item.getPrice()));
        }

        return itemDTOS;
    }

    @Override
    public String getNextId() throws SQLException {
        String id = itemDao.getNextId();

        if (id != null){
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    @Override
    public ArrayList<String> getAllItemIds() throws SQLException {

        return itemDao.getAllItemIds();

    }


    @Override
    public ItemDTO findById(String selectedItemId) throws SQLException {

        Item item = itemDao.findById(selectedItemId);

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setItemId(item.getItemId());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setPrice(item.getPrice());

        return itemDTO;
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {

        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setItemId(orderDetailsDTO.getItemId());
        orderDetails.setOrderId(orderDetailsDTO.getOrderId());
        orderDetails.setQuantity(orderDetailsDTO.getQuantity());
        orderDetails.setPrice(orderDetailsDTO.getPrice());

        return itemDao.reduceQty(orderDetails);
    }
}

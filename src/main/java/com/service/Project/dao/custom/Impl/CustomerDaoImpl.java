package com.service.Project.dao.custom.Impl;

import com.service.Project.dao.custom.CustomerDao;
import com.service.Project.entity.Customer;
import com.service.Project.util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerDaoImpl implements CustomerDao {

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public Customer findById(String selectedCusId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
        }
        return null;
    }

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            return rst.getString(1);
        }

        return null;
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String substring = lastId.substring(1);
//            int i = Integer.parseInt(substring);
//            int newIdIndex = i + 1;
//            return String.format("C%03d", newIdIndex);
//        }
//        return "C001";
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone()
        );
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<Customer> customers = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                entity.getName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from customer where customer_id=?", id);
    }
}







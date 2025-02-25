package com.service.Project.dao.custom;

import com.service.Project.dao.CrudDao;
import com.service.Project.entity.Customer;
import com.service.Project.dao.DAOFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDao extends CrudDao<Customer> {
    ArrayList<String> getAllCustomerIds() throws SQLException;
    Customer findById(String selectedCusId) throws SQLException;
}

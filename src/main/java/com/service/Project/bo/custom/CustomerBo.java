package com.service.Project.bo.custom;


import com.service.Project.bo.SuperBO;
import com.service.Project.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBO {
    ArrayList<String> getAllCustomerIds() throws SQLException;
    CustomerDTO findById(String selectedCusId) throws SQLException;
    String getNextId() throws SQLException;
    boolean save(CustomerDTO dto) throws SQLException;
    ArrayList<CustomerDTO> getAll() throws SQLException;
    boolean update(CustomerDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
}

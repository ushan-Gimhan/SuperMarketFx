package com.service.Project.bo.custom.Impl;


import com.service.Project.bo.custom.CustomerBo;
import com.service.Project.dao.DAOFactory;
import com.service.Project.dao.custom.CustomerDao;
import com.service.Project.entity.Customer;
import com.service.Project.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDao = (CustomerDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerDao.getAllCustomerIds();
        return customerIds;
    }

    public CustomerDTO findById(String selectedCusId) throws SQLException {
        Customer customer = customerDao.findById(selectedCusId);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNic(customer.getNic());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());

        return customerDTO;
    }

    @Override
    public String getNextId() throws SQLException {
        String id = customerDao.getNextId();

        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    @Override
    public boolean save(CustomerDTO dto) throws SQLException {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setNic(dto.getNic());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        return customerDao.save(customer);
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException {
        ArrayList<Customer> customers = customerDao.getAll();

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getNic(),
                    customer.getEmail(),
                    customer.getPhone()
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;

    }

    @Override
    public boolean update(CustomerDTO dto) throws SQLException {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setNic(dto.getNic());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        return customerDao.update(customer);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return customerDao.delete(id);
    }
}

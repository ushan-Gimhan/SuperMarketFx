package com.service.Project.dao;

import com.service.Project.dao.custom.Impl.CustomerDaoImpl;
import com.service.Project.dao.custom.Impl.ItemDaoImpl;
import com.service.Project.dao.custom.Impl.OrderDaoImpl;
import com.service.Project.dao.custom.Impl.OrderDetailsDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        if (daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER, ITEM, ORDER, ORDERDETAIL
    }

    public SuperDao getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDaoImpl();
            case ITEM:
                return new ItemDaoImpl();
            case ORDER:
                return new OrderDaoImpl();
            case ORDERDETAIL:
                return new OrderDetailsDaoImpl();
            default:
                return null;
        }
    }
}

package com.service.Project.bo;

import com.service.Project.bo.custom.Impl.CustomerBoImpl;
import com.service.Project.bo.custom.Impl.ItemBoImpl;
import com.service.Project.bo.custom.Impl.OrderDetailBoImpl;
import com.service.Project.bo.custom.Impl.OrdersBoImpl;

public class BOFactory {
    private static BOFactory instance;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        if (instance == null) {
            instance = new BOFactory();
        }
        return instance;
    }

    public enum BOType{
        CUSTOMER, ITEM, ORDER, ORDERDETAIL;
    }
    public SuperBO getBO(BOType type){
        switch (type) {
            case CUSTOMER:
                return new CustomerBoImpl();
            case ITEM:
                return new ItemBoImpl();
            case ORDER:
                return new OrdersBoImpl();
            case ORDERDETAIL:
                return new OrderDetailBoImpl();
            default:
                return null;
        }
    }
}

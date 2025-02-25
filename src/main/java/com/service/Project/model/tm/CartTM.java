package com.service.Project.model.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javafx. scene. control. Button;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartTM {
    private String itemId;
    private String itemName;
    private int cartQuantity;
    private double unitPrice;
    private double total;
    private Button removeBtn;

}

package com.service.Project.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String itemId;
    private String itemName;
    private int quantity;
    private double price;
}

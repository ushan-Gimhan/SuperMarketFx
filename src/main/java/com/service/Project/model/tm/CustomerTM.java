package com.service.Project.model.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTM {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String phone;
}

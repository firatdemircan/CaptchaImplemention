package com.works.captchaimplemention.model.dto;

import lombok.Data;

@Data
public class LoggedUserModel {

    private String userName;

    public LoggedUserModel(String userName) {
        this.userName = userName;
    }
}

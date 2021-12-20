package com.works.captchaimplemention.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoggedUserModel {

    private String userName;

    public LoggedUserModel(String userName) {
        this.userName = userName;
    }
}

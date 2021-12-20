package com.works.captchaimplemention.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CaptchDTO {

    UUID uid;
    String captchaKey;
    String userName;
    String password;
}

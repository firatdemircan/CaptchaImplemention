package com.works.captchatest.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CaptchDTO {

    UUID uid;
    String captchaKey;
}

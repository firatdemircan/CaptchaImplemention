package com.works.captchatest.controller;

import com.works.captchatest.model.Captcha;
import com.works.captchatest.model.dto.CaptchDTO;
import com.works.captchatest.service.CaptchaService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class TestController {

    private final CaptchaService captchaService;

    public TestController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/captcha")
    public Object GenerateCaptcha(){

        Captcha captchaModel =captchaService.generateCaptcha();
        return captchaModel;

    }

    @PostMapping("/checkCaptcha")
    public Boolean CheckCaptcha(@RequestBody CaptchDTO captchDTO){

        return captchaService.validateCaptcha(captchDTO);
    }

}

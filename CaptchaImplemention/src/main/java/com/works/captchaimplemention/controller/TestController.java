package com.works.captchaimplemention.controller;

import com.works.captchaimplemention.model.Captcha;
import com.works.captchaimplemention.model.dto.CaptchDTO;
import com.works.captchaimplemention.service.CaptchaService;
import org.springframework.web.bind.annotation.*;

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

        if(captchaService.validateCaptcha(captchDTO)){
            //giriş yapmaya çalış
        }
        else{
            //captcha yanlış yeni capthca gönder
        }

        return captchaService.validateCaptcha(captchDTO);
    }

}

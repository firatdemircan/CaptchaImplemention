package com.works.captchaimplemention.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.works.captchaimplemention.model.Captcha;
import com.works.captchaimplemention.model.dto.CaptchDTO;
import com.works.captchaimplemention.service.CaptchaService;
import com.works.captchaimplemention.utils.security.base.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final CaptchaService captchaService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;



    @GetMapping("/captcha")
    public Object GenerateCaptcha(){

        Captcha captchaModel =captchaService.generateCaptcha();
        return captchaModel;

    }

    @PostMapping("/checkCaptcha")
    public Boolean CheckCaptcha(@RequestBody CaptchDTO captchDTO) throws JsonProcessingException {


        int x = 5;

        if(captchaService.validateCaptcha(captchDTO)){
            //giriş yapmaya çalış
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(captchDTO.getUserName(), captchDTO.getPassword()));
            String token = jwtUtil.generateToken(authenticate);
            System.out.println(token);
        }
        else{
            //captcha yanlış yeni capthca gönder
        }

        return captchaService.validateCaptcha(captchDTO);
    }

}

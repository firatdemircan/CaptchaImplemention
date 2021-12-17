package com.works.captchaimplemention.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.works.captchaimplemention.model.Captcha;
import com.works.captchaimplemention.model.dto.CaptchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CaptchaService {

    private final LoadingCache<UUID, String> captchaCache;

    @Autowired
    public CaptchaService() {
        captchaCache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(UUID key) throws Exception {
                        return null;
                    }
                });    }


    public Captcha generateCaptcha() {
        try {
            Captcha captcha = generateCaptchaImage();
            UUID captchaUuid = UUID.randomUUID();
            captchaCache.put(captchaUuid, captcha.captchaKey());
            return new Captcha(captchaUuid.toString(), captcha.val());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateCaptcha( CaptchDTO captcha) {

        String storedValue = captchaCache.getIfPresent(captcha.getUid());

        Map<UUID,String> hm = new HashMap<>();
        hm.put(captcha.getUid(),captcha.getCaptchaKey());



        boolean valid = storedValue.equals(captcha.getCaptchaKey());
        if (valid)
            return true;
        else
            return false;

    }

    public Captcha refreshCaptcha(UUID captchaKey) {
//        String oldCaptcha = captchaCache.getIfPresent(captchaKey);
//        if (Strings.isNullOrEmpty(oldCaptcha)) return null;
        captchaCache.invalidate(captchaKey);
        return generateCaptcha();
    }

    private Captcha generateCaptchaImage() throws IOException {
        //image için gerekli olan sabitler
        char[] chars = "ABCDEFGHJKLMNPQRSTUVWXYabcdefghjkmnpqrstuvwxy23456789".toCharArray();
        Color bgColor = Color.WHITE;
        Color borderColor = Color.BLACK;
        Color textColor = new Color(141,48,49);
        Color shuffleColor = new Color(183, 165, 150);
        Font textFont = new Font("Verdana", Font.BOLD, 30);
        int charsToPrint = 5;
        int width = 34*charsToPrint;
        int height = 50;
        int shuffleCount = 25;
        float horizMargin = 10.0f;
        double rotationRange = 0.7;

        //görüntüyü karıştırmak için image üstüne çizilecek random şekiller (rect)
        BufferedImage captchaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphic = (Graphics2D) captchaImage.getGraphics();
        graphic.setColor(bgColor);
        graphic.fillRect(0, 0, width, height);
        graphic.setColor(shuffleColor);
        for (int i = 0; i < shuffleCount; i++) {
            int l = (int) (Math.random() * height / 2.0);
            int x = (int) (Math.random() * width - l);
            int y = (int) (Math.random() * height - l);
            graphic.draw3DRect(x, y, l * 2, l * 2, true);
        }
        graphic.setColor(textColor);
        graphic.setFont(textFont);

        //captcha karakterleri
        FontMetrics fontMetrics = graphic.getFontMetrics();
        int maxAdvance = fontMetrics.getMaxAdvance();
        int fontHeight = fontMetrics.getHeight();
        float spaceForLetters = -horizMargin * 2 + width;
        float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);

        StringBuilder captchaBuffer = new StringBuilder();
        for (int i = 0; i < charsToPrint; i++) {
            //char havuzundan random char seçilir
            int index = (int) Math.round(Math.random() * (chars.length - 1));
            char currentChar = chars[index];
            captchaBuffer.append(currentChar);
            int charWidth = fontMetrics.charWidth(currentChar);
            int charDim = Math.max(maxAdvance, fontHeight);
            int halfCharDim = (int) (charDim / 2);
            BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
            Graphics2D charGraphics = charImage.createGraphics();
            charGraphics.translate(halfCharDim, halfCharDim);
            double angle = (Math.random() - 0.5) * rotationRange;
            charGraphics.transform(AffineTransform.getRotateInstance(angle));
            charGraphics.translate(-halfCharDim, -halfCharDim);
            charGraphics.setColor(textColor);
            charGraphics.setFont(textFont);
            int charX = (int) (0.5 * charDim - 0.5 * charWidth);
            charGraphics.drawString("" + currentChar, charX, (int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));
            float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
            int y = (height - charDim) / 2;
            graphic.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
            charGraphics.dispose();
        }
        //image border
        graphic.setColor(borderColor);
        graphic.drawRect(0, 0, width - 1, height - 1);
        graphic.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "png", outputStream);
        byte[] imgBytes = outputStream.toByteArray();
        return new Captcha(captchaBuffer.toString(), imgBytes);
    }


}

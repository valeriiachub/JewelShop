package com.epam.preprod.captcha;

import com.epam.preprod.service.impl.CaptchaServiceImpl;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/captcha-image.jpg")
public class CaptchaServlet extends HttpServlet {
    private CaptchaStorageStrategy strategy;
    private CaptchaServiceImpl captchaService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        drawCaptcha(request, response);
    }

    private void drawCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        strategy = (CaptchaStorageStrategy) request.getServletContext().getAttribute("captchaStorageStrategy");
        captchaService = (CaptchaServiceImpl) request.getServletContext().getAttribute("captchaService");
        response.setContentType("image/jpg");

        int currentCaptchaID = strategy.getCurrentCaptchaID(request, response);
        String sImageCode = captchaService.getCaptcha(currentCaptchaID).getCaptchaValue();
        BufferedImage biImage = createCaptchaImage(sImageCode);

        OutputStream osImage = response.getOutputStream();
        ImageIO.write(biImage, "jpeg", osImage);
    }

    private BufferedImage createCaptchaImage(String captchaContent) {
        BufferedImage image = new BufferedImage(150, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
        graphics.setFont(font);
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics.setRenderingHints(rh);
        GradientPaint gradientPaint = new GradientPaint(10, 5, new Color(228, 211, 110), 20, 10, Color.GRAY, true);
        graphics.setPaint(gradientPaint);
        graphics.fillRect(0, 0, 200, 40);
        graphics.setColor(new Color(135, 116, 110));
        graphics.drawString(captchaContent, 5, 30);
        graphics.dispose();
        return image;
    }

}




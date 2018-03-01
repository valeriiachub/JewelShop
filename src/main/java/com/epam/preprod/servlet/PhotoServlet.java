package com.epam.preprod.servlet;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/photo.jpg")
public class PhotoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        String photoPath = request.getParameter("imgUrl");

        File sourceImage = new File(photoPath);
        OutputStream osImage = response.getOutputStream();
        BufferedImage image = ImageIO.read(sourceImage);
        ImageIO.write(image, "jpeg", osImage);
    }
}

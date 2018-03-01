package com.epam.preprod.service.impl;

import com.epam.preprod.service.PhotoService;

import java.io.*;
import java.util.UUID;

public class PhotoServiceImpl implements PhotoService {

    @Override
    public String saveImage(String imgPath, InputStream fileContent) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        OutputStream out = new FileOutputStream(new File(imgPath + File.separator + fileName));
        int readBytes;
        final byte[] bytes = new byte[1024];

        while ((readBytes = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, readBytes);
        }

        return fileName;
    }
}

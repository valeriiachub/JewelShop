package com.epam.preprod.service;

import java.io.IOException;
import java.io.InputStream;

public interface PhotoService {
    String saveImage(String imgPath, InputStream fileContent) throws IOException;
}

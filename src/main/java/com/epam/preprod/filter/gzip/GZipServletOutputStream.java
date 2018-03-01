package com.epam.preprod.filter.gzip;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipServletOutputStream extends ServletOutputStream {
    private GZIPOutputStream outputStream;

    public GZipServletOutputStream(OutputStream outputStream) throws IOException {
        super();
        this.outputStream = new GZIPOutputStream(outputStream);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }

    @Override
    public void flush() throws IOException {
        this.outputStream.flush();
    }

    @Override
    public void write(byte b[]) throws IOException {
        this.outputStream.write(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        this.outputStream.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        this.outputStream.write(b);
    }
}

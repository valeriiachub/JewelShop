package com.epam.preprod.wrapper;

import com.epam.preprod.filter.gzip.GZipServletOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipServletResponseWrapper extends HttpServletResponseWrapper {
    private GZipServletOutputStream gzipOutputStream;
    private PrintWriter printWriter;

    public GZipServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void close() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
        if (gzipOutputStream != null) {
            gzipOutputStream.close();
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        gzipOutputStream.flush();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (printWriter != null) {
            throw new IllegalStateException("printWriter is obtained already");
        }
        if (gzipOutputStream == null) {
            gzipOutputStream = new GZipServletOutputStream(
                    getResponse().getOutputStream());
        }
        return gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null && gzipOutputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }
        if (printWriter == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
            printWriter = new PrintWriter(new OutputStreamWriter(gzipOutputStream, getResponse().getCharacterEncoding()));
        }
        return printWriter;
    }

    @Override
    public void setContentLength(int len) {
    }
}

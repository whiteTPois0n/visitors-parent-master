package ch.elca.visitors.service.service;

import com.google.zxing.WriterException;

import java.io.IOException;


public interface QrCodeGenService {

    void generateQrCode(String data, String filePath, int width, int height) throws IOException, WriterException;

}

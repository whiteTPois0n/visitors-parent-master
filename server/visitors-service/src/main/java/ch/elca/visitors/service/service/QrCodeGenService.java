package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.QrDto;
import com.google.zxing.WriterException;

import java.io.IOException;


public interface QrCodeGenService {

    void generateQrCode(String data, String filePath, int width, int height) throws IOException, WriterException;

    byte[] generateQRCodeImage(QrDto qrDto) throws IOException, WriterException;

}

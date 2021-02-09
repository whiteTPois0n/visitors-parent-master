package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.service.dto.QrDto;
import ch.elca.visitors.service.service.QrCodeGenService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QrCodeGenServiceImpl implements QrCodeGenService {

    @Override
    public void generateQrCode(String data, String filePath, int width, int height) throws IOException, WriterException {

        /* Initialize the com.google.zxing.qrcode.QRCodeWriter class */
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        /* QRCodeWriter encode method convert the data string into BitMatrix */
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        /* Initialize the path to write the QR image */
        Path path = FileSystems.getDefault().getPath(filePath);
        /* Save QR as Png to specified path  */
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


    @Override
    public byte[] generateQRCodeImage(QrDto qrDto) throws IOException, WriterException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(qrDto.getText(), BarcodeFormat.QR_CODE, qrDto.getWidth(), qrDto.getHeight());

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return pngOutputStream.toByteArray();
    }

}

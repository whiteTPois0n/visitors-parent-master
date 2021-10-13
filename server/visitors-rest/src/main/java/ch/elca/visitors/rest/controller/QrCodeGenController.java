package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.dto.QrDto;
import ch.elca.visitors.service.service.QrCodeGenService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qr")
@CrossOrigin()
public class QrCodeGenController {

    private final QrCodeGenService qrCodeGenService;


    @PostMapping("/generate-qr-image")
    public byte[] generateQrImage(@RequestBody QrDto qrDto) throws IOException, WriterException {
        return qrCodeGenService.generateQRCodeImage(qrDto);
    }

}

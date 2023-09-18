//package com.example.ibproekt2.service.impl;
//
//import com.example.ibproekt2.service.MFATokenManager;
//import dev.samstevens.totp.code.*;
//import dev.samstevens.totp.exceptions.QrGenerationException;
//import dev.samstevens.totp.qr.QrData;
//import dev.samstevens.totp.qr.QrGenerator;
//import dev.samstevens.totp.qr.ZxingPngQrGenerator;
//import dev.samstevens.totp.secret.DefaultSecretGenerator;
//import dev.samstevens.totp.secret.SecretGenerator;
//import dev.samstevens.totp.time.SystemTimeProvider;
//import dev.samstevens.totp.time.TimeProvider;
//import dev.samstevens.totp.util.Utils;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import static dev.samstevens.totp.util.Utils.getDataUriForImage;
//
//@Service("mfaTokenManager")
//public class DefaultMFATokenManager implements MFATokenManager {
//
//    @Resource
//    private SecretGenerator secretGenerator;
//
//    @Resource
//    private QrGenerator qrGenerator;
//
//    @Resource
//    private CodeVerifier codeVerifier;
//
//    @Override
//    public String generateSecretKey() {
//        return secretGenerator.generate();
//    }
//
//    @Override
//    public String getQRCode(String secret) throws QrGenerationException {
//        QrData data = new QrData.Builder().label("MFA")
//                .secret(secret)
//                .issuer("Java Development Journal")
//                .algorithm(HashingAlgorithm.SHA256)
//                .digits(6)
//                .period(30)
//                .build();
//        return  Utils.getDataUriForImage(
//                qrGenerator.generate(data),
//                qrGenerator.getImageMimeType()
//        );
//    }
//
//    @Override
//    public boolean verifyTotp(String code, String secret) {
//        return codeVerifier.isValidCode(secret, code);
//    }
//}
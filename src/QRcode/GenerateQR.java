
package QRcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class GenerateQR {
    public static void generateQRCode(String maSach) {
        int width = 300;
        int height = 300;
        String folderPath = "qr_codes/";
        String filePath = folderPath + maSach + ".png";

        try {
            // Tạo thư mục nếu chưa tồn tại
            File directory = new File(folderPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Tạo mã QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(maSach, BarcodeFormat.QR_CODE, width, height);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("Mã QR đã tạo: " + filePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}


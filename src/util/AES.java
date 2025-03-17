/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ACER
 */
public class AES {
    // Khóa bí mật AES (phải là 16 ký tự)

    private static final String SECRET_KEY = "MySecretKey12345";  // Khóa bí mật cho AES phải có độ dài 16 ký tự

// Hàm mã hóa AES
    public static String encrypt(String input) {
        try {
            // Tạo đối tượng khóa AES từ chuỗi khóa bí mật
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            // Khởi tạo đối tượng Cipher với thuật toán AES
            Cipher cipher = Cipher.getInstance("AES");

            // Thiết lập Cipher để mã hóa (ENCRYPT_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Mã hóa dữ liệu đầu vào
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            // Trả về dữ liệu đã mã hóa dưới dạng chuỗi Base64
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Nếu có lỗi trong quá trình mã hóa, ném ra một ngoại lệ với thông báo lỗi
            throw new RuntimeException("Error during AES encryption", e);
        }
    }

// Hàm giải mã AES (nếu cần dùng)
    public static String decrypt(String encryptedInput) {
        try {
            // Tạo đối tượng khóa AES từ chuỗi khóa bí mật
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            // Khởi tạo đối tượng Cipher với thuật toán AES
            Cipher cipher = Cipher.getInstance("AES");

            // Thiết lập Cipher để giải mã (DECRYPT_MODE)
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Giải mã dữ liệu đầu vào (dữ liệu đã mã hóa)
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));

            // Trả về dữ liệu đã giải mã dưới dạng chuỗi
            return new String(decryptedBytes);
        } catch (Exception e) {
            // Nếu có lỗi trong quá trình giải mã, ném ra một ngoại lệ với thông báo lỗi
            throw new RuntimeException("Error during AES decryption", e);
        }
    }
}

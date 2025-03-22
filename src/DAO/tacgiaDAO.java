/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.tacgia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class tacgiaDAO {

    public static void insert(tacgia tg) {
        String sql = "INSERT INTO tacgia (TenTacGia) VALUES (?)";
        int result = jdbchelper.executeUpdate(sql, tg.getTentacgia());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm tác giả thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm tác giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(tacgia tg) {
        String sql = "UPDATE tacgia SET TenTacGia = ? WHERE MaTacGia = ?";
        int result = jdbchelper.executeUpdate(sql, tg.getTentacgia(), tg.getMatacgia());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy tác giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String matacgia) {
        String sql = "DELETE FROM tacgia WHERE MaTacGia = ?";
        int result = jdbchelper.executeUpdate(sql, matacgia);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa tác giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<tacgia> getAll() {
        List<tacgia> tacgia = new ArrayList<>();
        String sql = "SELECT MaTacGia, TenTacGia FROM tacgia";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                tacgia tg = new tacgia();
                tg.matacgia = rs.getString("MaTacGia");
                tg.tentacgia = rs.getString("TenTacGia");
                tacgia.add(tg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tacgia;
    }
}

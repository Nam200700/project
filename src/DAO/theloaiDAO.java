/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Entity.theloai;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class theloaiDAO {

    public static void insert(theloai tl) {
        String sql = "INSERT INTO theloai (TenTheLoai) VALUES (?)";
        int result = jdbchelper.executeUpdate(sql, tl.getTentheloai());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm thể loại sách thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm thể loại sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(theloai tl) {
        String sql = "UPDATE theloai SET TenTheLoai = ? WHERE MaTheLoai = ?";
        int result = jdbchelper.executeUpdate(sql, tl.getTentheloai(), tl.getMatheloai());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy thể loại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String matheloai) {
        String sql = "DELETE FROM theloai WHERE MaTheLoai = ?";
        int result = jdbchelper.executeUpdate(sql, matheloai);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa thể loại sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<theloai> getAll() {
        List<theloai> theloai = new ArrayList<>();
        String sql = "SELECT MaTheLoai, TenTheLoai FROM theloai";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                theloai tl = new theloai();
                tl.matheloai = rs.getString("MaTheLoai");
                tl.tentheloai = rs.getString("TenTheLoai");
                theloai.add(tl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return theloai;
    }
}

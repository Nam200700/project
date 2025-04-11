/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import util.jdbchelper;

public class userDAO {

    private static Set<String> allowedFunctions = new HashSet<>();

    public static void setUserRole(String userName) {
        try {
            String sql = "SELECT DISTINCT c.TenChucNang "
                    + "FROM TAIKHOAN t "
                    + "JOIN PHANQUYEN_CHUCNANG pc ON t.MaQuyen = pc.MaQuyen "
                    + "JOIN CHUCNANG c ON pc.MaChucNang = c.MaChucNang "
                    + "WHERE t.TenDangNhap = ?";

            try (ResultSet rs = jdbchelper.executeQuery(sql, userName)) {
                allowedFunctions.clear();  // Xóa dữ liệu cũ
                while (rs.next()) {
                    String functionName = rs.getString("TenChucNang");
                    allowedFunctions.add(functionName);
                    System.out.println("Allowed function: " + functionName);  // Debug: Kiểm tra quyền
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user role: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Set<String> getAllowedFunctions() {
        return allowedFunctions;
    }

    public static boolean insertUser(user u) {
        String sql = "INSERT INTO TAIKHOAN (TenDangNhap, MatKhau, Email, MaQuyen) VALUES (?, ?, ?, ?)";
        int rows = jdbchelper.executeUpdate(
                sql,
                u.getFullname(),
                u.getPassword(),
                u.getEmail(),
                u.getMaQuyen()
        );
        return rows > 0;
    }

    public static int getMaQuyenFromTenQuyen(String tenQuyen) {
        String sql = "SELECT MaQuyen FROM PHANQUYEN WHERE TenQuyen = ?";
        try (ResultSet rs = jdbchelper.executeQuery(sql, tenQuyen)) {
            if (rs != null && rs.next()) {
                return rs.getInt("MaQuyen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    public static void loadTenQuyenToComboBox(JComboBox<String> cbo) {
        String sql = "SELECT TenQuyen FROM PHANQUYEN";
        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            cbo.removeAllItems();
            while (rs.next()) {
                cbo.addItem(rs.getString("TenQuyen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<user> getAll() {
        List<user> list = new ArrayList<>();
        String sql = "SELECT tk.MaTaiKhoan, tk.TenDangNhap, tk.Email, tk.MatKhau, pq.MaQuyen, pq.TenQuyen "
                + "FROM TAIKHOAN tk "
                + "JOIN PHANQUYEN pq ON tk.MaQuyen = pq.MaQuyen "
                + "WHERE pq.TenQuyen = N'Thủ thư'"; // Chỉ lấy Thủ thư

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                user u = new user();
                u.setMaTaiKhoan(rs.getInt("MaTaiKhoan"));
                u.setFullname(rs.getString("TenDangNhap"));
                u.setEmail(rs.getString("Email"));
                u.setTenQuyen(rs.getString("TenQuyen"));

                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean updateUser(user u) {
        String sql = "UPDATE TAIKHOAN SET TenDangNhap = ?, MatKhau = ?, Email = ?, MaQuyen = ? WHERE MaTaiKhoan = ?";
        int rows = jdbchelper.executeUpdate(
                sql,
                u.getFullname(),
                u.getPassword(),
                u.getEmail(),
                u.getMaQuyen(),
                u.getMaTaiKhoan()
        );
        return rows > 0;
    }

    public static boolean deleteUser(int maTaiKhoan) {
        String sql = "DELETE FROM TAIKHOAN WHERE MaTaiKhoan = ?";
        int rows = jdbchelper.executeUpdate(sql, maTaiKhoan);
        return rows > 0;
    }

}

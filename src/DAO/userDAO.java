/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
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
}

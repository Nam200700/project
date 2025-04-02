/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.SachDAO;
import Entity.Sach;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class QL_sach extends TabbedForm {

    List<Sach> dsSach = new ArrayList<Sach>();

    /**
     * Creates new form QL_sach
     */
    public QL_sach() {
        initComponents();
        loadNhaXuatBanID();
        loadTacGiaID();
        loadTheLoaiID();
        guitrasach();
        fillTable();
    }

    public void guitrasach() {
        // Bo góc cho JTextField

        txt_tensach.putClientProperty("JComponent.roundRect", true);
        txt_tensach.putClientProperty("JTextField.placeholderText", "Nhập tên sách...");
        txt_tensach.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_madausach.putClientProperty("JComponent.roundRect", true);
        txt_madausach.putClientProperty("JTextField.placeholderText", "Nhập mã đầu sách...");
        txt_madausach.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_namxuatban.putClientProperty("JComponent.roundRect", true);
        txt_namxuatban.putClientProperty("JTextField.placeholderText", "Nhập năm xuất bản...");
        txt_namxuatban.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_ngonngu.putClientProperty("JComponent.roundRect", true);
        txt_ngonngu.putClientProperty("JTextField.placeholderText", "Nhập ngôn ngữ sách...");
        txt_ngonngu.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_soluong.putClientProperty("JComponent.roundRect", true);
        txt_soluong.putClientProperty("JTextField.placeholderText", "Nhập số lượng sách...");
        txt_soluong.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_lantainban.putClientProperty("JComponent.roundRect", true);
        txt_lantainban.putClientProperty("JTextField.placeholderText", "Nhập lần tái bản...");
        txt_lantainban.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_timkiem.putClientProperty("JTextField.placeholderText", "Vui lòng nhập tên hoặc mã để tìm kiếm...");
        // Cập nhật lại JButton với bo góc
        btn_them.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_capnhat.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

    }

    // load dữ liệu mã thể loại
    private void loadTheLoaiID() {
        String query = getSelectTheLoaiCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_theloai.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maTheLoai = rs.getString("MaTheLoai"); // Lấy mã thể loại từ ResultSet
                cbb_theloai.addItem(maTheLoai); // Thêm mã thể loại vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách thể loại.");
        }
    }

    private String getSelectTheLoaiCodeQuery() {
        return "SELECT MaTheLoai FROM theloai"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    // load dữ liệu mã tác giả
    private void loadTacGiaID() {
        String query = getSelectTacGiaCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_tacgia.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maTacGia = rs.getString("MaTacGia"); // Lấy mã tác giả từ ResultSet
                cbb_tacgia.addItem(maTacGia); // Thêm mã tác giả vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tác giả.");
        }
    }

    private String getSelectTacGiaCodeQuery() {
        return "SELECT MaTacGia FROM tacgia"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    // load dữ liệu mã nhà xuất bản
    private void loadNhaXuatBanID() {
        String query = getSelectNhaXuatBanCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_nhaxuatban.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maNhaXuatBan = rs.getString("MaNhaXuatBan"); // Lấy mã nhà xuất bản từ ResultSet
                cbb_nhaxuatban.addItem(maNhaXuatBan); // Thêm mã nhà xuất bản vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách nhà xuất bản.");
        }
    }

    private String getSelectNhaXuatBanCodeQuery() {
        return "SELECT MaNhaXuatBan FROM nhaxuatban"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    public void addSach() {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (txt_tensach.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (cbb_theloai.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (cbb_tacgia.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tác giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (cbb_nhaxuatban.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà xuất bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txt_madausach.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đầu sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txt_namxuatban.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập năm xuất bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txt_soluong.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txt_lantainban.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lần tái bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Ép kiểu dữ liệu từ chuỗi sang số nguyên
            int maTheLoai = Integer.parseInt(cbb_theloai.getSelectedItem().toString());
            int maTacGia = Integer.parseInt(cbb_tacgia.getSelectedItem().toString());
            int maNhaXuatBan = Integer.parseInt(cbb_nhaxuatban.getSelectedItem().toString());
            int namXuatBan = Integer.parseInt(txt_namxuatban.getText().trim());
            int soLuong = Integer.parseInt(txt_soluong.getText().trim());
            int lanTaiBan = Integer.parseInt(txt_lantainban.getText().trim());

            // Tạo đối tượng sách
            Sach sach = new Sach();
            sach.setTenSach(txt_tensach.getText().trim());
            sach.setMaTheLoai(maTheLoai);
            sach.setMaTacGia(maTacGia);
            sach.setMaNhaXuatBan(maNhaXuatBan);
            sach.setMaDauSach(txt_madausach.getText().trim());
            sach.setNamXuatBan(namXuatBan);
            sach.setNgonNgu(txt_ngonngu.getText().trim());
            sach.setSoLuong(soLuong);
            sach.setLanTaiBan(lanTaiBan);

            // Thêm vào cơ sở dữ liệu
            boolean isSuccess = SachDAO.insert(sach);
            if (isSuccess) {
                dsSach.add(sach);
                fillTable();
                clean();
                JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sách thất bại! Kiểm tra lại dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu! Hãy nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateSach() {
        // Lấy dòng được chọn trong bảng
        int index = tbl_sach.getSelectedRow();
        if (index == -1 || index >= dsSach.size()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sách nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra dữ liệu đầu vào
        if (txt_tensach.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbb_theloai.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbb_tacgia.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tác giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbb_nhaxuatban.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà xuất bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txt_madausach.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đầu sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txt_namxuatban.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm xuất bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txt_soluong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txt_lantainban.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lần tái bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Ép kiểu dữ liệu từ chuỗi sang số nguyên
            int maTheLoai = Integer.parseInt(cbb_theloai.getSelectedItem().toString());
            int maTacGia = Integer.parseInt(cbb_tacgia.getSelectedItem().toString());
            int maNhaXuatBan = Integer.parseInt(cbb_nhaxuatban.getSelectedItem().toString());
            int namXuatBan = Integer.parseInt(txt_namxuatban.getText().trim());
            int soLuong = Integer.parseInt(txt_soluong.getText().trim());
            int lanTaiBan = Integer.parseInt(txt_lantainban.getText().trim());

            // Cập nhật thông tin sách
            Sach sach = dsSach.get(index);
            sach.setTenSach(txt_tensach.getText().trim());
            sach.setMaTheLoai(maTheLoai);
            sach.setMaTacGia(maTacGia);
            sach.setMaNhaXuatBan(maNhaXuatBan);
            sach.setMaDauSach(txt_madausach.getText().trim());
            sach.setNamXuatBan(namXuatBan);
            sach.setNgonNgu(txt_ngonngu.getText().trim());
            sach.setSoLuong(soLuong);
            sach.setLanTaiBan(lanTaiBan);

            // Cập nhật vào cơ sở dữ liệu
            SachDAO.update(sach);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu! Hãy nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeSach() {
        int[] selectedRows = tbl_sach.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sách nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int index = selectedRows[i];
                int maSach = (int) tbl_sach.getValueAt(index, 0);
                boolean isDeleted = SachDAO.delete(maSach);
                if (isDeleted) {
                    dsSach.removeIf(sach -> sach.getMaSach() == maSach);
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa sách có mã " + maSach + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            fillTable();
            JOptionPane.showMessageDialog(this, "Xóa sách thành công!");
            if (tbl_sach.getRowCount() > 0) {
                int newIndex = Math.min(selectedRows[0], tbl_sach.getRowCount() - 1);
                tbl_sach.setRowSelectionInterval(newIndex, newIndex);
                loadRowIndexField(newIndex);
            } else {
                clean();
            }
        }
    }

    public void fillTable() {
        List<Sach> sachList = SachDAO.getAll();

        dsSach.clear();
        dsSach.addAll(sachList);

        DefaultTableModel model = (DefaultTableModel) tbl_sach.getModel();
        model.setRowCount(0);

        for (Sach sach : dsSach) {
            Object[] row = new Object[]{sach.getMaSach(), sach.getTenSach(), sach.getMaTheLoai(), sach.getMaTacGia(), sach.getMaNhaXuatBan(), sach.getMaDauSach(), sach.getNamXuatBan(), sach.getNgonNgu(), sach.getSoLuong(), sach.getLanTaiBan()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int rowIndex) {
        int maSach = (int) tbl_sach.getValueAt(rowIndex, 0);
        String tenSach = (String) tbl_sach.getValueAt(rowIndex, 1);
        int maTheLoai = (int) tbl_sach.getValueAt(rowIndex, 2);
        int maTacGia = (int) tbl_sach.getValueAt(rowIndex, 3);
        int maNhaXuatBan = (int) tbl_sach.getValueAt(rowIndex, 4);
        String maDauSach = (String) tbl_sach.getValueAt(rowIndex, 5);
        int namXuatBan = (int) tbl_sach.getValueAt(rowIndex, 6);
        String ngonNgu = (String) tbl_sach.getValueAt(rowIndex, 7);
        int soLuong = (int) tbl_sach.getValueAt(rowIndex, 8);
        int lanTaiBan = (int) tbl_sach.getValueAt(rowIndex, 9);

        txt_tensach.setText(tenSach);
        cbb_theloai.setSelectedItem(maTheLoai);
        cbb_tacgia.setSelectedItem(maTacGia);
        cbb_nhaxuatban.setSelectedItem(maNhaXuatBan);
        txt_madausach.setText(maDauSach);
        txt_namxuatban.setText(String.valueOf(namXuatBan));
        txt_ngonngu.setText(ngonNgu);
        txt_soluong.setText(String.valueOf(soLuong));
        txt_lantainban.setText(String.valueOf(lanTaiBan));
    }

    public void clean() {
        txt_tensach.setText("");
        cbb_theloai.setSelectedIndex(0);
        cbb_tacgia.setSelectedIndex(0);
        cbb_nhaxuatban.setSelectedIndex(0);
        txt_madausach.setText("");
        txt_namxuatban.setText("");
        txt_ngonngu.setText("");
        txt_soluong.setText("");
        txt_lantainban.setText("");
    }

    public void clickSach() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tbl_sach.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbl_sach.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String tenSach = tbl_sach.getValueAt(row, 1).toString();       // Tên sách
            String theLoai = tbl_sach.getValueAt(row, 2).toString();       // Thể loại
            String tacGia = tbl_sach.getValueAt(row, 3).toString();        // Tác giả
            String nhaXuatBan = tbl_sach.getValueAt(row, 4).toString();    // Nhà xuất bản
            String maDauSach = tbl_sach.getValueAt(row, 5).toString();     // Mã đầu sách
            String namXuatBan = tbl_sach.getValueAt(row, 6).toString();    // Năm xuất bản
            String ngonNgu = tbl_sach.getValueAt(row, 7).toString();       // Ngôn ngữ
            String soLuong = tbl_sach.getValueAt(row, 8).toString();       // Số lượng
            String lanTaiBan = tbl_sach.getValueAt(row, 9).toString();     // Lần tái bản

            // Cập nhật các trường nhập liệu
            txt_tensach.setText(tenSach);
            cbb_theloai.setSelectedItem(theLoai);
            cbb_tacgia.setSelectedItem(tacGia);
            cbb_nhaxuatban.setSelectedItem(nhaXuatBan);
            txt_madausach.setText(maDauSach);
            txt_namxuatban.setText(namXuatBan);
            txt_lantainban.setText(lanTaiBan);
            txt_ngonngu.setText(ngonNgu);
            txt_soluong.setText(soLuong);

        } else {
            // Nếu không có dòng nào được chọn
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new swing.RoundedPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_sach = new javax.swing.JTable();
        txt_timkiem = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_capnhat = new javax.swing.JButton();
        txt_tensach = new javax.swing.JTextField();
        cbb_theloai = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbb_tacgia = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbb_nhaxuatban = new javax.swing.JComboBox<>();
        txt_madausach = new javax.swing.JTextField();
        txt_namxuatban = new javax.swing.JTextField();
        txt_ngonngu = new javax.swing.JTextField();
        txt_soluong = new javax.swing.JTextField();
        txt_lantainban = new javax.swing.JTextField();
        tbl_don = new javax.swing.JLabel();

        setOpaque(false);

        roundedPanel1.setPreferredSize(new java.awt.Dimension(1050, 547));

        tbl_sach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Thể loại sách", "Tác giả", "Nhà xuất bản", "Mã đầu sách", "Năm xuất bản", "Ngôn ngữ ", "Số lượng", "Lần tái bản"
            }
        ));
        tbl_sach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_sach);

        jButton4.setText("Tìm kiếm");

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_xoa.setText("Xóa");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_capnhat.setText("Sửa");
        btn_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatActionPerformed(evt);
            }
        });

        cbb_theloai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Thể loại sách");

        jLabel2.setText("Tác giả");

        cbb_tacgia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Nhà xuất bản");

        cbb_nhaxuatban.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_madausach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_madausachActionPerformed(evt);
            }
        });

        txt_lantainban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lantainbanActionPerformed(evt);
            }
        });

        tbl_don.setText("Dọn");
        tbl_don.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_donMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(txt_namxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_ngonngu, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_lantainban, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(cbb_theloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(29, 29, 29)
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                                .addComponent(cbb_tacgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cbb_nhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(55, 55, 55)
                                                .addComponent(jLabel3))))
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(125, 125, 125)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txt_madausach, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(78, 78, 78))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_them, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                .addComponent(btn_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_capnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(tbl_don, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(txt_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_madausach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_theloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_tacgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_nhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 28, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_namxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ngonngu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_lantainban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_them)
                        .addGap(36, 36, 36)
                        .addComponent(btn_capnhat)
                        .addGap(38, 38, 38)
                        .addComponent(btn_xoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tbl_don)))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        addSach();
    }//GEN-LAST:event_btn_themActionPerformed

    private void txt_madausachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_madausachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_madausachActionPerformed

    private void txt_lantainbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lantainbanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lantainbanActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeSach();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatActionPerformed
        updateSach();
    }//GEN-LAST:event_btn_capnhatActionPerformed

    private void tbl_sachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sachMouseClicked
        clickSach();
    }//GEN-LAST:event_tbl_sachMouseClicked

    private void tbl_donMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_donMouseClicked
        clean();
    }//GEN-LAST:event_tbl_donMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbb_nhaxuatban;
    private javax.swing.JComboBox<String> cbb_tacgia;
    private javax.swing.JComboBox<String> cbb_theloai;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JLabel tbl_don;
    private javax.swing.JTable tbl_sach;
    private javax.swing.JTextField txt_lantainban;
    private javax.swing.JTextField txt_madausach;
    private javax.swing.JTextField txt_namxuatban;
    private javax.swing.JTextField txt_ngonngu;
    private javax.swing.JTextField txt_soluong;
    private javax.swing.JTextField txt_tensach;
    private javax.swing.JTextField txt_timkiem;
    // End of variables declaration//GEN-END:variables
}

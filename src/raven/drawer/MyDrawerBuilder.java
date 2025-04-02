package raven.drawer;

import DAO.userDAO;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import main.main;
import raven.swing.AvatarIcon;
import ui.QL_docgia;
import ui.QL_nhaxuatban;
import ui.QL_tacgia;
import ui.QL_theloaisach;
import ui.Test;
import java.util.Set;
import ui.QL_Muon;
import ui.QL_ThongTinTheDocGia;
import ui.QL_Tra;
import ui.QL_chitietphieumuon;
import ui.QL_khuvucsach;
import ui.QL_mail;
import ui.QL_sach;

public class MyDrawerBuilder extends SimpleDrawerBuilder {

    private static String userRole = "";  // Biến toàn cục để lưu quyền người dùng
    private static String userName = "";

    public static void setUserRole(String role) {
        userRole = role;  // Cập nhật quyền người dùng
    }

    public static void setuserName(String name) {
        userName = name;  // Cập nhật quyền người dùng
    }

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/image/profile.png"), 65, 65, 999))
                .setTitle(userName)
                .setDescription("nghia@gmail.com");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {

        // Gọi setUserRole để đảm bảo quyền người dùng được cập nhật
        userDAO.setUserRole(userName);

        Set<String> allowedFunctions = new HashSet<>(userDAO.getAllowedFunctions());

        // In ra các chức năng hợp lệ
        System.out.println("Allowed functions: " + allowedFunctions);

        String menus[][] = {
            {"Dashboard"},
            {"Quản lý độc giả"},
            {"Đăng ký thẻ"},
            {"Mượn sách"},
            {"Trả sách"},
            {"Sách"},
            {"Thể loại"},
            {"Tác giả"},
            {"Nhà xuất bản"},
            {"Khu vực sách"},
            {"Lưu trữ sách cũ"},
            {"Duyệt yêu cầu"},
            {"Phiếu mượn"},
            {"Phiếu trả"},
            {"Send mail"},
            {"Logout"}
        };

        String icons[] = {
            "chart.svg",
            "reader.svg",
            "membership.svg",
            "book.svg",
            "book_return.svg",
            "book1.svg",
            "bookmark.svg",
            "author.svg",
            "publisher.svg",
            "area_book.svg",
            "old_book.svg",
            "approve.svg",
            "slip.svg",
            "return_slip.svg",
            "sendmail.svg",
            "logout.svg"
        };

        return new SimpleMenuOption()
                .setMenus(menus)
                .setIcons(icons)
                .setBaseIconPath("drawer/svg")
                .setIconScale(0.45f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int index, int subIndex) {
                        // Xử lý khi người dùng chọn một menu hợp lệ
                        if (index == 0) {
                            WindowsTabbed.getInstance().addTab("Test Form", new Test());
                        } else if (index == 1) {
                            WindowsTabbed.getInstance().addTab("QL docgia", new QL_docgia());
                        } else if (index == 2) {
                            WindowsTabbed.getInstance().addTab("Thẻ thành viên", new QL_ThongTinTheDocGia());
                        } else if (index == 3) {
                            WindowsTabbed.getInstance().addTab("Quản lý mượn sách", new QL_Muon());
                        } else if (index == 4) {
                            WindowsTabbed.getInstance().addTab("QL trả sách", new QL_Tra());
                        } else if (index == 5) {
                            WindowsTabbed.getInstance().addTab("QL sách", new QL_sach());

                        } else if (index == 6) {
                            WindowsTabbed.getInstance().addTab("QL theloai", new QL_theloaisach());
                        } else if (index == 7) {
                            WindowsTabbed.getInstance().addTab("QL tg", new QL_tacgia());
                        } else if (index == 8) {
                            WindowsTabbed.getInstance().addTab("QL nhaxuatban", new QL_nhaxuatban());
                        } else if (index == 9) {
                            WindowsTabbed.getInstance().addTab("QL khu vực sách", new QL_khuvucsach());
                        } else if (index == 12) {
                            WindowsTabbed.getInstance().addTab("QL chi tiết phiếu mượn", new QL_chitietphieumuon());
                        } else if (index == 14) {
                            WindowsTabbed.getInstance().addTab("QL sendmail", new QL_mail());
                        } else if (index == 15) {
                            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(WindowsTabbed.getInstance().getBody());

                            if (currentFrame != null) {
                                currentFrame.dispose(); // Đóng form hiện tại
                            }

                            // Hiển thị lại form login
                            new main().setVisible(true);
                        }
                        System.out.println("Menu selected " + index + " " + subIndex);
                    }
                })
                .setMenuValidation(new MenuValidation() {
                    @Override
                    public boolean menuValidation(int index, int subIndex) {
                        if (index >= 0 && index < menus.length) {
                            String menuName = menus[index][subIndex];  // Lấy tên menu con
                            if (allowedFunctions.contains(menuName)) {
                                System.out.println("Access granted for: " + menuName);  // Debug thông báo
                                return true;  // Nếu có quyền truy cập thì cho phép
                            } else {
                                System.out.println("Access denied for: " + menuName);  // Debug thông báo
                                return false;  // Nếu không có quyền thì từ chối
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("Libary Mangement")
                .setDescription("Quản lý");
    }

    @Override
    public int getDrawerWidth() {
        return 275;
    }
}

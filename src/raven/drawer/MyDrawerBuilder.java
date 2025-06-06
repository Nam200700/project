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
import java.util.Set;
import ui.DashboardForm;
import ui.QL_Muon;
import ui.QL_PhieuPhat;
import ui.QL_ThongBao;
import ui.QL_Tra;
import ui.QL_chitietphieumuon;
import ui.QL_chitietphieutra;
import ui.QL_khuvucsach;
import ui.QL_mail;
import ui.QL_sach;
import ui.QL_taikhoan;

public class MyDrawerBuilder extends SimpleDrawerBuilder {

    private static String userRole = "";  // Biến toàn cục để lưu quyền người dùng
    private static String userName = "";
    private static String userEmail = "";

    public static void setUserRole(String role) {
        userRole = role;  // Cập nhật quyền người dùng
    }

    public static void setuserName(String name) {
        userName = name;  // Cập nhật quyền người dùng
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MyDrawerBuilder.userName = userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        MyDrawerBuilder.userEmail = userEmail;
    }

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/image/profile.png"), 65, 65, 999))
                .setTitle(userName)
                .setDescription(userEmail);
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
            {"Quản lý sách", "Tác giả", "Thể loại", "Nhà xuất bản", "Khu vực sách", "Sách"},
            {"Quản lý phiếu mượn", "Phiếu mượn", "Chi tiết phiếu mượn"},
            {"Quản lý phiếu trả", "Phiếu trả", "Chi tiết phiếu trả"},
            {"Phiếu phạt"},
            {"Quản lý tài khoản"},
            {"Gửi thông báo"},
            {"Send mail"},
            {"Logout"}
        };

        String icons[] = {
            "chart.svg",
            "reader.svg",
            "book.svg",
            "book1.svg",
            "book_return.svg",
            "membership.svg",
            "author.svg",
            "notification1.svg",
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
                            WindowsTabbed.getInstance().addTab("Dashboard", new DashboardForm());
                        } else if (index == 1) {
                            WindowsTabbed.getInstance().addTab("Quản lý độc giả", new QL_docgia());
                        } else if (index == 2) {
                            if (subIndex == 1) {

                                WindowsTabbed.getInstance().addTab("Tác giả", new QL_tacgia());
                            } else if (subIndex == 2) {
                                WindowsTabbed.getInstance().addTab("Thể loại", new QL_theloaisach());
                            } else if (subIndex == 3) {
                                WindowsTabbed.getInstance().addTab("Nhà xuất bản", new QL_nhaxuatban());
                            } else if (subIndex == 4) {
                                WindowsTabbed.getInstance().addTab("Khu vực sách", new QL_khuvucsach());
                            } else if (subIndex == 5) {
                                WindowsTabbed.getInstance().addTab("Sách", new QL_sach());
                            }
                        } else if (index == 3) {
                            if (subIndex == 1) {
                                WindowsTabbed.getInstance().addTab("Phiếu mượn", new QL_Muon());
                            } else if (subIndex == 2) {
                                WindowsTabbed.getInstance().addTab("Chi tiết phiếu mượn", new QL_chitietphieumuon());
                            }
                        } else if (index == 4) {
                            if (subIndex == 1) {
                                WindowsTabbed.getInstance().addTab("Phiếu trả", new QL_Tra());
                            } else if (subIndex == 2) {
                                WindowsTabbed.getInstance().addTab("Chi tiết phiếu trả", new QL_chitietphieutra());
                            }
                        } else if (index == 5) {
                            WindowsTabbed.getInstance().addTab("Phiếu phạt", new QL_PhieuPhat());

                        } else if (index == 6) {
                            WindowsTabbed.getInstance().addTab("Quản lý tài khoản", new QL_taikhoan());

                        }
                        else if (index == 7) {
                            WindowsTabbed.getInstance().addTab("Gửi thông báo", new QL_ThongBao());
                        }
                        else if (index == 8) {
                            WindowsTabbed.getInstance().addTab("Send mail", new QL_mail());
                        }
                        else if (index == 9) {
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

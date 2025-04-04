package raven.drawer;

import DAO.ThongBaoDAO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatToggleButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.Timer;

/**
 *
 * @author RAVEN
 */
public class WindowsTabbed {

    private JWindow notificationWindow = null; // Đảm bảo có một biến global để quản lý cửa sổ thông báo
    private static WindowsTabbed instance;
    private JMenuBar menuBar;
    private PanelTabbed panelTabbed;
    private JPanel body;
    private TabbedForm temp;
    private final int LIMIT = 5; // -1 for unlimit
    private final boolean REMOVE_WHEN_LIMIT = false;
    private static int maTaiKhoan;
    private JLabel notificationCountLabel; // Thêm nhãn để hiển thị số lượng thông báo chưa đọc

    public static int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public static void setMaTaiKhoan(int maTaiKhoan) {
        WindowsTabbed.maTaiKhoan = maTaiKhoan;
    }

    public JPanel getBody() {
        return body;
    }

    public static WindowsTabbed getInstance() {
        if (instance == null) {
            instance = new WindowsTabbed();
        }
        return instance;
    }

    public void install(JFrame frame, JPanel body) {
        this.body = body;
        menuBar = new JMenuBar();
        menuBar.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderColor:$TitlePane.background;"
                + "border:0,0,0,0");
        panelTabbed = new PanelTabbed();
        panelTabbed.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$TitlePane.background");
        menuBar.add(createDrawerButton());
        menuBar.add(createScroll(panelTabbed));
        // Thêm khoảng trống để đẩy nút sang phải
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createBellIcon());

        // Thêm Dark/Light Toggle Button
        menuBar.add(createDarkLightSwitch());
        frame.setJMenuBar(menuBar);
    }

    public void removeAllTabbed() {
        panelTabbed.removeAll();
        panelTabbed.repaint();
        panelTabbed.revalidate();
        body.removeAll();
        body.revalidate();
        body.repaint();
    }

    public void showTabbed(boolean show) {
        menuBar.setVisible(show);
        if (!show) {
            Drawer.getInstance().closeDrawer();
        }
    }

    private JButton createDrawerButton() {
        JButton cmd = new JButton(new FlatSVGIcon("drawer/svg/menu.svg", 0.9f));
        cmd.addActionListener((ae) -> {
            Drawer.getInstance().showDrawer();
        });
        cmd.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:null;"
                + "arc:5");
        return cmd;
    }

    private FlatToggleButton createDarkLightSwitch() {
        FlatToggleButton toggle = new FlatToggleButton();
        toggle.setSelected(false); // Mặc định Light Mode
        toggle.setIcon(new DarkLightSwitchIcon());

        // Loại bỏ viền xanh dư thừa
        toggle.setFocusable(false);
        toggle.setBorderPainted(false);
        toggle.setContentAreaFilled(false);

        toggle.addActionListener(e -> {
            boolean darkMode = toggle.isSelected();
            changeThemes(darkMode);
        });

        return toggle;
    }

    private JButton createBellIcon() {
        JButton bellButton = new JButton(new FlatSVGIcon("drawer/svg/bell.svg", 0.9f));
        bellButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:null;"
                + "arc:5");

        // Tạo nhãn để hiển thị số lượng thông báo chưa đọc
        notificationCountLabel = new JLabel("0");
        notificationCountLabel.setForeground(Color.RED);
        notificationCountLabel.setFont(new Font("Arial", Font.BOLD, 15));
        notificationCountLabel.setPreferredSize(new Dimension(30, 35));
        bellButton.add(notificationCountLabel, BorderLayout.NORTH);  // Đặt nhãn vào nút chuông

        // Thêm ActionListener để lấy thông báo khi nhấn chuông
        bellButton.addActionListener(e -> {
            // Lấy danh sách thông báo
            ThongBaoDAO thongBaoDAO = new ThongBaoDAO();
            List<String> notifications = thongBaoDAO.getNotifications(WindowsTabbed.getMaTaiKhoan()); // Giả sử maTaiKhoan là ID tài khoản người dùng

            // Nếu có thông báo, cập nhật số lượng thông báo
            if (!notifications.isEmpty()) {
                // Cập nhật số lượng thông báo chưa đọc
                updateNotificationCount(notifications.size());

                // Hiển thị hoặc ẩn thông báo
                if (notificationWindow != null && notificationWindow.isVisible()) {
                    notificationWindow.setVisible(false);
                } else {
                    showNotification(bellButton, notifications);
                }
            } else {
                // Nếu không có thông báo, cập nhật lại số lượng về 0
                updateNotificationCount(0);
            }
        });

        return bellButton;
    }

    private void updateNotificationCount(int count) {
        if (count > 99) {
            notificationCountLabel.setText("99+");
        } else {
            notificationCountLabel.setText(String.valueOf(count));
        }
    }

    private void showNotification(JButton bellButton, List<String> messages) {
        // Tạo JWindow để hiển thị thông báo
        notificationWindow = new JWindow();
        notificationWindow.setLayout(new BorderLayout());

        // Tạo panel để chứa danh sách thông báo
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));

        // Thêm các thông báo vào panel
        for (String message : messages) {
            JLabel notificationLabel = new JLabel(message);
            notificationLabel.setBackground(new Color(0, 0, 0, 150)); // Màu nền đen với độ mờ
            notificationLabel.setForeground(Color.WHITE);
            notificationLabel.setOpaque(true);
            notificationLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            notificationLabel.setPreferredSize(new Dimension(300, 50));
            notificationPanel.add(notificationLabel);
        }

        // Đặt thanh cuộn nếu có quá nhiều thông báo
        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        scrollPane.setPreferredSize(new Dimension(300, 150)); // Kích thước cố định cho cửa sổ thông báo
        notificationWindow.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Tạo nút "Xóa tất cả"
        JButton clearAllButton = new JButton("Xóa tất cả");
        clearAllButton.addActionListener(e -> {
            // Xóa tất cả thông báo
            ThongBaoDAO thongBaoDAO = new ThongBaoDAO();
            thongBaoDAO.deleteAllNotifications(WindowsTabbed.getMaTaiKhoan());  // Xóa thông báo trong cơ sở dữ liệu
            notificationWindow.setVisible(false); // Ẩn cửa sổ thông báo

            // Cập nhật số lượng thông báo còn lại
            updateNotificationCount(0); // Sau khi xem hết thông báo, cập nhật lại về 0
        });

        // Đặt nút "Xóa tất cả" vào dưới cùng của cửa sổ thông báo
        notificationWindow.getContentPane().add(clearAllButton, BorderLayout.SOUTH);

        // Lấy vị trí của nút chuông và đặt thông báo ngay dưới nó
        Point bellButtonLocation = bellButton.getLocationOnScreen();
        int bellButtonY = bellButtonLocation.y + bellButton.getHeight(); // Vị trí ngay dưới chuông

        // Đặt kích thước và vị trí của thông báo
        notificationWindow.setSize(625, 300);  // Kích thước cửa sổ lớn hơn để chứa nhiều thông báo
        notificationWindow.setLocation(bellButtonLocation.x - 610, bellButtonY); // Đặt thông báo ngay dưới chuông

        // Hiển thị thông báo
        notificationWindow.setVisible(true);

        // Cập nhật số lượng thông báo còn lại về 0
        updateNotificationCount(0); // Khi hiển thị thông báo, bạn cũng có thể đặt lại số lượng thông báo
    }

    private void shakeButton(JButton button) {
        int delay = 30; // Thời gian mỗi bước rung
        int maxShake = 5; // Biên độ rung lớn hơn để giống YouTube
        Timer shakeTimer = new Timer(delay, null);

        shakeTimer.addActionListener(new ActionListener() {
            int count = 0;
            boolean right = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= 20) { // Sau 20 bước thì dừng
                    button.setLocation(button.getX(), button.getY()); // Đưa về vị trí cũ
                    shakeTimer.stop();
                } else {
                    button.setLocation(button.getX() + (right ? maxShake : -maxShake), button.getY());
                    right = !right;
                    count++;
                }
            }
        });

        shakeTimer.start();
    }

    private void changeThemes(boolean dark) {
        if (FlatLaf.isLafDark() != dark) {
            EventQueue.invokeLater(() -> {
                FlatAnimatedLafChange.showSnapshot();
                if (!dark) {
                    FlatIntelliJLaf.setup();
                } else {
                    FlatDarculaLaf.setup();
                }
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();

                // Cập nhật màu nền cho body và tất cả JPanel bên trong
                updatePanelColors(body, dark);
            });
        }
    }

    private void updatePanelColors(Container container, boolean dark) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (dark) {
                    panel.setBackground(new Color(45, 45, 45));
                    panel.setForeground(Color.gray);
                } else {
                    panel.setBackground(Color.gray);
                    panel.setForeground(Color.BLACK);
                }
            }
        }
        container.repaint();
    }

    private JScrollPane createScroll(Component com) {
        JScrollPane scroll = new JScrollPane(com);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "width:0");
        scroll.getHorizontalScrollBar().setUnitIncrement(10);
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0");
        return scroll;
    }

    public boolean addTab(String title, TabbedForm component) {
        if (LIMIT != -1 && panelTabbed.getComponentCount() >= LIMIT) {
            if (REMOVE_WHEN_LIMIT) {
                panelTabbed.remove(0);
            } else {
                return false;
            }
        }
        TabbedItem item = new TabbedItem(title, component);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                showForm(item.getComponent());
            }
        });
        panelTabbed.addTab(item);
        showForm(component);
        item.setSelected(true);
        return true;
    }

    public void removeTab(TabbedItem tab) {
        if (tab.getComponent().formClose()) {
            if (tab.isSelected()) {
                body.removeAll();
                body.revalidate();
                body.repaint();
            }
            panelTabbed.remove(tab);
            panelTabbed.revalidate();
            panelTabbed.repaint();
        }
    }

    public void removeTabAt(int index) {
        Component com = panelTabbed.getComponent(index);
        if (com instanceof TabbedItem) {
            removeTab((TabbedItem) com);
        }
    }

    public void removeTab(TabbedForm tab) {
        for (Component com : panelTabbed.getComponents()) {
            if (com instanceof TabbedItem) {
                TabbedForm form = ((TabbedItem) com).getComponent();
                if (form == tab) {
                    removeTab((TabbedItem) com);
                }
            }
        }
    }

    public String[] getTabName() {
        List<String> list = new ArrayList<>();
        for (Component com : panelTabbed.getComponents()) {
            if (com instanceof TabbedItem) {
                String name = ((TabbedItem) com).getTabbedName();
                list.add(name);
            }
        }
        String[] arr = new String[list.size()];
        list.toArray(arr);
        return arr;
    }

    public int getTabSelectedIndex() {
        for (Component com : panelTabbed.getComponents()) {
            if (com instanceof TabbedItem) {
                if (((TabbedItem) com).isSelected()) {
                    return panelTabbed.getComponentZOrder(com);
                }
            }
        }
        return -1;
    }

    public void showForm(TabbedForm component) {
        body.removeAll();
        body.setLayout(new BorderLayout()); // Đảm bảo layout phù hợp
        body.add(component, BorderLayout.CENTER); // Thêm vào center để tự động co giãn
        body.revalidate();
        body.repaint();
        panelTabbed.revalidate();
        panelTabbed.repaint();
        component.formOpen();
        temp = component;
        System.out.println("Component count trong body: " + body.getComponentCount());
        for (Component c : body.getComponents()) {
            System.out.println("Component: " + c.getClass().getName());
        }

    }

}

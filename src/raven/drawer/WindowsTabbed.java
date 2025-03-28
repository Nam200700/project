package raven.drawer;

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
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import raven.drawer.Drawer;

/**
 *
 * @author RAVEN
 */
public class WindowsTabbed {

    private static WindowsTabbed instance;
    private JMenuBar menuBar;
    private PanelTabbed panelTabbed;
    private JPanel body;
    private TabbedForm temp;
    private final int LIMIT = 5; // -1 for unlimit
    private final boolean REMOVE_WHEN_LIMIT = false;

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

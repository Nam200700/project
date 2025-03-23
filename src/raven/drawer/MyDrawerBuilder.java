package raven.drawer;

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
import ui.view;
import raven.swing.AvatarIcon;
import ui.QL_docgia;
import ui.QL_nhaxuatban;
import ui.QL_tacgia;
import ui.QL_theloaisach;
import ui.Test;

/**
 *
 * @author RAVEN
 */
public class MyDrawerBuilder extends SimpleDrawerBuilder {

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/image/profile.png"), 65, 65, 999))
                .setTitle("Nam")
                .setDescription("raven@gmail.com");

    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        String menus[][] = {
            {"~MAIN~"},
            {"Dashboard"},
            {"~WEB APP~"},
            {"Email", "Inbox", "Read", "Compost"},
            {"Chat"},
            {"Calendar"},
            {"~COMPONENT~"},
            {"Advanced UI", "Cropper", "Owl Carousel", "Sweet Alert"},
            {"Forms", "Basic Elements", "Advanced Elements", "SEditors", "Wizard"},
            {"~OTHER~"},
            {"Charts", "Apex", "Flot", "Sparkline"},
            {"Icons", "Feather Icons", "Flag Icons", "Mdi Icons"},
            {"Special Pages", "Blank page", "Faq", "Invoice", "Profile", "Pricing", "Timeline"},
            {"Logout"}};

        String icons[] = {
            "chart.svg",
            "book.svg"
        };

        return new SimpleMenuOption()
                .setMenus(menus)
                .setIcons(icons)
                .setBaseIconPath("drawer/svg")
                .setIconScale(0.45f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int index, int subIndex) {
                        if (index == 0) {
                            WindowsTabbed.getInstance().addTab("Test Form", new Test());
                        }else if(index == 2){
                            WindowsTabbed.getInstance().addTab("QL tg", new QL_tacgia());
                        }else if(index == 3){
                            WindowsTabbed.getInstance().addTab("QL docgia", new QL_docgia());
                        }else if(index == 1){
                            WindowsTabbed.getInstance().addTab("QL nhaxuatban", new QL_nhaxuatban());
                        }else if(index == 4){
                            WindowsTabbed.getInstance().addTab("QL theloai", new QL_theloaisach());
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
//                        if(index==0){
//                            return false;
//                        }else if(index==3){
//                            return false;
//                        }
                        return true;
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

package raven.drawer;

import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import ui.view;
import raven.swing.AvatarIcon;
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
            "chart.svg"
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

                }
                System.out.println("Menu selected " + index + " " + subIndex);
            }})
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
                .setTitle("Hotel Mangement")
                .setDescription("Quản lý");
    }

    @Override
    public int getDrawerWidth() {
        return 275;
    }
}

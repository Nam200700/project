/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;

/**
 *
 * @author ACER
 */
public class Test extends TabbedForm {

    /**
     * Creates new form Test
     */
    public Test() {
        initComponents();
        applyTableStyle();
        testData();
    }

    private void applyTableStyle() {
        JScrollPane scroll = (JScrollPane) tableTest.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        System.out.println("✅ Đã tìm thấy JScrollPane: " + scroll.getClass().getName());

        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Table.background;"
                + "track:$Table.background;"
                + "trackArc:999");

        tableTest.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        tableTest.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        tableTest.revalidate();
        tableTest.repaint();
    }

    private void testData() {
        DefaultTableModel model = (DefaultTableModel) tableTest.getModel();
        model.setRowCount(0);

        // Thêm từng dòng dữ liệu
        model.addRow(new Object[]{"SP005", "Chuột", 500000, 12});
        model.addRow(new Object[]{"SP006", "Màn hình", 4500000, 4});

        System.out.println("✅ Đã thêm dữ liệu test vào bảng!");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableTest = new javax.swing.JTable();

        tableTest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableTest);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void formOpen() {
        System.out.println("Form open");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableTest;
    // End of variables declaration//GEN-END:variables
}

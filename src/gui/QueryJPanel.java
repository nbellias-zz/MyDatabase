/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import mydatabase.Database;
import mydatabase.EntityData;

/**
 *
 * @author nikolaos
 */
public class QueryJPanel extends javax.swing.JPanel {

    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Creates new form QueryJPanel
     */
    public QueryJPanel() {
        initComponents();
    }

    public QueryJPanel(String dbName) {
        initComponents();
        this.dbName = dbName;
        this.dbTitleJLabel.setText(dbName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        queryResponseJSplitPane = new javax.swing.JSplitPane();
        queryJPanel = new javax.swing.JPanel();
        queryEditorJScrollPane = new javax.swing.JScrollPane();
        queryJEditorPane = new javax.swing.JEditorPane();
        queryExecuteJPanel = new javax.swing.JPanel();
        executeJButton = new javax.swing.JButton();
        clearJButton = new javax.swing.JButton();
        dbNameJLabel = new javax.swing.JLabel();
        dbTitleJLabel = new javax.swing.JLabel();
        responseJScrollPane = new javax.swing.JScrollPane();
        responseJTextPane = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        queryResponseJSplitPane.setDividerLocation(300);
        queryResponseJSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        queryJPanel.setLayout(new java.awt.BorderLayout());

        queryEditorJScrollPane.setViewportView(queryJEditorPane);

        queryJPanel.add(queryEditorJScrollPane, java.awt.BorderLayout.CENTER);

        queryExecuteJPanel.setPreferredSize(new java.awt.Dimension(985, 50));

        executeJButton.setText("Execute");
        executeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeJButtonActionPerformed(evt);
            }
        });

        clearJButton.setText("Clear");
        clearJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJButtonActionPerformed(evt);
            }
        });

        dbNameJLabel.setText("Current Database:");

        dbTitleJLabel.setText("          ");

        javax.swing.GroupLayout queryExecuteJPanelLayout = new javax.swing.GroupLayout(queryExecuteJPanel);
        queryExecuteJPanel.setLayout(queryExecuteJPanelLayout);
        queryExecuteJPanelLayout.setHorizontalGroup(
            queryExecuteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(queryExecuteJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(executeJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 530, Short.MAX_VALUE)
                .addComponent(dbNameJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dbTitleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        queryExecuteJPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {clearJButton, executeJButton});

        queryExecuteJPanelLayout.setVerticalGroup(
            queryExecuteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(queryExecuteJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(queryExecuteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(queryExecuteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(executeJButton)
                        .addComponent(clearJButton))
                    .addGroup(queryExecuteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dbNameJLabel)
                        .addComponent(dbTitleJLabel)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        queryJPanel.add(queryExecuteJPanel, java.awt.BorderLayout.PAGE_END);

        queryResponseJSplitPane.setLeftComponent(queryJPanel);

        responseJTextPane.setEnabled(false);
        responseJScrollPane.setViewportView(responseJTextPane);

        queryResponseJSplitPane.setRightComponent(responseJScrollPane);

        add(queryResponseJSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void clearJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJButtonActionPerformed
        // TODO add your handling code here:
        this.queryJEditorPane.setText("");
        this.responseJTextPane.setText("");
    }//GEN-LAST:event_clearJButtonActionPerformed

    private void executeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeJButtonActionPerformed
        // TODO add your handling code here:
        String answer = "";
        List<EntityData> entDataList = new ArrayList();
        String query = this.queryJEditorPane.getText();
        if (query.startsWith("select ") && query.contains(" from ") && query.endsWith(";")) {
            String[] arr = query.split("select|from|where|order by");
            String strBetweenSelectAndFrom = arr[1]; // table column(s)
            String strBetweenFromAndWhere = arr[2]; // table name(s)
//            String strBetweenWhereAndOrderBy = arr[3]; // condition(s)
//            String strBetweenBetweenAfterOrderBy = arr[4]; // order by
            System.out.println("strBetweenSelectAndFrom=" + strBetweenSelectAndFrom);
            System.out.println("strBetweenFromAndWhere=" + strBetweenFromAndWhere);
//            System.out.println("strBetweenBetweenWhereAndOrderBy=" + strBetweenWhereAndOrderBy);
//            System.out.println("strBetweenBetweenAfterOrderBy=" + strBetweenBetweenAfterOrderBy);
            Database db = new Database();
            
            entDataList = db.fetchDatabaseEntityDataGUI(this.dbName, strBetweenFromAndWhere.trim());
            for (EntityData entityData : entDataList) {
                answer += entityData.getRecord().toString() + "\n";
            }

        } else {
            answer += "Unable to process and execute query...Try again";
        }

        this.responseJTextPane.setText(answer);
    }//GEN-LAST:event_executeJButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearJButton;
    private javax.swing.JLabel dbNameJLabel;
    private javax.swing.JLabel dbTitleJLabel;
    private javax.swing.JButton executeJButton;
    private javax.swing.JScrollPane queryEditorJScrollPane;
    private javax.swing.JPanel queryExecuteJPanel;
    private javax.swing.JEditorPane queryJEditorPane;
    private javax.swing.JPanel queryJPanel;
    private javax.swing.JSplitPane queryResponseJSplitPane;
    private javax.swing.JScrollPane responseJScrollPane;
    private javax.swing.JTextPane responseJTextPane;
    // End of variables declaration//GEN-END:variables
}
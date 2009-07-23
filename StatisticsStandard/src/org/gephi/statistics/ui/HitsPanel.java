/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HitsPanel.java
 *
 * Created on Jun 23, 2009, 11:58:10 AM
 */
package org.gephi.statistics.ui;

import javax.swing.JPanel;
import org.gephi.statistics.Hits;
import org.gephi.statistics.api.Statistics;
import org.gephi.statistics.ui.api.StatisticsUI;

/**
 *
 * @author pjmcswee
 */
public class HitsPanel extends javax.swing.JPanel {

    /** Creates new form HitsPanel */
    public HitsPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        epsilonTextField = new javax.swing.JTextField();
        undirectedButton = new javax.swing.JRadioButton();
        directedButton = new javax.swing.JRadioButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.jLabel1.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.jLabel3.text")); // NOI18N

        epsilonTextField.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.epsilonTextField.text")); // NOI18N
        epsilonTextField.setMinimumSize(new java.awt.Dimension(59, 25));
        epsilonTextField.setPreferredSize(new java.awt.Dimension(59, 25));

        buttonGroup1.add(undirectedButton);
        undirectedButton.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.undirectedButton.text")); // NOI18N

        buttonGroup1.add(directedButton);
        directedButton.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.directedButton.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(33, 33, 33)
                        .addComponent(epsilonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(directedButton)
                            .addComponent(undirectedButton))))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(undirectedButton)
                .addGap(5, 5, 5)
                .addComponent(directedButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(epsilonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(144, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JRadioButton directedButton;
    protected javax.swing.JTextField epsilonTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    protected javax.swing.JRadioButton undirectedButton;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public static class HitsUI implements StatisticsUI {

        private HitsPanel panel;
        private Hits hits;

        public HitsUI() {
            panel = new HitsPanel();
        }

        /**
         *
         */
        public void unsetup() {
            //Set params
            try {
                double epsilon = Double.parseDouble(panel.epsilonTextField.getText());
                hits.setEpsilon(epsilon);
                hits.setUndirected(panel.undirectedButton.isSelected());

            } catch (Exception e) {
            }
        }

        /**
         *
         * @return
         */
        public JPanel getPanel() {
            return panel;
        }

        /**
         *
         * @param statistics
         */
        public void setup(Statistics statistics) {
            this.hits = (Hits) statistics;
            panel.epsilonTextField.setText("" + hits.getEpsilon());
            panel.undirectedButton.setSelected(hits.getUndirected());
            panel.directedButton.setSelected(!hits.getUndirected());
        }
    }
}

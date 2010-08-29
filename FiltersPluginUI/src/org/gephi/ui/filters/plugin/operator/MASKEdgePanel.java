/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.ui.filters.plugin.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.gephi.filters.plugin.operator.MASKBuilderEdge;

/**
 *
 * @author Mathieu Bastian
 */
public class MASKEdgePanel extends javax.swing.JPanel implements ActionListener {

    private MASKBuilderEdge.MaskEdgesOperator operator;

    public MASKEdgePanel() {
        initComponents();
        anyButton.addActionListener(this);
        bothButton.addActionListener(this);
        sourceButton.addActionListener(this);
        targetButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (operator != null) {
            MASKBuilderEdge.MaskEdgesOperator.EdgesOptions option = MASKBuilderEdge.MaskEdgesOperator.EdgesOptions.ANY;
            if (bothButton.isSelected()) {
                option = MASKBuilderEdge.MaskEdgesOperator.EdgesOptions.BOTH;
            } else if (sourceButton.isSelected()) {
                option = MASKBuilderEdge.MaskEdgesOperator.EdgesOptions.SOURCE;
            } else if (targetButton.isSelected()) {
                option = MASKBuilderEdge.MaskEdgesOperator.EdgesOptions.TARGET;
            }
            operator.getProperties()[0].setValue(option.toString());
        }
    }

    public void setup(MASKBuilderEdge.MaskEdgesOperator operator) {
        this.operator = operator;
        MASKBuilderEdge.MaskEdgesOperator.EdgesOptions option = MASKBuilderEdge.MaskEdgesOperator.EdgesOptions.valueOf(operator.getOption());
        switch(option) {
            case ANY:
                anyButton.setSelected(true);
                break;
            case BOTH:
                bothButton.setSelected(true);
                break;
            case SOURCE:
                sourceButton.setSelected(true);
                break;
            case TARGET:
                targetButton.setSelected(true);
                break;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        group = new javax.swing.ButtonGroup();
        sourceButton = new javax.swing.JRadioButton();
        targetButton = new javax.swing.JRadioButton();
        anyButton = new javax.swing.JRadioButton();
        bothButton = new javax.swing.JRadioButton();

        setLayout(new java.awt.GridBagLayout());

        group.add(sourceButton);
        sourceButton.setText(org.openide.util.NbBundle.getMessage(MASKEdgePanel.class, "MASKEdgePanel.sourceButton.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(sourceButton, gridBagConstraints);

        group.add(targetButton);
        targetButton.setText(org.openide.util.NbBundle.getMessage(MASKEdgePanel.class, "MASKEdgePanel.targetButton.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(targetButton, gridBagConstraints);

        group.add(anyButton);
        anyButton.setSelected(true);
        anyButton.setText(org.openide.util.NbBundle.getMessage(MASKEdgePanel.class, "MASKEdgePanel.anyButton.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(anyButton, gridBagConstraints);

        group.add(bothButton);
        bothButton.setText(org.openide.util.NbBundle.getMessage(MASKEdgePanel.class, "MASKEdgePanel.bothButton.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        add(bothButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton anyButton;
    private javax.swing.JRadioButton bothButton;
    private javax.swing.ButtonGroup group;
    private javax.swing.JRadioButton sourceButton;
    private javax.swing.JRadioButton targetButton;
    // End of variables declaration//GEN-END:variables
}

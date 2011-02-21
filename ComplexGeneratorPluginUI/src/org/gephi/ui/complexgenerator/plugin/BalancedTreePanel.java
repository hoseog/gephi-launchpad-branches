/*
 * Copyright 2008-2010 Gephi
 * Authors : Cezary Bartosiak
 * Website : http://www.gephi.org
 *
 * This file is part of Gephi.
 *
 * Gephi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gephi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.ui.complexgenerator.plugin;

import org.netbeans.validation.api.Problems;
import org.netbeans.validation.api.Validator;
import org.netbeans.validation.api.builtin.Validators;
import org.netbeans.validation.api.ui.ValidationGroup;
import org.netbeans.validation.api.ui.ValidationPanel;

/**
 *
 *
 * @author Cezary Bartosiak
 */
public class BalancedTreePanel extends javax.swing.JPanel {

    /** Creates new form BarabasiAlbertPanel */
    public BalancedTreePanel() {
        initComponents();
    }

	public static ValidationPanel createValidationPanel(BalancedTreePanel innerPanel) {
		ValidationPanel validationPanel = new ValidationPanel();
		if (innerPanel == null)
			innerPanel = new BalancedTreePanel();
		validationPanel.setInnerComponent(innerPanel);

		ValidationGroup group = validationPanel.getValidationGroup();

		group.add(innerPanel.rField, Validators.REQUIRE_NON_EMPTY_STRING,
				new rValidator(innerPanel));
		group.add(innerPanel.hField, Validators.REQUIRE_NON_EMPTY_STRING,
				new hValidator(innerPanel));

		return validationPanel;
	}

	private static class rValidator implements Validator<String> {
		private BalancedTreePanel innerPanel;

		public rValidator(BalancedTreePanel innerPanel) {
			this.innerPanel = innerPanel;
		}

		@Override
		public boolean validate(Problems problems, String compName, String model) {
			boolean result = false;

			try {
				Integer r = Integer.parseInt(innerPanel.rField.getText());
				result = r >= 2;
			}
			catch (Exception e) { }
			if (!result) {
				String message = "<html>r &gt;= 2</html>";
				problems.add(message);
			}

			return result;
		}
    }

	private static class hValidator implements Validator<String> {
		private BalancedTreePanel innerPanel;

		public hValidator(BalancedTreePanel innerPanel) {
			this.innerPanel = innerPanel;
		}

		@Override
		public boolean validate(Problems problems, String compName, String model) {
			boolean result = false;

			try {
				Integer h = Integer.parseInt(innerPanel.hField.getText());
				result = h >= 1;
			}
			catch (Exception e) { }
			if (!result) {
				String message = "<html>h &gt;= 1</html>";
				problems.add(message);
			}

			return result;
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

        rField = new javax.swing.JTextField();
        hField = new javax.swing.JTextField();
        rLabel = new javax.swing.JLabel();
        hLabel = new javax.swing.JLabel();
        constraintsLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(330, 102));

        rField.setText(org.openide.util.NbBundle.getMessage(BalancedTreePanel.class, "BalancedTreePanel.rField.text")); // NOI18N

        hField.setText(org.openide.util.NbBundle.getMessage(BalancedTreePanel.class, "BalancedTreePanel.hField.text")); // NOI18N

        rLabel.setText(org.openide.util.NbBundle.getMessage(BalancedTreePanel.class, "BalancedTreePanel.rLabel.text")); // NOI18N

        hLabel.setText(org.openide.util.NbBundle.getMessage(BalancedTreePanel.class, "BalancedTreePanel.hLabel.text")); // NOI18N

        constraintsLabel.setText(org.openide.util.NbBundle.getMessage(BalancedTreePanel.class, "BalancedTreePanel.constraintsLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rLabel)
                            .addComponent(hLabel))
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(rField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(constraintsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(constraintsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel constraintsLabel;
    protected javax.swing.JTextField hField;
    private javax.swing.JLabel hLabel;
    protected javax.swing.JTextField rField;
    private javax.swing.JLabel rLabel;
    // End of variables declaration//GEN-END:variables

}

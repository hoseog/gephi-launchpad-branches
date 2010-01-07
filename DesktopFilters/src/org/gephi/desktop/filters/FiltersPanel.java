/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.desktop.filters;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.gephi.desktop.filters.library.FiltersExplorer;
import org.gephi.desktop.filters.query.QueryExplorer;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.FilterModel;
import org.gephi.filters.api.Query;
import org.openide.explorer.ExplorerManager;
import org.openide.util.Lookup;

/**
 *
 * @author Mathieu Bastian
 */
public class FiltersPanel extends javax.swing.JPanel implements ExplorerManager.Provider {

    private ExplorerManager manager = new ExplorerManager();
    private FilterModel model;
    private FilterUIModel uIModel;

    public FiltersPanel() {
        initComponents();
        uIModel = new FilterUIModel();
        model = Lookup.getDefault().lookup(FilterController.class).getModel();
        ((FiltersExplorer) libraryTree).setup(manager, model, uIModel);

        southPanel.add(new FunctionPanel(), BorderLayout.CENTER);
        filtersUIPanel.add(new FilterPanelPanel(uIModel));

        resetButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                FilterController controller = Lookup.getDefault().lookup(FilterController.class);
                for (Query query : model.getQueries()) {
                    controller.remove(query);
                }
                uIModel.setSelectedFilter(null);
            }
        });

    }

    private class FunctionPanel extends JPanel implements ExplorerManager.Provider {

        private ExplorerManager manager = new ExplorerManager();

        public FunctionPanel() {
            super(new BorderLayout());
            QueryExplorer functionExplorer = new QueryExplorer();
            functionExplorer.setup(manager, model, uIModel);
            add(functionExplorer, BorderLayout.CENTER);
        }

        public ExplorerManager getExplorerManager() {
            return manager;
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

        toolbar = new javax.swing.JToolBar();
        resetButton = new javax.swing.JButton();
        splitPane = new javax.swing.JSplitPane();
        libraryTree = new FiltersExplorer();
        southPanel = new javax.swing.JPanel();
        filtersUIPanel = new javax.swing.JPanel();
        applyButton = new javax.swing.JButton();
        selectButton = new javax.swing.JButton();
        southToolbar = new javax.swing.JToolBar();
        liveButton = new javax.swing.JToggleButton();

        setLayout(new java.awt.GridBagLayout());

        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        resetButton.setText(org.openide.util.NbBundle.getMessage(FiltersPanel.class, "FiltersPanel.resetButton.text")); // NOI18N
        resetButton.setFocusable(false);
        resetButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(resetButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        add(toolbar, gridBagConstraints);

        splitPane.setBorder(null);
        splitPane.setDividerLocation(260);
        splitPane.setDividerSize(3);
        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        libraryTree.setBorder(null);
        splitPane.setLeftComponent(libraryTree);

        southPanel.setLayout(new java.awt.BorderLayout());
        splitPane.setRightComponent(southPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(splitPane, gridBagConstraints);

        filtersUIPanel.setPreferredSize(new java.awt.Dimension(0, 50));
        filtersUIPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 1, 5);
        add(filtersUIPanel, gridBagConstraints);

        applyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gephi/desktop/filters/resources/filter.png"))); // NOI18N
        applyButton.setText(org.openide.util.NbBundle.getMessage(FiltersPanel.class, "FiltersPanel.applyButton.text")); // NOI18N
        applyButton.setMargin(new java.awt.Insets(2, 7, 2, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 3);
        add(applyButton, gridBagConstraints);

        selectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gephi/desktop/filters/resources/select.png"))); // NOI18N
        selectButton.setText(org.openide.util.NbBundle.getMessage(FiltersPanel.class, "FiltersPanel.selectButton.text")); // NOI18N
        selectButton.setMargin(new java.awt.Insets(2, 7, 2, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 3);
        add(selectButton, gridBagConstraints);

        southToolbar.setFloatable(false);
        southToolbar.setRollover(true);

        liveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gephi/desktop/filters/resources/live.png"))); // NOI18N
        liveButton.setText(org.openide.util.NbBundle.getMessage(FiltersPanel.class, "FiltersPanel.liveButton.text")); // NOI18N
        liveButton.setFocusable(false);
        southToolbar.add(liveButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(southToolbar, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JPanel filtersUIPanel;
    private javax.swing.JScrollPane libraryTree;
    private javax.swing.JToggleButton liveButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton selectButton;
    private javax.swing.JPanel southPanel;
    private javax.swing.JToolBar southToolbar;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

    public ExplorerManager getExplorerManager() {
        return manager;
    }
}

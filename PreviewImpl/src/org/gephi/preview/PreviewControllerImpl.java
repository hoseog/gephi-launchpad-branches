package org.gephi.preview;

import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.preview.api.Graph;
import org.gephi.preview.api.GraphSheet;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.project.api.ProjectController;
import org.gephi.workspace.api.Workspace;
import org.gephi.workspace.api.WorkspaceListener;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the preview controller.
 *
 * @author Jérémy Subtil <jeremy.subtil@gephi.org>
 */
@ServiceProvider(service = PreviewController.class)
public class PreviewControllerImpl implements PreviewController {

    //Fatory
    private final PreviewGraphFactory factory = new PreviewGraphFactory();
    //Current graphs
    private GraphImpl previewGraph = null;
    private PartialGraphImpl partialPreviewGraph = null;
    private GraphSheetImpl graphSheet = null;
    private GraphSheetImpl partialGraphSheet = null;
    //Model
    private PreviewModelImpl model;

    /**
     * Constructor.
     */
    public PreviewControllerImpl() {
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.addWorkspaceListener(new WorkspaceListener() {

            public void initialize(Workspace workspace) {
                workspace.add(new PreviewModelImpl());
            }

            public void select(Workspace workspace) {
                model = (PreviewModelImpl) workspace.getLookup().lookup(PreviewModel.class);
                if (model == null) {
                    model = new PreviewModelImpl();
                    workspace.add(model);
                }
            }

            public void unselect(Workspace workspace) {
            }

            public void close(Workspace workspace) {
            }

            public void disable() {
                graphSheet = null;
                model = null;
            }
        });

        // checks the current workspace state
        if (pc.getCurrentWorkspace() != null) {
            Workspace workspace = pc.getCurrentWorkspace();
            model = (PreviewModelImpl) workspace.getLookup().lookup(PreviewModel.class);
            if (model == null) {
                model = new PreviewModelImpl();
                workspace.add(model);
            }
        }
    }

    /**
     * Returns the current preview graph.
     *
     * @return the current preview graph
     */
    public Graph getGraph() {
        if (model != null) {
            if (model.isUpdateFlag()) {
                buildGraph();
            }
            return previewGraph;
        }
        return null;
    }

    /**
     * Returns a subgraph of the current preview graph.
     *
     * @param visibilityRatio  the ratio of the preview graph to display
     * @return                 a subgraph of the current preview graph
     */
    public Graph getPartialGraph(float visibilityRatio) {
        if (model != null) {
            if (model.isUpdateFlag() || null == partialPreviewGraph || partialPreviewGraph.getVisibilityRatio() != visibilityRatio) {
                partialPreviewGraph = new PartialGraphImpl(getGraph(), visibilityRatio);
                model.setVisibilityRatio(visibilityRatio);
            }
            return partialPreviewGraph;
        }
        return null;
    }

    public GraphSheet getGraphSheet() {
        if (model != null) {
            if (model.isUpdateFlag() || null == graphSheet || graphSheet.getGraph() != previewGraph) {
                graphSheet = new GraphSheetImpl(getGraph());
            }
            return graphSheet;
        }
        return null;
    }

    public GraphSheet getPartialGraphSheet(float visibilityRatio) {
        if (model != null) {
            if (model.isUpdateFlag() || null == partialGraphSheet
                    || ((PartialGraphImpl) partialGraphSheet.getGraph()).getVisibilityRatio() != visibilityRatio) {
                partialGraphSheet = new GraphSheetImpl(getPartialGraph(visibilityRatio));
            }
            return partialGraphSheet;
        }
        return null;
    }

    /**
     * Retreives the workspace graph and builds a preview graph from it.
     *
     * For each build, the supervisors' lists of supervised elements are
     * cleared because the previous preview graph is forgotten.
     *
     * @see PreviewController#buildGraph()
     */
    public void buildGraph() {
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        model.clearSupervisors();

        if (graphModel.isUndirected()) {
            previewGraph = factory.createPreviewGraph(model, graphModel.getUndirectedGraph());
        } else if (graphModel.isDirected()) {
            previewGraph = factory.createPreviewGraph(model, graphModel.getDirectedGraph());
        } else if (graphModel.isMixed()) {
            previewGraph = factory.createPreviewGraph(model, graphModel.getMixedGraph());
        }
        model.setUpdateFlag(false);
    }

    public PreviewModel getModel() {
        return model;
    }
}

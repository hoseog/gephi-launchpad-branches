/*
Copyright (c) 2010, Matt Groeninger
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of
conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list
of conditions and the following disclaimer in the documentation and/or other materials
provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.gephi.layout.plugin.circularlayout.circlelayout;


import org.gephi.layout.plugin.circularlayout.nodecomparator.BasicNodeComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.layout.plugin.AbstractLayout;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;
import org.openide.util.NbBundle;
import org.openide.util.Lookup;
import java.util.EnumMap;
import java.util.Map;


/**
 *
 * @author Matt
 */
public class CircleLayout extends AbstractLayout implements Layout {

    private Graph graph;
    private boolean converged;
    private double diameter;
    private boolean boolfixeddiameter;
    private Enum enumNodeplacement;
    private boolean boolNoOverlap = true;
    private Enum enumNodePlacementDirection;
    static final double TWO_PI = (2 * Math.PI);

    public CircleLayout(LayoutBuilder layoutBuilder, double diameter, boolean boolfixeddiameter) {
        super(layoutBuilder);
        this.diameter = diameter;
        this.boolfixeddiameter = boolfixeddiameter;
    }

    private enum PlacementEnum {
        NodeID,
        Random,
        Degree,
        Indegree,
        Outdegree,
        Mutual,
        Children,
        Descendents;
    }

    public static Map getPlacementEnumMap() {
        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        GraphModel objGraphModel = graphController.getModel();
        Map<PlacementEnum, String> map = new EnumMap<PlacementEnum, String>(PlacementEnum.class);
        map.put(PlacementEnum.NodeID, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.NodeID.name"));
        map.put(PlacementEnum.Random, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Random.name"));
        map.put(PlacementEnum.Degree, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Degree.name"));
        if (objGraphModel.isDirected()) {
            map.put(PlacementEnum.Indegree, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.InDegree.name"));
            map.put(PlacementEnum.Outdegree, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.OutDegree.name"));
            map.put(PlacementEnum.Mutual, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Mutual.name"));
        } else if (objGraphModel.isHierarchical()) {
            map.put(PlacementEnum.Children, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Children.name"));
            map.put(PlacementEnum.Descendents, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Descendents.name"));
        }
        return map;
    }

        private enum RotationEnum {
        CCW,
        CW;
    }

    public static Map getRotationEnumMap() {
        Map<RotationEnum, String> map = new EnumMap<RotationEnum, String>(RotationEnum.class);
        map.put(RotationEnum.CCW, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.CCW"));
        map.put(RotationEnum.CW, NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.CW"));
        return map;
    }


    @Override
    public void initAlgo() {
        converged = false;
        graph = graphModel.getGraphVisible();
    }

    @Override
    public void goAlgo() {
        //Determine Radius of Circle
        graph = graphModel.getGraphVisible();
        float[] nodeCoords = new float[2];
        double tmpcirc = 0;
        double tmpdiameter = 0;
        int index = 0;
        int nodecount = graph.getNodeCount();
        double noderadius = 0;
        double theta = TWO_PI / nodecount;
        double lasttheta = 0;

        if (!this.boolfixeddiameter) {
            Node[] nodes = graph.getNodes().toArray();
            for (Node n : nodes) {
                tmpcirc += (n.getNodeData().getRadius() * 2);
            }
            tmpcirc = (tmpcirc * 1.2);
            tmpdiameter = tmpcirc / Math.PI;
            if (this.boolNoOverlap) {
                theta = (TWO_PI / tmpcirc);
            }
        } else {
            tmpdiameter = this.diameter;
        }
        double radius = tmpdiameter / 2;

        //determine Node placement
        Node[] nodes = graph.getNodes().toArray();
        
        if (this.enumNodeplacement == PlacementEnum.NodeID) {
            //Do nothing
        } else if (this.enumNodeplacement == PlacementEnum.Random) {
            List nodesList = Arrays.asList(nodes);
            Collections.shuffle(nodesList);
        } else if (this.enumNodeplacement == PlacementEnum.Degree) {
            Arrays.sort(nodes, new BasicNodeComparator(graph, nodes,"Degree", false));
        } else if (this.enumNodeplacement == PlacementEnum.Indegree) {
            Arrays.sort(nodes, new BasicNodeComparator(graph, nodes,"InDegree", false));
        } else if (this.enumNodeplacement == PlacementEnum.Outdegree) {
            Arrays.sort(nodes, new BasicNodeComparator(graph, nodes,"OutDegree", false));
        } else if (this.enumNodeplacement == PlacementEnum.Mutual) {
            Arrays.sort(nodes, new BasicNodeComparator(graph, nodes,"MutualDegree", false));
        } else if (this.enumNodeplacement == PlacementEnum.Children) {
            Arrays.sort(nodes, new BasicNodeComparator(graph, nodes,"Children", false));
        } else if (this.enumNodeplacement == PlacementEnum.Descendents) {
            Arrays.sort(nodes, new BasicNodeComparator(graph, nodes,"Descendent", false));
        }


        if (this.enumNodePlacementDirection == RotationEnum.CW) {
            theta = -theta;
        }
        for (Node n : nodes) {
            if (this.boolNoOverlap) {
                noderadius = (n.getNodeData().getRadius());
                double noderadian = (theta * noderadius * 1.2);
                nodeCoords = this.cartCoors(radius, 1, lasttheta + noderadian);
                lasttheta += (noderadius * 2 * theta * 1.2);
            } else {
                nodeCoords = this.cartCoors(radius, index, theta);
            }
            n.getNodeData().setX(nodeCoords[0]);
            n.getNodeData().setY(nodeCoords[1]);
            index++;
        }
        converged = true;
    }

    @Override
    public boolean canAlgo() {
        return !converged;
    }

    @Override
    public void endAlgo() {
    }

    @Override
    public LayoutProperty[] getProperties() {
        List<LayoutProperty> properties = new ArrayList<LayoutProperty>();
        try {
            properties.add(LayoutProperty.createProperty(
                    this, Boolean.class,
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.BoolFixedDiameter.name"),
                    "Circle Properties",
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.BoolFixedDiameter.desc"),
                    "isBoolFixedDiameter", "setBoolFixedDiameter"));
            properties.add(LayoutProperty.createProperty(
                    this, Double.class,
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.Diameter.name"),
                    "Circle Properties",
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.Diameter.desc"),
                    "getDiameter", "setDiameter"));
            properties.add(LayoutProperty.createProperty(
                    this, Enum.class,
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.NodeOrdering.name"),
                    "Node Placement",
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.NodeOrdering.desc"),
                    "getNodePlacement", "setNodePlacement", LayoutComboBoxEditor.class));
            properties.add(LayoutProperty.createProperty(
                    this, Enum.class,
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Direction.name"),
                    "Node Placement",
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.Direction.desc"),
                    "getNodePlacementDirection", "setNodePlacementDirection", RotationComboBoxEditor.class));
            properties.add(LayoutProperty.createProperty(
                    this, Boolean.class,
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.NoOverlap.name"),
                    "Node Placement",
                    NbBundle.getMessage(CircleLayout.class, "CircleLayout.NodePlacement.NoOverlap.desc"),
                    "isNodePlacementNoOverlap", "setNodePlacementNoOverlap"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.toArray(new LayoutProperty[0]);
    }

    @Override
    public void resetPropertiesValues() {
        setDiameter(500.0);
        setBoolFixedDiameter(false);
        setNodePlacement(PlacementEnum.NodeID);
        setNodePlacementNoOverlap(true);
        setNodePlacementDirection(RotationEnum.CCW);
    }

    public void setNodePlacement(Enum enumNodeplacement) {
        this.enumNodeplacement = enumNodeplacement;
    }

    public Enum getNodePlacement() {
        return this.enumNodeplacement;
    }

    public void setBoolFixedDiameter(Boolean boolfixeddiameter) {
        this.boolfixeddiameter = boolfixeddiameter;
        if (this.boolfixeddiameter && this.boolNoOverlap) {
            setNodePlacementNoOverlap(false);
        }
    }

    public boolean isBoolFixedDiameter() {
        return boolfixeddiameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Double getDiameter() {
        return diameter;
    }

    public Enum getNodePlacementDirection() {
        return this.enumNodePlacementDirection;
    }

    public void setNodePlacementDirection(Enum enumNodePlacementDirection) {
        this.enumNodePlacementDirection = enumNodePlacementDirection;
    }

    public boolean isNodePlacementNoOverlap() {
        return boolNoOverlap;
    }

    public void setNodePlacementNoOverlap(Boolean boolNoOverlap) {
        this.boolNoOverlap = boolNoOverlap;
        if (this.boolfixeddiameter && this.boolNoOverlap) {
            setBoolFixedDiameter(false);
        }
    }

    private float[] cartCoors(double radius, int whichInt, double theta) {
        float[] coOrds = new float[2];
        coOrds[0] = (float) (radius * (Math.cos((theta * whichInt) + (Math.PI / 2))));
        coOrds[1] = (float) (radius * (Math.sin((theta * whichInt) + (Math.PI / 2))));
        return coOrds;
    }


}

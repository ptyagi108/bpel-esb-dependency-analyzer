package com.tomecode.soa.bpel.dependency.analyzer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.tomecode.soa.bpel.dependency.analyzer.gui.cellview.CellViewFactory;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * 
 * @author Frastia Tomas
 * 
 */
public final class WorkspaceVisualPanel extends JPanel {

	private static final long serialVersionUID = -5786182349758892615L;

	private final List<Object> insertedObjects;

	private final GraphModel graphModel;
	private final JGraph graph;

	private JGraphOrganicLayout organicLayout = new JGraphOrganicLayout();

	private final JGraphFacade facade;

	public WorkspaceVisualPanel() {
		super(new BorderLayout());
		insertedObjects = new ArrayList<Object>();
		graphModel = new DefaultGraphModel();
		graph = new JGraph(graphModel);
		graph.setBackground(new Color(218, 218, 218));

		graph.getGraphLayoutCache().setFactory(new CellViewFactory());
		// graph.setGridEnabled(true);
		// graph.setGridColor(Color.red);
		// graph.setGridVisible(true);
		// graph.setGridSize(30);
		graph.setAntiAliased(true);
		// graph.setPortsVisible(true);
		// graph.setEdgeLabelsMovable(false);
		graph.setDisconnectable(false);
		add(new JScrollPane(graph), BorderLayout.CENTER);
		facade = new JGraphFacade(graph);
		organicLayout.setRadiusScaleFactor(0.9);
		organicLayout.setNodeDistributionCostFactor(8000000.0);
		organicLayout.setOptimizeBorderLine(false);
		organicLayout.setDeterministic(true);
		organicLayout.run(facade);
		// layouts do not implement the Runnable interface, to avoid confusion
		Map<?, ?> nested = facade.createNestedMap(true, true); // Obtain a map
		// of the resulting attribute changes from the facade
		graph.getGraphLayoutCache().edit(nested); // Apply the results to
		// the actual graph
	}

	/**
	 * show dependency graph
	 * 
	 * @param project
	 */
	public final void showGraphBpel(BpelProject project) {
		clearCells();

		DefaultGraphCell rootCell = createVertexBpel(project);
		insertedObjects.add(rootCell);

		for (PartnerLinkBinding partnerLinkBinding : project.getPartnerLinkBindings()) {
			BpelProject bpelProject = partnerLinkBinding.getDependencyBpelProject();
			if (bpelProject != null) {

				DefaultGraphCell bpelCell = createVertexBpel(bpelProject);
				if (bpelProject.equals(project)) {
					insertedObjects.add(createEdge(rootCell, rootCell));
				} else {
					insertedObjects.add(bpelCell);
					insertedObjects.add(createEdge(rootCell, bpelCell));
				}
			} else {
				EsbProject esbProject = partnerLinkBinding.getDependencyEsbProject();
				if (esbProject != null) {
					DefaultGraphCell esbCell = createVertexEsb(esbProject);
					insertedObjects.add(esbCell);
					insertedObjects.add(createEdge(rootCell, esbCell));
				}
			}
		}

		graph.getGraphLayoutCache().insert(insertedObjects.toArray());

		organicLayout.run(facade);

		Map<?, ?> nested = facade.createNestedMap(true, true); // Obtain a map
		// of the resulting attribute changes from the facade
		graph.getGraphLayoutCache().edit(nested); // Apply the results to

	}

	/**
	 * create new vertex for bpel
	 * 
	 * @param userObject
	 * @param position
	 * @return
	 */
	private final DefaultGraphCell createVertexBpel(Object userObject, Point2D position) {
		AttributeMap attributes = new AttributeMap(createCellAttributesBpel(position));
		GraphConstants.setBounds(attributes, new Rectangle2D.Double(position.getX(), position.getY(), 40, 20));
		DefaultGraphCell cell = new DefaultGraphCell(userObject, attributes);
		// Add a Port
		cell.addPort();
		return cell;
	}

	/**
	 * create new vertex for esb
	 * 
	 * @param userObject
	 * @param position
	 * @return
	 */
	private final DefaultGraphCell createVertexEsb(EsbProject userObject, Point2D position) {
		AttributeMap attributes = new AttributeMap(createCellAttributesEsb(position));
		GraphConstants.setBounds(attributes, new Rectangle2D.Double(position.getX(), position.getY(), 40, 20));
		DefaultGraphCell cell = new DefaultGraphCell(userObject, attributes);
		// Add a Port
		cell.addPort();
		return cell;
	}

	private final DefaultGraphCell createVertexBpel(BpelProject bpelProject) {

		double x = this.getBounds().getWidth() / 3;
		double y = this.getBounds().getHeight() / 2;

		Point2D position = new Point2D.Double(x, y);// calcCellPosition(i);
		return createVertexBpel(bpelProject, position);
	}

	/**
	 * creat new {@link DefaultGraphCell} for {@link EsbProject}
	 * 
	 * @param esbProject
	 * @return
	 */
	private final DefaultGraphCell createVertexEsb(EsbProject esbProject) {

		double x = this.getBounds().getWidth() / 3;
		double y = this.getBounds().getHeight() / 2;

		Point2D position = new Point2D.Double(x, y);// calcCellPosition(i);
		return createVertexEsb(esbProject, position);
	}

	/**
	 * create edge between two vertex
	 * 
	 * @param sourcePort
	 * @param targetPort
	 * @return
	 */
	private final DefaultEdge createEdge(DefaultGraphCell sourcePort, DefaultGraphCell targetPort) {

		AttributeMap edgeAttrib = new AttributeMap();
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(edgeAttrib, arrow);
		GraphConstants.setEndFill(edgeAttrib, true);
		GraphConstants.setEditable(edgeAttrib, false);
		DefaultEdge edge = new DefaultEdge(null, edgeAttrib);// (null,
		// edgeAttrib);

		// TODO: dorobit aby sa edge nemohol premiestnovat kurzorom
		edge.setSource(sourcePort.getChildAt(0));
		edge.setTarget(targetPort.getChildAt(0));

		Point2D point = null;
		if (sourcePort.getUserObject().equals(targetPort.getUserObject())) {
			point = new Point2D.Double(GraphConstants.PERMILLE / 2, -10);
		} else {
			point = new Point2D.Double(GraphConstants.PERMILLE / 2, 10);
		}
		edge.setUserObject("aaa");

		GraphConstants.setLabelPosition(edgeAttrib, point);
		return edge;
	}

	// private final Point2D calcCellPosition(int i) {
	// int gridWidth = (int) Math.sqrt(i);
	//
	// if (i != 0) {
	// return new Point2D.Double(20 + (60 * (i % gridWidth)), 20 + (40 * (i /
	// gridWidth)));
	// } else {
	// return new Point2D.Double(20, 20);
	// }
	// }

	/**
	 * Hook from GraphEd to set attributes of a new cell
	 */
	@SuppressWarnings("unchecked")
	private final Map<?, ?> createCellAttributesBpel(Point2D point) {

		Map<?, ?> map = new Hashtable();

		// Snap the Point to the Grid

		// if (graph != null) {
		point = graph.snap((Point2D) point.clone());
		// } else {
		// point = (Point2D) point.clone();
		// }

		CellViewFactory.setRoundRectView(map);

		// Add a Bounds Attribute to the Map
		GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 0, 0));
		// Make sure the cell is resized on insert
		GraphConstants.setResize(map, true);
		// Add a nice looking gradient background
		// GraphConstants.setGradientColor(map, Color.blue);
		// Add a Border Color Attribute to the Map
		GraphConstants.setBorderColor(map, Color.black);
		// Add a White Background
		// GraphConstants.setBackground(map, Color.white);
		// Make Vertex Opaque
		GraphConstants.setOpaque(map, true);
		// return map;
		//		
		GraphConstants.setIcon(map, IconFactory.PROCESS);
		//		
		// Map map = createCellAttributes1(point);
		GraphConstants.setInset(map, 5);
		// GraphConstants.setGradientColor(map, new Color(102,255,102));//(100,
		// 200, 255));
		GraphConstants.setBackground(map, new Color(0, 200, 202));

		return map;
	}

	@SuppressWarnings("unchecked")
	private final Map<?, ?> createCellAttributesEsb(Point2D point) {

		Map<?, ?> map = new Hashtable();
		// Snap the Point to the Grid

		CellViewFactory.setRoundRectView(map);

		// if (graph != null) {
		point = graph.snap((Point2D) point.clone());
		// } else {
		// point = (Point2D) point.clone();
		// }
		// Add a Bounds Attribute to the Map
		GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 0, 0));
		// Make sure the cell is resized on insert
		GraphConstants.setResize(map, true);
		// Add a nice looking gradient background
		// GraphConstants.setGradientColor(map, Color.blue);
		// Add a Border Color Attribute to the Map
		GraphConstants.setBorderColor(map, Color.black);

		GraphConstants.setIcon(map, IconFactory.ESB);
		// Add a White Background
		// GraphConstants.setBackground(map, Color.white);
		// Make Vertex Opaque
		GraphConstants.setOpaque(map, true);
		// return map;
		//		
		//		
		// Map map = createCellAttributes1(point);
		GraphConstants.setInset(map, 5);
		// GraphConstants.setGradientColor(map, new Color(102,255,102));//(100,
		// 200, 255));
		GraphConstants.setBackground(map, new Color(102, 255, 102));

		return map;
	}

	public final void clearCells() {

		graph.getModel().beginUpdate();

		try {
			graph.getGraphLayoutCache().remove(insertedObjects.toArray());
		} finally {
			graph.getModel().endUpdate();
		}

		insertedObjects.clear();
	}

	public final void showGraphEsb(EsbProject rootProject) {
		clearCells();

		DefaultGraphCell rootCell = createVertexEsb(rootProject);
		insertedObjects.add(rootCell);

		for (Project project : rootProject.getEsbProjectsDependecies()) {
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpelProject = (BpelProject) project;
				DefaultGraphCell bpelCell = createVertexBpel(bpelProject);
				if (bpelProject.equals(rootProject)) {
					insertedObjects.add(createEdge(rootCell, rootCell));
				} else {
					insertedObjects.add(bpelCell);
					insertedObjects.add(createEdge(rootCell, bpelCell));
				}
			} else {
				EsbProject esbProject = (EsbProject) rootProject;
				DefaultGraphCell esbCell = createVertexEsb(esbProject);
				insertedObjects.add(esbCell);
				insertedObjects.add(createEdge(rootCell, esbCell));
			}
		}

		graph.getGraphLayoutCache().insert(insertedObjects.toArray());

		organicLayout.run(facade);

		Map<?, ?> nested = facade.createNestedMap(true, true); // Obtain a map
		// of the resulting attribute changes from the facade
		graph.getGraphLayoutCache().edit(nested); // Apply the results to

	}
}

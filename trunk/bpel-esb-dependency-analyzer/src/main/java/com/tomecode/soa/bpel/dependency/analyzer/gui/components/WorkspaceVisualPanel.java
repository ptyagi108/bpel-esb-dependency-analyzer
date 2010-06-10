package com.tomecode.soa.bpel.dependency.analyzer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.tomecode.soa.bpel.dependency.analyzer.gui.cellview.CellViewFactory;
import com.tomecode.soa.bpel.dependency.analyzer.gui.cellview.SelfDefaultEdge;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.DependencyNode;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.esb.EsbOperation;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbSvc;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.project.UnknownProject;

/**
 * 
 * dependency graph - for visualizing dependencies beetween projects
 * 
 * @author Frastia Tomas
 * 
 */
public final class WorkspaceVisualPanel extends JPanel {

	private static final long serialVersionUID = -5786182349758892615L;

	/**
	 * list of inserted objects in {@link GraphModel}
	 */
	private final List<Object> insertedObjects;

	private final GraphModel graphModel;
	/**
	 * visual graph
	 */
	private final JGraph graph;

	private JGraphFastOrganicLayout organicLayout;// JGraphOrganicLayout
	// organicLayout;

	private final JGraphFacade facade;

	private final Color defaultColor;

	/**
	 * Constructor
	 */
	public WorkspaceVisualPanel() {
		super(new BorderLayout());
		organicLayout = new JGraphFastOrganicLayout(); // JGraphOrganicLayout();
		insertedObjects = new ArrayList<Object>();
		graphModel = new DefaultGraphModel();
		graph = new JGraph(graphModel);
		defaultColor = new Color(218, 218, 218);
		graph.setBackground(defaultColor);

		graph.getGraphLayoutCache().setFactory(new CellViewFactory());
		// graph.setGridEnabled(true);
		// graph.setGridColor(Color.red);
		// graph.setGridVisible(true);
		// graph.setGridSize(30);
		graph.setAntiAliased(true);
		// graph.setPortsVisible(true);
		// graph.setEdgeLabelsMovable(false);
		graph.setDisconnectable(false);
		graph.setAutoscrolls(true);

		add(new JScrollPane(graph), BorderLayout.CENTER);
		facade = new JGraphFacade(graph);
		// facade.setIgnoresCellsInGroups(true);
		// facade.setCircleRadiusFactor(100.9D);
		// organicLayout.setRadiusScaleFactor(0.9);
		// organicLayout.setNodeDistributionCostFactor(8000000.0);
		// organicLayout.setOptimizeBorderLine(false);
		// organicLayout.setDeterministic(true);
		organicLayout.setForceConstant(160D);
		// organicLayout.setInitialTemp(150D);
		organicLayout.run(facade);
		// layouts do not implement the Runnable interface, to avoid confusion
		Map<?, ?> nested = facade.createNestedMap(true, true); // Obtain a map
		// of the resulting attribute changes from the facade
		// graph.getGraphLayoutCache().edit(nested); // Apply the results to
		// the actual graph

		// Map map = facade.createNestedMap(true, true);
		graph.getGraphLayoutCache().edit(nested, null, null, null);
		// organicLayout.run(facade);
	}

	/**
	 * show dependency graph
	 * 
	 * @param project
	 */
	public final void showGraphBpel(BpelProject project) {
		zoomReset();
		clearCells();

		checkForceBySize(project.getPartnerLinkBindings().size());
		DefaultGraphCell rootCell = createVertexBpel(project);
		insertedObjects.add(rootCell);

		// show all dependecies by partnerlink
		for (PartnerLinkBinding partnerLinkBinding : project.getPartnerLinkBindings()) {
			if (partnerLinkBinding.getDependencyBpelProject() != null) {
				BpelProject bpelProject = partnerLinkBinding.getDependencyBpelProject();

				DefaultGraphCell bpelCell = createVertexBpel(bpelProject);
				if (bpelProject.equals(project)) {
					insertedObjects.add(createEdge(rootCell, rootCell, partnerLinkBinding, true));
				} else {
					insertedObjects.add(bpelCell);
					insertedObjects.add(createEdge(rootCell, bpelCell, partnerLinkBinding, false));
				}
			} else if (partnerLinkBinding.getDependencyEsbProject() != null) {
				EsbProject esbProject = partnerLinkBinding.getDependencyEsbProject();
				DefaultGraphCell esbCell = createVertexEsb(esbProject);
				insertedObjects.add(esbCell);
				insertedObjects.add(createEdge(rootCell, esbCell, partnerLinkBinding, false));
			} else if (partnerLinkBinding.getUnknownProject() != null) {
				DefaultGraphCell unknownCell = createVertexUnknownProject(partnerLinkBinding.getUnknownProject());
				insertedObjects.add(unknownCell);
				insertedObjects.add(createEdge(rootCell, unknownCell, partnerLinkBinding, false));
			}
		}

		graph.getGraphLayoutCache().insert(insertedObjects.toArray());

		organicLayout.run(facade);

		Map<?, ?> nested = facade.createNestedMap(true, true);
		graph.getGraphLayoutCache().edit(nested);

	}

	private final void checkForceBySize(int size) {
		if (size <= 2) {
			organicLayout.setForceConstant(350D);
		} else {
			organicLayout.setForceConstant(160D);
		}
	}

	/**
	 * create vertex for {@link BpelProject}
	 * 
	 * @param bpelProject
	 * @return
	 */
	private final DefaultGraphCell createVertexBpel(BpelProject bpelProject) {

		double x = this.getBounds().getWidth() / 3;
		double y = this.getBounds().getHeight() / 2;

		Point2D position = new Point2D.Double(x, y);// calcCellPosition(i);
		AttributeMap attributes = new AttributeMap(createCellAttributesBpel(position));
		GraphConstants.setBounds(attributes, new Rectangle2D.Double(position.getX(), position.getY(), 40, 20));
		DefaultGraphCell cell = new DefaultGraphCell(bpelProject, attributes);
		// Add a Port
		cell.addPort();
		return cell;
	}

	/**
	 * create vertex for {@link UnknownProject}
	 * 
	 * @param unknownProject
	 * @return
	 */
	private final DefaultGraphCell createVertexUnknownProject(UnknownProject unknownProject) {
		double x = this.getBounds().getWidth() / 2;
		double y = this.getBounds().getHeight() / 2;

		Point2D position = new Point2D.Double(x, y);// calcCellPosition(i);

		AttributeMap attributes = new AttributeMap(createCellAttributeUnknownProject(position));
		GraphConstants.setBounds(attributes, new Rectangle2D.Double(position.getX(), position.getY(), 40, 20));
		DefaultGraphCell cell = new DefaultGraphCell(unknownProject, attributes);
		// Add a Port
		cell.addPort();
		return cell;
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
		AttributeMap attributes = new AttributeMap(createCellAttributesEsb(position));
		GraphConstants.setBounds(attributes, new Rectangle2D.Double(position.getX(), position.getY(), 40, 20));
		DefaultGraphCell cell = new DefaultGraphCell(esbProject, attributes);
		// Add a Port
		cell.addPort();
		return cell;
	}

	/**
	 * create edge between two vertex
	 * 
	 * @param sourcePort
	 * @param targetPort
	 * @return
	 */
	private final DefaultEdge createEdge(DefaultGraphCell sourcePort, DefaultGraphCell targetPort, Object userObject, boolean self) {

		AttributeMap edgeAttrib = new AttributeMap();
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(edgeAttrib, arrow);
		GraphConstants.setEndFill(edgeAttrib, true);
		GraphConstants.setEditable(edgeAttrib, false);
		GraphConstants.setForeground(edgeAttrib, Color.RED);

		DefaultEdge edge = self ? new SelfDefaultEdge(null, edgeAttrib) : new DefaultEdge(null, edgeAttrib);
		edge.setSource(sourcePort.getChildAt(0));
		edge.setTarget(targetPort.getChildAt(0));

		Point2D point = null;
		if (sourcePort.getUserObject().equals(targetPort.getUserObject())) {
			point = new Point2D.Double((GraphConstants.PERMILLE / 2) + countSelfEdge(), (-16 * countSelfEdge()));
		} else {
			point = new Point2D.Double(GraphConstants.PERMILLE / 2, 0);
		}
		edge.setUserObject(userObject);

		GraphConstants.setLabelPosition(edgeAttrib, point);
		return edge;
	}

	private final int countSelfEdge() {
		int count = 0;
		for (Object insertedObject : insertedObjects) {
			if (insertedObject instanceof SelfDefaultEdge) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Hook from GraphEd to set attributes of a new cell
	 */
	@SuppressWarnings("unchecked")
	private final Map<?, ?> createCellAttributesBpel(Point2D point) {

		Map<?, ?> map = new Hashtable();

		// Snap the Point to the Grid

		point = graph.snap((Point2D) point.clone());
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

		GraphConstants.setEditable(map, false);
		return map;
	}

	@SuppressWarnings("unchecked")
	private final Map<?, ?> createCellAttributesEsb(Point2D point) {

		Map<?, ?> map = new Hashtable();
		CellViewFactory.setRoundRectView(map);

		point = graph.snap((Point2D) point.clone());

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
		GraphConstants.setEditable(map, false);
		// Map map = createCellAttributes1(point);
		GraphConstants.setInset(map, 5);
		// GraphConstants.setGradientColor(map, new Color(102,255,102));//(100,
		// 200, 255));
		GraphConstants.setBackground(map, new Color(102, 255, 102));

		return map;
	}

	@SuppressWarnings("unchecked")
	private final Map<?, ?> createCellAttributeUnknownProject(Point2D point) {

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

		GraphConstants.setIcon(map, IconFactory.UNKNOWN);
		// Add a White Background
		// GraphConstants.setBackground(map, Color.white);
		// Make Vertex Opaque
		GraphConstants.setOpaque(map, true);
		// return map;
		//		
		GraphConstants.setEditable(map, false);
		//		
		// Map map = createCellAttributes1(point);
		GraphConstants.setInset(map, 5);
		// GraphConstants.setGradientColor(map, new Color(102,255,102));//(100,
		// 200, 255));
		GraphConstants.setBackground(map, new Color(254, 254, 0));

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

	/**
	 * show esb dependency graph
	 * 
	 * @param rootProject
	 */
	public final void showGraphEsb(EsbProject rootProject) {
		zoomReset();
		clearCells();

		checkForceBySize(rootProject.getEsbProjectsDependecies().size());
		DefaultGraphCell rootCell = createVertexEsb(rootProject);
		insertedObjects.add(rootCell);

		for (EsbSvc esbSvc : rootProject.getAllEsbSvc()) {

			List<Project> projects = filterUniqueProjects(esbSvc.getEsbOperations());
			// for (EsbOperation esbOperation : esbSvc.getEsbOperations()) {

			// for (DependencyNode dependencyNode :
			// esbOperation.getDependencyNodes()) {
			for (Project project : projects) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject bpelProject = (BpelProject) project;
					DefaultGraphCell bpelCell = createVertexBpel(bpelProject);
					if (bpelProject.equals(rootProject)) {
						insertedObjects.add(createEdge(rootCell, rootCell, esbSvc, true));
					} else {
						insertedObjects.add(bpelCell);
						insertedObjects.add(createEdge(rootCell, bpelCell, esbSvc, false));
					}
				} else {
					EsbProject esbProject = (EsbProject) rootProject;
					DefaultGraphCell esbCell = createVertexEsb(esbProject);
					insertedObjects.add(esbCell);
					insertedObjects.add(createEdge(rootCell, esbCell, esbSvc, false));
				}
			}
			// for (Project project :
			// rootProject.getEsbProjectsDependecies()) {

			// }

			// }
			// }
		}

		graph.getGraphLayoutCache().insert(insertedObjects.toArray());

		organicLayout.run(facade);

		Map<?, ?> nested = facade.createNestedMap(true, true);
		graph.getGraphLayoutCache().edit(nested);
	}

	private final List<Project> filterUniqueProjects(List<EsbOperation> esbOperations) {
		List<Project> projects = new ArrayList<Project>();
		for (EsbOperation esbOperation : esbOperations) {
			for (DependencyNode dependencyNode : esbOperation.getDependencyNodes()) {
				if (!projects.contains(dependencyNode.getProject())) {
					projects.add(dependencyNode.getProject());
				}
			}
		}
		return projects;
	}

	/**
	 * zoom in
	 */
	public final void zoomIn() {
		graph.setScale(2 * graph.getScale());
	}

	/**
	 * zoom out
	 */
	public final void zoomOut() {
		graph.setScale(graph.getScale() / 2);
	}

	/**
	 * reset zoom - set defualt zoom
	 */
	public final void zoomReset() {
		graph.setScale(1.0D);
	}

	/**
	 * export to jpg
	 * 
	 * @param file
	 * @throws Exception
	 */
	public final void exportToJpg(File file) throws Exception {
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			BufferedImage image = graph.getImage(graph.getBackground(), 10);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam encpar = encoder.getDefaultJPEGEncodeParam(image);
			encpar.setQuality(1, false);
			encoder.setJPEGEncodeParam(encpar);
			encoder.encode(image);
			ImageIO.write(image, "jpg", out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * export to png
	 * 
	 * @param file
	 * @throws IOException
	 */
	public final void exportToPng(File file) throws IOException {
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			graph.setBackground(Color.white);
			graph.setSelectionCells(new Object[] {});
			BufferedImage image = graph.getImage(graph.getBackground(), 10);
			ImageIO.write(image, "png", out);
		} finally {
			graph.setBackground(defaultColor);
			if (out != null) {
				out.close();
			}
		}
	}
}

package com.tomecode.soa.dependency.analyzer.view.visual;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.jface.action.Action;
import org.eclipse.zest.core.viewers.internal.ZoomManager;
import org.eclipse.zest.core.widgets.Graph;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Better zoom contribution for graph:)
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@SuppressWarnings("restriction")
public final class ZoomHelper {

	private final ZoomManager zoomManager;

	/**
	 * list of zoom actions
	 */
	private final Hashtable<Double, ZoomAction> zoomsTable;

	private double currentZoom = 100;

	private final ZoomInAction zoomInAction;

	private final ZoomOutAction zoomOutAction;

	private final ZoomResetAction zoomResetAction;

	/**
	 * 
	 * Constructor
	 * 
	 * @param zoomableWorkbenchPart
	 */
	public ZoomHelper(Graph graph) {
		this.zoomInAction = new ZoomInAction();
		this.zoomOutAction = new ZoomOutAction();
		this.zoomResetAction = new ZoomResetAction();
		zoomManager = new ZoomManager(graph.getRootLayer(), graph.getViewport());

		zoomsTable = new Hashtable<Double, ZoomAction>();
		zoomsTable.put(50d, new ZoomAction(50));
		zoomsTable.put(100d, new ZoomAction(100));
		zoomsTable.put(150d, new ZoomAction(150));
		zoomsTable.put(200d, new ZoomAction(200));
		zoomsTable.put(250d, new ZoomAction(250));
		zoomsTable.put(300d, new ZoomAction(300));
		zoomsTable.put(350d, new ZoomAction(350));
		zoomsTable.put(400d, new ZoomAction(400));

		zoomsTable.get(100d).run();
	}

	public final ZoomResetAction getZoomResetAction() {
		return zoomResetAction;
	}

	public final ZoomInAction getZoomInAction() {
		return zoomInAction;
	}

	public final ZoomOutAction getZoomOutAction() {
		return zoomOutAction;
	}

	public final Hashtable<Double, ZoomAction> getZoomActions() {
		return zoomsTable;
	}

	/**
	 * Zoom reset
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public final class ZoomResetAction extends Action {

		public ZoomResetAction() {
			setToolTipText("Zoom Reset");
			setImageDescriptor(ImageFactory.zoom_reset);
		}

		public final void run() {
			currentZoom = 100;
			zoomsTable.get(currentZoom).run();
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Zoom in action
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
	 */
	public final class ZoomInAction extends Action {

		public ZoomInAction() {
			setToolTipText("Zoom In");
			setImageDescriptor(ImageFactory.zoom_in);
		}

		public final void run() {
			if (currentZoom + 50 <= 400) {
				currentZoom = currentZoom + 50;
				zoomsTable.get(currentZoom).run();
			}
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Zoom out action
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
	 */
	public final class ZoomOutAction extends Action {

		public ZoomOutAction() {
			setToolTipText("Zoom Out");
			setImageDescriptor(ImageFactory.zoom_out);
		}

		public final void run() {
			if (currentZoom > 50) {
				currentZoom = currentZoom - 50;
				zoomsTable.get(currentZoom).run();
			}
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Zoom action
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
	 */
	public final class ZoomAction extends Action {

		private double zoom;

		private ZoomAction(int zoom) {
			this.zoom = zoom;
			setText(zoom + "%");
		}

		public final void run() {
			zoomManager.setZoomAsText(getText());

			setChecked(true);

			Enumeration<Double> e = zoomsTable.keys();
			while (e.hasMoreElements()) {
				double d = e.nextElement();
				if (d != zoom) {
					zoomsTable.get(d).setChecked(false);
				}
			}
		}
	}

}

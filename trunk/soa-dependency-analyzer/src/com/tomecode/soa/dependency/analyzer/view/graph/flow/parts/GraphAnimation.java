package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class GraphAnimation {

	static final long DURATION = 230;

	static long current;
	static double progress;
	static long start = -1;
	static long finish;
	static Viewport viewport;
	// static IFigure trackMe;
	// static IFigure showMe;
	// static Point trackLocation;

	static boolean PLAYBACK;
	static boolean RECORDING;

	static Map initialStates;
	static Map finalStates;

	static void end() {
		Iterator iter = initialStates.keySet().iterator();
		while (iter.hasNext()) {
			IFigure f = ((IFigure) iter.next());
			f.revalidate();
			f.setVisible(true);
		}
		// $TODO instead of performing a final normal layout, what about setting
		// progress=1.0?
		initialStates = null;
		finalStates = null;
		PLAYBACK = false;
		// trackMe = null;
		// showMe = null;
		viewport = null;
	}

	static boolean captureLayout(IFigure root) {

		RECORDING = true;

		while (!(root instanceof Viewport))
			root = root.getParent();
		viewport = (Viewport) root;
		while (root.getParent() != null)
			root = root.getParent();

		initialStates = new HashMap();
		finalStates = new HashMap();

		// This part records all layout results.
		root.validate();
		Iterator iter = initialStates.keySet().iterator();
		if (!iter.hasNext()) {
			// Nothing layed out, so abort the animation
			RECORDING = false;
			return false;
		}
		while (iter.hasNext())
			recordFinalState((IFigure) iter.next());

		start = System.currentTimeMillis();
		finish = start + DURATION;
		current = start + 20;

		RECORDING = false;
		PLAYBACK = true;
		return true;
	}

	static boolean playbackState(Connection conn) {
		if (!PLAYBACK)
			return false;

		PointList list1 = (PointList) initialStates.get(conn);
		PointList list2 = (PointList) finalStates.get(conn);
		if (list1 == null) {
			conn.setVisible(false);
			return true;
		}
		if (list1.size() == list2.size()) {
			Point pt1 = new Point(), pt2 = new Point();
			PointList points = conn.getPoints();
			points.removeAllPoints();
			for (int i = 0; i < list1.size(); i++) {
				list1.getPoint(pt2, i);
				list2.getPoint(pt1, i);
				pt1.x = (int) Math.round(pt1.x * progress + (1 - progress) * pt2.x);
				pt1.y = (int) Math.round(pt1.y * progress + (1 - progress) * pt2.y);
				points.addPoint(pt1);
			}
			conn.setPoints(points);
		}
		return true;
	}

	static boolean playbackState(IFigure container) {
		if (!PLAYBACK)
			return false;

		List children = container.getChildren();
		Rectangle rect1, rect2;
		for (int i = 0; i < children.size(); i++) {
			IFigure child = (IFigure) children.get(i);
			rect1 = (Rectangle) initialStates.get(child);
			rect2 = (Rectangle) finalStates.get(child);
			if (rect2 == null)
				continue;
			child.setBounds(new Rectangle((int) Math.round(progress * rect2.x + (1 - progress) * rect1.x), (int) Math.round(progress * rect2.y + (1 - progress) * rect1.y), (int) Math.round(progress
					* rect2.width + (1 - progress) * rect1.width), (int) Math.round(progress * rect2.height + (1 - progress) * rect1.height)));
			// child.invalidate();
		}
		return true;
	}

	static void recordFinalState(Connection conn) {
		// $TODO
		PointList points1 = (PointList) initialStates.get(conn);
		PointList points2 = conn.getPoints().getCopy();

		if (points1 != null && points1.size() != points2.size()) {
			Point p = new Point(), q = new Point();

			int size1 = points1.size() - 1;
			int size2 = points2.size() - 1;

			int i1 = size1;
			int i2 = size2;

			double current1 = 1.0;
			double current2 = 1.0;

			double prev1 = 1.0;
			double prev2 = 1.0;

			while (i1 > 0 || i2 > 0) {
				if (Math.abs(current1 - current2) < 0.1 && i1 > 0 && i2 > 0) {
					// Both points are the same, use them and go on;
					prev1 = current1;
					prev2 = current2;
					i1--;
					i2--;
					current1 = (double) i1 / size1;
					current2 = (double) i2 / size2;
				} else if (current1 < current2) {
					// 2 needs to catch up
					// current1 < current2 < prev1
					points1.getPoint(p, i1);
					points1.getPoint(q, i1 + 1);

					p.x = (int) (((q.x * (current2 - current1) + p.x * (prev1 - current2)) / (prev1 - current1)));
					p.y = (int) (((q.y * (current2 - current1) + p.y * (prev1 - current2)) / (prev1 - current1)));

					points1.insertPoint(p, i1 + 1);

					prev1 = prev2 = current2;
					i2--;
					current2 = (double) i2 / size2;

				} else {
					// 1 needs to catch up
					// current2< current1 < prev2

					points2.getPoint(p, i2);
					points2.getPoint(q, i2 + 1);

					p.x = (int) (((q.x * (current1 - current2) + p.x * (prev2 - current1)) / (prev2 - current2)));
					p.y = (int) (((q.y * (current1 - current2) + p.y * (prev2 - current1)) / (prev2 - current2)));

					points2.insertPoint(p, i2 + 1);

					prev2 = prev1 = current1;
					i1--;
					current1 = (double) i1 / size1;
				}
			}
		}
		finalStates.put(conn, points2);
	}

	static void recordFinalState(IFigure child) {
		if (child instanceof Connection) {
			recordFinalState((Connection) child);
			return;
		}
		Rectangle rect2 = child.getBounds().getCopy();
		Rectangle rect1 = (Rectangle) initialStates.get(child);
		if (rect1.isEmpty()) {
			rect1.x = rect2.x;
			rect1.y = rect2.y;
			rect1.width = rect2.width;
		}
		finalStates.put(child, rect2);
	}

	static void recordInitialState(Connection connection) {
		if (!RECORDING)
			return;
		PointList points = connection.getPoints().getCopy();
		if (points.size() == 2 && points.getPoint(0).equals(Point.SINGLETON.setLocation(0, 0)) && points.getPoint(1).equals(Point.SINGLETON.setLocation(100, 100)))
			initialStates.put(connection, null);
		else
			initialStates.put(connection, points);
	}

	static void recordInitialState(IFigure container) {
		if (!RECORDING)
			return;

		List children = container.getChildren();
		IFigure child;
		for (int i = 0; i < children.size(); i++) {
			child = (IFigure) children.get(i);
			initialStates.put(child, child.getBounds().getCopy());
		}
	}

	static void swap() {
		Map temp = finalStates;
		finalStates = initialStates;
		initialStates = temp;
	}

	static boolean step() {
		current = System.currentTimeMillis() + 30;
		progress = (double) (current - start) / (finish - start);
		progress = Math.min(progress, 0.999);
		Iterator iter = initialStates.keySet().iterator();

		while (iter.hasNext())
			((IFigure) iter.next()).revalidate();
		viewport.validate();

		// Point loc = viewport.getViewLocation();
		// loc.translate(trackMe.getBounds().getLocation().getDifference(trackLocation));
		// viewport.setViewLocation(loc);
		// trackLocation = trackMe.getBounds().getLocation();

		return current < finish;
	}

}

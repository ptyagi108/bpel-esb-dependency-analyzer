package com.tomecode.soa.dependency.analyzer.view.visual;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * 
 * Simple browser for visual graph - back/forward...
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class BrowserManager {

	private final List<Object> objects;

	private final VisualGraphView visualGraphView;

	private final BackAction backAction;

	private final ForwardAction forwardAction;

	private int currentIndex = 0;

	/**
	 * Constructor
	 * 
	 * @param visualGraphView
	 */
	public BrowserManager(VisualGraphView visualGraphView) {
		this.objects = new ArrayList<Object>();
		this.visualGraphView = visualGraphView;
		this.backAction = new BackAction();
		this.forwardAction = new ForwardAction();
	}

	/**
	 * @return the backAction
	 */
	public final BackAction getBackAction() {
		return backAction;
	}

	/**
	 * @return the forwardAction
	 */
	public final ForwardAction getForwardAction() {
		return forwardAction;
	}

	/**
	 * 
	 * Back action in visual graph
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public final class BackAction extends Action {

		public BackAction() {
			setToolTipText("Show previous graph...");
			setImageDescriptor(ImageFactory.arrow_back);
			setEnabled(false);
		}

		public final void run() {
			if (currentIndex == 0) {
				visualGraphView.showGraph(objects.get(currentIndex--));
				setEnabled(false);
			} else {
				setEnabled(true);
				if ((currentIndex - 1) == 0) {
					currentIndex = 0;
					setEnabled(false);
				} else {
					currentIndex--;
				}
				visualGraphView.showGraph(objects.get(currentIndex));
			}

		}
	}

	/**
	 * 
	 * Back action in visual graph
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public final class ForwardAction extends Action {

		public ForwardAction() {
			setToolTipText("Show next graph...");
			setImageDescriptor(ImageFactory.arrow_forward);
			setEnabled(false);
		}

		public final void run() {
			if (currentIndex >= objects.size() - 1) {
				visualGraphView.showGraph(objects.get(currentIndex));
				setEnabled(false);
			} else {
				currentIndex++;
				visualGraphView.showGraph(objects.get(currentIndex));
				setEnabled(true);
			}

		}
	}

	/**
	 * add new graph to object browser list
	 * 
	 * @param objectInGraph
	 */
	public final void add(Object objectInGraph) {
		backAction.setEnabled(true);
		if (objects.isEmpty()) {
			objects.add(objectInGraph);
			currentIndex = objects.size();
		} else {
			if (!objects.get(objects.size() - 1).equals(objectInGraph)) {
				objects.add(objectInGraph);
				currentIndex = objects.size();
			}
		}

	}
}

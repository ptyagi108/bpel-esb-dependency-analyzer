package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.Activity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.ParallelActivity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.SequentialActivity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.Transition;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class ActivityPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if (model instanceof com.tomecode.soa.dependency.analyzer.view.graph.flow.ActivityDiagram) {
			part = new ActivityDiagramPart();
		} else if (model instanceof ParallelActivity) {
			part = new ParallelActivityPart();
		} else if (model instanceof SequentialActivity) {
			part = new SequentialActivityPart();
		} else if (model instanceof Activity) {
			part = new SimpleActivityPart();
		} else if (model instanceof Transition) {
			part = new TransitionPart();
		}

		part.setModel(model);
		return part;
	}

}

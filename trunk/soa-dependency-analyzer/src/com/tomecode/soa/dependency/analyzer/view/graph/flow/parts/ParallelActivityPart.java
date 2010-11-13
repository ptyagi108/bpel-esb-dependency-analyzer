package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import org.eclipse.draw2d.IFigure;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.figures.ParallelActivityFigure;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.figures.SubgraphFigure;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class ParallelActivityPart extends StructuredActivityPart {

	protected IFigure createFigure() {
		return new ParallelActivityFigure();
	}

	/**
	 * @see org.eclipse.gef.EditPart#setSelected(int)
	 */
	public void setSelected(int value) {
		super.setSelected(value);
		SubgraphFigure sf = (SubgraphFigure) getFigure();
		sf.setSelected(value != SELECTED_NONE);
	}

}

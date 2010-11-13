package com.tomecode.soa.dependency.analyzer.view.graph.flow;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Transition extends FlowElement {

	private static final long serialVersionUID = -4162803392523633294L;
	public Activity source, target;

	public Transition(Activity source, Activity target) {
		this.source = source;
		this.target = target;
		source.addOutput(this);
		target.addInput(this);
	}

}

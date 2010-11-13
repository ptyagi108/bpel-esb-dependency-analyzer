package com.tomecode.soa.dependency.analyzer.view.graph.flow.policies;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.TransitionPart;

//import org.eclipse.gef.examples.flow.model.StructuredActivity;
//import org.eclipse.gef.examples.flow.model.Transition;
//import org.eclipse.gef.examples.flow.model.commands.DeleteConnectionCommand;
//import org.eclipse.gef.examples.flow.model.commands.SplitTransitionCommand;

/**
 * EditPolicy for Transitions. Supports deletion and "splitting", i.e. adding an
 * Activity that splits the transition into an incoming and outgoing connection
 * to the new Activity.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class TransitionEditPolicy extends ConnectionEditPolicy {

	/**
	 * @see org.eclipse.gef.editpolicies.ConnectionEditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	public Command getCommand(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			return getSplitTransitionCommand(request);
		return super.getCommand(request);
	}

	private PolylineConnection getConnectionFigure() {
		return ((PolylineConnection) ((TransitionPart) getHost()).getFigure());
	}

	/**
	 * @see ConnectionEditPolicy#getDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command getDeleteCommand(GroupRequest request) {
		// DeleteConnectionCommand cmd = new DeleteConnectionCommand();
		// Transition t = (Transition)getHost().getModel();
		// cmd.setTransition(t);
		// cmd.setSource(t.source);
		// cmd.setTarget(t.target);
		// return cmd;
		return null;
	}

	protected Command getSplitTransitionCommand(Request request) {
		// SplitTransitionCommand cmd = new SplitTransitionCommand();
		// cmd.setTransition(((Transition)getHost().getModel()));
		// cmd.setParent(
		// ((StructuredActivity) ((TransitionPart) getHost())
		// .getSource()
		// .getParent()
		// .getModel()));
		// cmd.setNewActivity(((Activity)((CreateRequest)request).getNewObject()));
		// return cmd;
		return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
	 */
	public EditPart getTargetEditPart(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			return getHost();
		return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseTargetFeedback(org.eclipse.gef.Request)
	 */
	public void eraseTargetFeedback(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			getConnectionFigure().setLineWidth(1);
	}

	/**
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#showTargetFeedback(org.eclipse.gef.Request)
	 */
	public void showTargetFeedback(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			getConnectionFigure().setLineWidth(2);
	}

}

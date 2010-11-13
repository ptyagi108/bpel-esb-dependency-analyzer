package com.tomecode.soa.dependency.analyzer.view.graph.flow.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.Activity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.ActivityPart;

//import org.eclipse.gef.examples.flow.model.Activity;
//import org.eclipse.gef.examples.flow.model.Transition;
//import org.eclipse.gef.examples.flow.model.commands.ConnectionCreateCommand;
//import org.eclipse.gef.examples.flow.model.commands.ReconnectSourceCommand;
//import org.eclipse.gef.examples.flow.model.commands.ReconnectTargetCommand;
//import org.eclipse.gef.examples.flow.parts.ActivityPart;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class ActivityNodeEditPolicy extends GraphicalNodeEditPolicy {

	/**
	 * @see GraphicalNodeEditPolicy#getConnectionCompleteCommand(CreateConnectionRequest)
	 */
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		// ConnectionCreateCommand cmd =
		// (ConnectionCreateCommand)request.getStartCommand();
		// cmd.setTarget(getActivity());
		// return cmd;
		return null;
	}

	/**
	 * @see GraphicalNodeEditPolicy#getConnectionCreateCommand(CreateConnectionRequest)
	 */
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// ConnectionCreateCommand cmd = new ConnectionCreateCommand();
		// cmd.setSource(getActivity());
		// request.setStartCommand(cmd);
		// return cmd;
		return null;
	}

	/**
	 * Returns the ActivityPart on which this EditPolicy is installed
	 * 
	 * @return the
	 */
	protected ActivityPart getActivityPart() {
		return (ActivityPart) getHost();
	}

	/**
	 * Returns the model associated with the EditPart on which this EditPolicy
	 * is installed
	 * 
	 * @return the model
	 */
	protected Activity getActivity() {
		return (Activity) getHost().getModel();
	}

	/**
	 * @see GraphicalNodeEditPolicy#getReconnectSourceCommand(ReconnectRequest)
	 */
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// ReconnectSourceCommand cmd = new ReconnectSourceCommand();
		// cmd.setTransition((Transition)request.getConnectionEditPart().getModel());
		// cmd.setSource(getActivity());
		// return cmd;
		return null;
	}

	/**
	 * @see GraphicalNodeEditPolicy#getReconnectTargetCommand(ReconnectRequest)
	 */
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// ReconnectTargetCommand cmd = new ReconnectTargetCommand();
		// cmd.setTransition((Transition)request.getConnectionEditPart().getModel());
		// cmd.setTarget(getActivity());
		// return cmd;
		return null;
	}

}

package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

//import org.eclipse.gef.examples.flow.model.Activity;
//import org.eclipse.gef.examples.flow.model.StructuredActivity;
//import org.eclipse.gef.examples.flow.model.commands.AddAndAssignSourceCommand;
//import org.eclipse.gef.examples.flow.model.commands.CreateAndAssignSourceCommand;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class ActivitySourceEditPolicy extends ContainerEditPolicy {

	/**
	 * @see org.eclipse.gef.editpolicies.ContainerEditPolicy#getAddCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command getAddCommand(GroupRequest request) {
		// CompoundCommand cmd = new CompoundCommand();
		// for (int i = 0; i < request.getEditParts().size(); i++) {
		// AddAndAssignSourceCommand add = new AddAndAssignSourceCommand();
		// add.setParent((StructuredActivity)getHost().getParent().getModel());
		// add.setSource((Activity)getHost().getModel());
		// add.setChild(((Activity)((EditPart)request.getEditParts().get(i)).getModel()));
		// cmd.add(add);
		// }
		// return cmd;
		return null;
	}

	/**
	 * @see ContainerEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		// CreateAndAssignSourceCommand cmd = new
		// CreateAndAssignSourceCommand();
		// cmd.setParent((StructuredActivity)getHost().getParent().getModel());
		// cmd.setChild((Activity)request.getNewObject());
		// cmd.setSource((Activity)getHost().getModel());
		// return cmd;
		return null;
	}

	/**
	 * @see AbstractEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
	 */
	public EditPart getTargetEditPart(Request request) {
		if (REQ_CREATE.equals(request.getType()))
			return getHost();
		if (REQ_ADD.equals(request.getType()))
			return getHost();
		if (REQ_MOVE.equals(request.getType()))
			return getHost();
		return super.getTargetEditPart(request);
	}

}

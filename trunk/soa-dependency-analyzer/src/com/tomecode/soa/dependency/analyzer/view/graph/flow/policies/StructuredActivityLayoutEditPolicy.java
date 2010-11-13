package com.tomecode.soa.dependency.analyzer.view.graph.flow.policies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.SimpleActivityPart;

//import org.eclipse.gef.examples.flow.model.Activity;
//import org.eclipse.gef.examples.flow.model.StructuredActivity;
//import org.eclipse.gef.examples.flow.model.commands.AddCommand;
//import org.eclipse.gef.examples.flow.model.commands.CreateCommand;
//import org.eclipse.gef.examples.flow.parts.SimpleActivityPart;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class StructuredActivityLayoutEditPolicy extends LayoutEditPolicy {

	protected Command createAddCommand(EditPart child) {
		// Activity activity = (Activity)child.getModel();
		// AddCommand add = new AddCommand();
		// add.setParent((StructuredActivity)getHost().getModel());
		// add.setChild(activity);
		// return add;
		return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		if (child instanceof SimpleActivityPart)
			return new SimpleActivitySelectionEditPolicy();
		return new NonResizableEditPolicy();
	}

	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return null;
	}

	protected Command getAddCommand(Request req) {
		ChangeBoundsRequest request = (ChangeBoundsRequest) req;
		List editParts = request.getEditParts();
		CompoundCommand command = new CompoundCommand();
		for (int i = 0; i < editParts.size(); i++) {
			EditPart child = (EditPart) editParts.get(i);
			command.add(createAddCommand(child));
		}
		return command.unwrap();
	}

	/**
	 * @see LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		// CreateCommand command = new CreateCommand();
		// command.setParent((StructuredActivity) getHost().getModel());
		// command.setChild((Activity) request.getNewObject());
		// return command;
		return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getMoveChildrenCommand(org.eclipse.gef.Request)
	 */
	protected Command getMoveChildrenCommand(Request request) {
		return null;
	}

}

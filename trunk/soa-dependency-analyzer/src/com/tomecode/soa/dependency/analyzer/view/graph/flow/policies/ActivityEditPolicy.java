package com.tomecode.soa.dependency.analyzer.view.graph.flow.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class ActivityEditPolicy extends ComponentEditPolicy {

	/**
	 * @see ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		// StructuredActivity parent =
		// (StructuredActivity)(getHost().getParent().getModel());
		// DeleteCommand deleteCmd = new DeleteCommand();
		// deleteCmd.setParent(parent);
		// deleteCmd.setChild((Activity)(getHost().getModel()));
		// return deleteCmd;
		return null;
	}

}

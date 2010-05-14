package com.tomecode.soa.oracle10g.bpel.activity;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
 * Assign in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Assign extends Activity {

	private final List<AssignOperation> operations;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Assign(String name) {
		super(ActivityType.ASSIGN, name);
		operations = new ArrayList<AssignOperation>();
	}

	public final void addOperations(AssignOperation operation) {
		operations.add(operation);
	}

	/**
	 * 
	 * assign operation
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public static final class AssignOperation {

		private OperationType type;

		private String from;
		private String to;

		/**
		 * Constructor
		 * 
		 * @param type
		 * @param from
		 * @param to
		 */
		public AssignOperation(OperationType type, String from, String to) {
			this.type = type;
			this.from = from;
			this.to = to;
		}

		public final OperationType getType() {
			return type;
		}

		public final String getFrom() {
			return from;
		}

		public final String getTo() {
			return to;
		}

	}

	/**
	 * assing operation type
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public static enum OperationType {
		COPY, APPEND, INSERT_AFTER, INSERT_BEFORE, COPYLIST, REMOVE, RENAME;
	}

	public final void findVariable(FindUsageVariableResult findUsageVariableResult) {
		for (AssignOperation operation : operations) {
			if (operation.getFrom() != null && operation.getFrom().equals(findUsageVariableResult.getVariable().getName())) {
				findUsageVariableResult.addUsage(this);
			} else if (operation.getTo() != null && operation.getTo().equals(findUsageVariableResult.getVariable().getName())) {
				findUsageVariableResult.addUsage(this);
			}
		}
	}
}

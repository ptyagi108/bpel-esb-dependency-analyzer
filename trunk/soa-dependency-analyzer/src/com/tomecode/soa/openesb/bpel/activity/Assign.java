package com.tomecode.soa.openesb.bpel.activity;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

/**
 * assign activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Assign extends Activity {

	private static final long serialVersionUID = 4362082156260815421L;

	private List<Copy> copies;

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param name
	 */
	public Assign(ActivityType type, String name) {
		super(type, name);
		this.copies = new ArrayList<Copy>();
	}

	public final void addCopy(Copy copy) {
		this.copies.add(copy);
	}

	/**
	 * copy element for {@link Assign}
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public static final class Copy {

		private String from;
		private String to;

		public Copy(String from, String to) {
			this.from = from;
			this.to = to;
		}

		/**
		 * @return the from
		 */
		public final String getFrom() {
			return from;
		}

		/**
		 * @return the to
		 */
		public final String getTo() {
			return to;
		}

	}
}

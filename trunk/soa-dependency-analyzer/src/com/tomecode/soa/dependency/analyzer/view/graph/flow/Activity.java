package com.tomecode.soa.dependency.analyzer.view.graph.flow;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tomecode.soa.dependency.analyzer.view.graph.FlowGraphView;

/**
 * Activity in {@link FlowGraphView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Activity extends FlowElement {

	private static final long serialVersionUID = -3196272959309696782L;

	public static final String NAME = "name";
	protected static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new TextPropertyDescriptor(NAME, "Name") };

	private final List<Transition> inputs;
	private final List<Transition> outputs;
	private String name;
	private int sortIndex;

	private Object data;

	public Activity(Object data) {
		this.data = data;
		this.inputs = new ArrayList<Transition>();
		this.outputs = new ArrayList<Transition>();
		name = "...activity...";
		if (data != null) {
			this.name = data.toString();
		}
	}

	public Activity(String name, Object data) {
		this(data);
		this.name = name;

		firePropertyChange(NAME, null, name);
	}

	public final void addInput(Transition transition) {
		inputs.add(transition);
		fireStructureChange(INPUTS, transition);
	}

	public final void addOutput(Transition transtition) {
		outputs.add(transtition);
		fireStructureChange(OUTPUTS, transtition);
	}

	public final List<Transition> getIncomingTransitions() {
		return inputs;
	}

	public final String getName() {
		return name;
	}

	public final List<Transition> getOutgoingTransitions() {
		return outputs;
	}

	// public List getConnections() {
	// Vector v = (Vector)outputs.clone();
	// Enumeration ins = inputs.elements();
	// while (ins.hasMoreElements())
	// v.addElement(ins.nextElement());
	// return v;
	// }

	/**
	 * Returns useful property descriptors for the use in property sheets. this
	 * supports location and size.
	 * 
	 * @return Array of property descriptors.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	/**
	 * Returns an Object which represents the appropriate value for the property
	 * name supplied.
	 * 
	 * @param propName
	 *            Name of the property for which the the values are needed.
	 * @return Object which is the value of the property.
	 */
	public Object getPropertyValue(Object propName) {
		if (NAME.equals(propName))
			return getName();
		return super.getPropertyValue(propName);
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void removeInput(Transition transition) {
		inputs.remove(transition);
		fireStructureChange(INPUTS, transition);
	}

	public void removeOutput(Transition transition) {
		outputs.remove(transition);
		fireStructureChange(OUTPUTS, transition);
	}

	public final void setName(String name) {
		this.name = name;
		firePropertyChange(NAME, null, name);
	}

	public void clear() {
		outputs.clear();
		inputs.clear();
		fireStructureChange(CHILDREN, null);
	}

	/**
	 * Sets the value of a given property with the value supplied.
	 * 
	 * @param id
	 *            Name of the parameter to be changed.
	 * @param value
	 *            Value to be set to the given parameter.
	 */
	public void setPropertyValue(Object id, Object value) {
		if (id == NAME)
			setName((String) value);
	}

	public void setSortIndex(int i) {
		sortIndex = i;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className + "(" + name + ")";
	}

	/**
	 * @return the data
	 */
	public final Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public final void setData(Object data) {
		this.data = data;
	}

}

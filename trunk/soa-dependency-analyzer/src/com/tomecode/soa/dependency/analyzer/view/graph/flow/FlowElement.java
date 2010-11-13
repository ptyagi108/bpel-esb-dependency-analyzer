package com.tomecode.soa.dependency.analyzer.view.graph.flow;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
abstract public class FlowElement implements IPropertySource, Cloneable, Serializable {

	private static final long serialVersionUID = -3330608741990101770L;
	public static final String CHILDREN = "Children";
	public static final String INPUTS = "inputs";
	public static final String OUTPUTS = "outputs";

	transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public final void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.addPropertyChangeListener(l);
	}

	protected final void firePropertyChange(String prop, Object old, Object newValue) {
		listeners.firePropertyChange(prop, old, newValue);
	}

	protected final void fireStructureChange(String prop, Object child) {
		listeners.firePropertyChange(prop, null, child);
	}

	public final Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[0];
	}

	public Object getPropertyValue(Object propName) {
		return null;
	}

	public final boolean isPropertySet(Object propName) {
		return true;
	}

	private final void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		listeners = new PropertyChangeSupport(this);
	}

	public final void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.removePropertyChangeListener(l);
	}

	public final void resetPropertyValue(Object propName) {
	}

	public void setPropertyValue(Object propName, Object val) {
	}

}

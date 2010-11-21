package com.tomecode.soa.ora.osb10g.services.dependnecies;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.services.Service;

/**
 * 
 * Simple object which contain {@link OsbActivity} has reference to
 * {@link #targetService}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ServiceDependency {

	/**
	 * reference path
	 */
	private String refPath;
	/**
	 * activity where contains service reference
	 */
	private OsbActivity activity;

	private final List<Service> services;

	private ServiceDependencyType type;

	/**
	 * Constructor
	 * 
	 * @param activity
	 * @param refPath
	 * @param type
	 */
	public ServiceDependency(OsbActivity activity, String refPath, ServiceDependencyType type) {
		this.services = new ArrayList<Service>();
		this.activity = activity;
		this.refPath = refPath;
		this.type = type;
	}

	public final OsbActivity getActivity() {
		return activity;
	}

	/**
	 * @return the refPath
	 */
	public final String getRefPath() {
		return refPath;
	}

	public final String getRefPathWithoutServiceName() {
		int index = refPath.lastIndexOf("/");
		if (index != -1) {
			return refPath.substring(0, index);
		}
		return refPath;
	}

	/**
	 * @return the type
	 */
	public final ServiceDependencyType getType() {
		return type;
	}

	/**
	 * @param targetService
	 *            the targetService to set
	 */
	public final void addService(Service targetService) {
		if (!services.contains(targetService)) {
			services.add(targetService);
		}
	}

	public final List<Service> getServices() {
		return services;
	}

	/**
	 * 
	 * @return name of project from {@link #refPath}
	 */
	public final String getProjectNameFromRefPath() {
		if (refPath != null) {
			int index = refPath.indexOf("/");
			if (index != -1) {
				return refPath.substring(0, index);
			}
		}
		return null;
	}

	/**
	 * 
	 * @return service name from {@link #refPath}
	 */
	public final String getServiceName() {
		if (refPath != null) {
			int index = refPath.lastIndexOf("/");
			if (index != -1) {
				return refPath.substring(index + 1);
			}
		}
		return null;
	}

	public final String getRealPath() {
		if (refPath != null) {
			String realPath = null;
			int index = refPath.indexOf("/");
			if (index != -1) {
				realPath = refPath.substring(index + 1);
			}
			index = realPath.lastIndexOf("/");
			if (index != -1) {
				return realPath.substring(0, index);

			}
		}
		return null;
	}

	public final String toString() {
		return refPath;
	}

	/**
	 * 
	 * 
	 * Type of dependency
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum ServiceDependencyType {
		/**
		 * proxy service
		 */
		PROXY_SERVICE("ProxyRef", "Proxy Service"),
		/**
		 * business service
		 */
		BUSINESS_SERVICE("BusinessServiceRef", "Business Service"),

		/**
		 * WSDL
		 */
		WSDL("", "WSDL"),

		/**
		 * Unknown file
		 */
		UNKNOWN("", "Unknown"),
		/**
		 * split join
		 */
		SPLITJOIN("", "SplitJoin flow"),

		XQUERY("", "Xquery"), XMLSCHEMA("", "XMLSchema"), SERVICEACCOUNT("", "Service Account"), ARCHIVE("", "Archive"), SKP("", "Service Key Provider");

		/**
		 * in element
		 */
		private final String element;

		/**
		 * name
		 */
		private final String name;

		/**
		 * Constructor
		 * 
		 * @param element
		 * @param name
		 */
		private ServiceDependencyType(String element, String name) {
			this.element = element;
			this.name = name;
		}

		public final static ServiceDependencyType parse(String type) {
			if (type == null) {
				return UNKNOWN;
			}

			int index = type.indexOf(":");
			if (index != -1) {
				type = type.substring(index + 1);
			}
			for (ServiceDependencyType dependencyType : values()) {
				if (dependencyType.element.equals(type)) {
					return dependencyType;
				}
			}
			return UNKNOWN;
		}

		public final String toString() {
			return name;
		}
	}

}

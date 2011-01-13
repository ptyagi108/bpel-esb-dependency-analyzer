package com.tomecode.soa.ora.osb10g.services;

/**
 * 
 * Service binding
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Binding {

	private final BindingType type;

	private String request;

	private String response;

	private boolean isSoap12;

	private String wsdlRef;

	private WsdlServiceBinding wsdlServiceBinding;

	private String wsdlOperation;

	public Binding(BindingType bindingType, boolean isSoap12) {
		this.type = bindingType;
		this.isSoap12 = isSoap12;
	}

	public final BindingType getType() {
		return type;
	}

	public final String getWsdlRef() {
		return wsdlRef;
	}

	public final void setWsdlRef(String wsdlRef) {
		this.wsdlRef = wsdlRef;
	}

	public final boolean isSoap12() {
		return isSoap12;
	}

	public final WsdlServiceBinding getWsdlServiceBinding() {
		return wsdlServiceBinding;
	}

	public final void setWsdlServiceBinding(WsdlServiceBinding wsdlServiceBinding) {
		this.wsdlServiceBinding = wsdlServiceBinding;
	}

	public final String getWsdlOperation() {
		return wsdlOperation;
	}

	public final void setWsdlOperation(String wsdlOperation) {
		this.wsdlOperation = wsdlOperation;
	}

	/**
	 * @return the request
	 */
	public final String getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public final void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public final String getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public final void setResponse(String response) {
		this.response = response;
	}

	/**
	 * 
	 * Service binding type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum BindingType {
		UNKNOWN(""), SOAP_SERVICES("SOAP"), MESSAGING_SERVICE("Mixed"), ANY_SOAP_SERVICES("abstract SOAP"), ANY_XML_SERVICES("abstract XML");

		private final String name;

		private BindingType(String name) {
			this.name = name;
		}

		public static final BindingType parse(String value) {
			if (value != null) {
				for (BindingType type : values()) {
					if (type.name.equals(value)) {
						return type;
					}
				}
			}
			return UNKNOWN;
		}
	}

	/**
	 * 
	 * WSDL service binding
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 * 
	 */
	public static class WsdlServiceBinding {
		private WsldServiceBindingType type;
		private String name;
		private String namespace;

		public WsdlServiceBinding(WsldServiceBindingType type, String name, String namespace) {
			this.type = type;
			this.name = name;
			this.namespace = namespace;
		}

		public final WsldServiceBindingType getType() {
			return type;
		}

		public final String getName() {
			return name;
		}

		public final String getNamespace() {
			return namespace;
		}

	}

	/**
	 * 
	 * wsdl service binding type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public static enum WsldServiceBindingType {
		PORT, BINDING
	}
}

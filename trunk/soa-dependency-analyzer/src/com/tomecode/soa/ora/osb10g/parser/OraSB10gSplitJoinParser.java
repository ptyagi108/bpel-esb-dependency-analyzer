package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Assign;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.AssignAction;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Catch;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.CatchAll;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Copy;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.DataLayout;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Delete;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Else;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Elseif;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.ErrorHandlers;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Flow;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.ForEach;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.If;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.IfAction;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Insert;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Invoke;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.JavaCallout;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Log;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.LogicFlyout;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.PartnerLink;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.PartnerLinks;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.RaiseError;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.ReRaiseError;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Receive;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.RepeatUntil;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Replace;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Reply;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Scope;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Variable;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.Variables;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.While;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.osb10g.services.SplitJoinStructure;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * Oracle SB 10g - SplitJoin parser
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gSplitJoinParser extends AbstractParser {

	/**
	 * parse {@link SplitJoin} file
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	public final SplitJoin parseSplitJoin(File file) throws ServiceParserException {
		Element eXml = parseXml(file);
		SplitJoin splitJoin = new SplitJoin(file, eXml.attributeValue("name"));
		parseActivities(splitJoin.getServiceDependencies(), eXml.elements(), splitJoin.getStructure());
		return splitJoin;
	}

	/**
	 * parse all activities
	 * 
	 * @param elements
	 * @param structure
	 */
	private final void parseActivities(ServiceDependencies serviceDepependnecies, List<?> elements, OsbActivity root) {
		for (Object o : elements) {
			Element element = (Element) o;
			if ("partnerLinks".equals(element.getName())) {
				if ("process".equals(element.getParent().getName())) {
					DataLayout leftArrow = new DataLayout();
					root.addActivity(leftArrow);
					PartnerLinks partnerLinks = new PartnerLinks();
					leftArrow.addActivity(partnerLinks);
					parseActivities(serviceDepependnecies, element.elements(), partnerLinks);
				}

			} else if ("partnerLink".equals(element.getName())) {
				root.addActivity(new PartnerLink(element.attributeValue("name"), element.attributeValue("partnerLinkType"), element.attributeValue("myRole"), element.attributeValue("partnerRole")));
			} else if ("receive".equals(element.getName())) {
				Receive receive = new Receive(element.attributeValue("partnerLink"), element.attributeValue("operation"), element.attributeValue("variable"));
				root.addActivity(receive);
				Element eReceiveInfo = element.element("receiveInfo");
				if (eReceiveInfo != null) {
					Element eWsdl = eReceiveInfo.element("wsdl");
					if (eWsdl != null) {
						serviceDepependnecies.addDependency(new ServiceDependency(receive, eWsdl.attributeValue("ref"), ServiceDependencyType.WSDL));
					}
				}

			} else if ("assign".equals(element.getName())) {
				if ("extensionAssignOperation".equals(element.getParent().getName())) {
					AssignAction assignAction = new AssignAction();
					root.addActivity(assignAction);
				} else {
					Assign assignSplitJoin = new Assign();
					root.addActivity(assignSplitJoin);
					parseActivities(serviceDepependnecies, element.elements(), assignSplitJoin);
				}
			} else if ("flow".equals(element.getName())) {
				Flow flow = new Flow();
				root.addActivity(flow);
				parseActivities(serviceDepependnecies, element.elements(), flow);
			} else if ("scope".equals(element.getName())) {
				Scope scope = new Scope(element.attributeValue("name"));
				root.addActivity(scope);
				parseActivities(serviceDepependnecies, element.elements(), scope);
			} else if ("faultHandlers".equals(element.getName())) {
				OsbActivity errorHandlers = null;
				if ("process".equals(element.getParent().getName())) {
					SplitJoinStructure structure = (SplitJoinStructure) root;
					OsbActivity logicFlyout = structure.getLogicFlyout();
					if (logicFlyout == null) {
						logicFlyout = new LogicFlyout();
						structure.addActivity(logicFlyout);
						errorHandlers = new ErrorHandlers();
						logicFlyout.addActivity(errorHandlers);
					} else {
						errorHandlers = logicFlyout.getErroHandlers();
					}
				} else {
					Scope scope = (Scope) root;
					OsbActivity logicFlyout = scope.getLogicFlyout();
					if (logicFlyout == null) {
						logicFlyout = new LogicFlyout();
						scope.addActivity(logicFlyout);
						errorHandlers = new ErrorHandlers();
						logicFlyout.addActivity(errorHandlers);
					} else {
						errorHandlers = logicFlyout.getErroHandlers();
					}
				}
				parseActivities(serviceDepependnecies, element.elements(), errorHandlers);
			} else if ("catch".equals(element.getName())) {
				Catch catchh = new Catch();
				root.addActivity(catchh);
				parseActivities(serviceDepependnecies, element.elements(), catchh);
			} else if ("catchAll".equals(element.getName())) {
				CatchAll catchAll = new CatchAll();
				root.addActivity(catchAll);
				parseActivities(serviceDepependnecies, element.elements(), catchAll);
			} else if ("variables".equals(element.getName())) {
				if ("process".equals(element.getParent().getName())) {
					SplitJoinStructure structure = (SplitJoinStructure) root;
					OsbActivity dataFlyout = structure.getDataFlyout();
					if (dataFlyout == null) {
						dataFlyout = new DataLayout();
						structure.addActivity(dataFlyout);
					}

					parseVariables(element, dataFlyout, false);
				} else if (root instanceof Scope) {
					Scope scope = (Scope) root;
					OsbActivity dataFlyout = scope.getDataFlyout();
					if (dataFlyout == null) {
						dataFlyout = new DataLayout();
						scope.addActivity(dataFlyout);
					}
					parseVariables(element, dataFlyout, true);
				}
			} else if ("invoke".equals(element.getName())) {
				Invoke invoke = new Invoke(element.attributeValue("name"), element.attributeValue("operation"), element.attributeValue("partnerLink"), element.attributeValue("inputVariable"), element
						.attributeValue("outputVariable"));
				root.addActivity(invoke);

				Element eInvokeInfo = element.element("invokeInfo");
				if (eInvokeInfo != null) {
					Element eService = eInvokeInfo.element("service");
					boolean isProxy = Boolean.parseBoolean(eService.attributeValue("isProxy"));
					serviceDepependnecies.addDependency(new ServiceDependency(invoke, eService.attributeValue("ref"), isProxy ? ServiceDependencyType.PROXY_SERVICE : ServiceDependencyType.WSDL));
				}

			} else if ("reply".equals(element.getName())) {
				Reply reply = new Reply(element.attributeValue("partnerLink"), element.attributeValue("operation"), element.attributeValue("variable"));
				root.addActivity(reply);
			} else if ("extensionAssignOperation".equals(element.getName())) {
				parseActivities(serviceDepependnecies, element.elements(), root);
			} else if ("copy".equals(element.getName())) {
				root.addActivity(new Copy());
			} else if ("replace".equals(element.getName())) {
				Replace replace = new Replace();
				root.addActivity(replace);
			} else if ("log".equals(element.getName())) {
				root.addActivity(new Log());
			} else if ("javaCallout".equals(element.getName())) {
				root.addActivity(new JavaCallout());
			} else if ("delete".equals(element.getName())) {
				root.addActivity(new Delete());
			} else if ("insert".equals(element.getName())) {
				Insert insert = new Insert();
				root.addActivity(insert);
			} else if ("forEach".equals(element.getName())) {
				ForEach forEach = new ForEach();
				root.addActivity(forEach);
				parseActivities(serviceDepependnecies, element.elements(), forEach);
			} else if ("if".equals(element.getName())) {
				If iff = new If();
				root.addActivity(iff);
				parseActivities(serviceDepependnecies, element.elements(), iff);
			} else if ("sequence".equals(element.getName()) && (root instanceof If)) {
				IfAction ifAction = new IfAction();
				root.addActivity(ifAction);
				parseActivities(serviceDepependnecies, element.elements(), ifAction);
			} else if ("sequence".equals(element.getName()) && (root instanceof Elseif) || (root instanceof Else)) {
				parseActivities(serviceDepependnecies, element.elements(), root);
			} else if ("elseif".equals(element.getName())) {
				Elseif elseif = new Elseif();
				root.addActivity(elseif);
				parseActivities(serviceDepependnecies, element.elements(), elseif);
			} else if ("throw".equals(element.getName())) {
				root.addActivity(new RaiseError());
			} else if ("rethrow".equals(element.getName())) {
				root.addActivity(new ReRaiseError());
			} else if ("repeatUntil".equals(element.getName())) {
				RepeatUntil repeatUntil = new RepeatUntil();
				root.addActivity(repeatUntil);
				parseActivities(serviceDepependnecies, element.elements(), repeatUntil);
			} else if ("sequence".equals(element.getName()) && (root instanceof RepeatUntil)) {
				parseActivities(serviceDepependnecies, element.elements(), root);
			} else if ("while".equals(element.getName())) {
				While while1 = new While();
				root.addActivity(while1);
				parseActivities(serviceDepependnecies, element.elements(), while1);
			} else if ("sequence".equals(element.getName()) && (root instanceof While)) {
				parseActivities(serviceDepependnecies, element.elements(), root);
			} else if ("sequence".equals(element.getName())) {
				parseActivities(serviceDepependnecies, element.elements(), root);
			}
		}
	}

	/**
	 * parse element: variable
	 * 
	 * @param eVariables
	 *            current element in XML
	 * @param structure
	 */
	private final void parseVariables(Element eVariables, OsbActivity root, boolean isPrivate) {
		if (eVariables != null) {
			Variables variables = new Variables();
			root.addActivity(variables);
			for (Object o : eVariables.elements()) {
				Element element = (Element) o;
				if ("variable".equals(element.getName())) {
					variables.addActivity(new Variable(element.attributeValue("name"), element.attributeValue("messageType"), isPrivate));
				}
			}
		}
	}

}

package com.tomecode.soa.openesb.bpel.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.Catch;
import com.tomecode.soa.bpel.activity.Empty;
import com.tomecode.soa.bpel.activity.Invoke;
import com.tomecode.soa.bpel.activity.Receive;
import com.tomecode.soa.bpel.activity.Reply;
import com.tomecode.soa.bpel.activity.Throw;
import com.tomecode.soa.bpel.activity.Variable;
import com.tomecode.soa.bpel.activity.While;
import com.tomecode.soa.openesb.bpel.Import;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcessStructure;
import com.tomecode.soa.openesb.bpel.PartnerLink;
import com.tomecode.soa.openesb.bpel.activity.Assign;
import com.tomecode.soa.openesb.bpel.activity.Compensate;
import com.tomecode.soa.openesb.bpel.activity.CompensateScope;
import com.tomecode.soa.openesb.bpel.activity.Else;
import com.tomecode.soa.openesb.bpel.activity.Exit;
import com.tomecode.soa.openesb.bpel.activity.ForEach;
import com.tomecode.soa.openesb.bpel.activity.If;
import com.tomecode.soa.openesb.bpel.activity.OnAlarm;
import com.tomecode.soa.openesb.bpel.activity.OnEvent;
import com.tomecode.soa.openesb.bpel.activity.OnMessage;
import com.tomecode.soa.openesb.bpel.activity.RepeatUntil;
import com.tomecode.soa.openesb.bpel.activity.Rethrow;
import com.tomecode.soa.openesb.bpel.activity.Wait;
import com.tomecode.soa.openesb.bpel.activity.Assign.Copy;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * 
 * Open ESB - BPEL process parser
 * 
 * 
 * @author Frastia Tomas
 * 
 */
public final class OpenEsbBpelParser extends AbstractParser {

	public final OpenEsbBpelProcess parseBpel(File bpelFile) throws ServiceParserException {
		return parseBpelXml(parseXml(bpelFile), bpelFile);
	}

	/**
	 * parse *.bpel XML file
	 * 
	 * @param eProcess
	 *            parsed BPEL process to dom4j
	 * @param bpelProcessFile
	 *            BPEL process real path
	 * @return new {@link OpenEsbBpelProcess}
	 */
	private final OpenEsbBpelProcess parseBpelXml(Element eProcess, File bpelProcessFile) {
		String bpelProcessName = eProcess.attributeValue("name");

		OpenEsbBpelProcess process = new OpenEsbBpelProcess(bpelProcessFile, bpelProcessName);

		parseImports(process, eProcess.elements("import"));
		parsePartnerLinks(process, eProcess.element("partnerLinks"));
		parseProcessStructure(process, eProcess);
		return process;

	}

	/**
	 * parse BPEL process structure
	 * 
	 * @param process
	 *            current parsed BPEL process
	 * @param eProcess
	 *            parsed BPEL process to dom4j
	 */
	private final void parseProcessStructure(OpenEsbBpelProcess process, Element eProcess) {
		OpenEsbBpelProcessStructure structure = new OpenEsbBpelProcessStructure(process);
		process.setProcessStructure(structure);

		Activity activity = new Activity(eProcess.getName());
		structure.addActivity(activity);

		parseProcessActivities(eProcess.elements(), activity, structure);
	}

	/**
	 * parse all activities in BPEL process to tree structure
	 * 
	 * @param elements
	 * @param root
	 * @param strukture
	 */
	private final void parseProcessActivities(List<?> elements, Activity root, OpenEsbBpelProcessStructure strukture) {
		for (Object e : elements) {
			Element element = (Element) e;
			if (element.getName().equals("sequence") || element.getName().equals("variables") || element.getName().equals("faultHandlers") || element.getName().equals("catchAll")
					|| element.getName().equals("partnerLinks") || element.getName().equals("eventHandlers") || element.getName().equals("terminationHandler") || element.getName().equals("scope")
					|| element.getName().equals("flow") || element.getName().equals("compensationHandler") || element.getName().equals("pick")) {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
				parseProcessActivities(element.elements(), activity, strukture);

			} else if (element.getName().equals("partnerLink")) {
				com.tomecode.soa.bpel.activity.PartnerLink partnerLink = new com.tomecode.soa.bpel.activity.PartnerLink(element.attributeValue("name"), element.attributeValue("partnerLinkType"),
						element.attributeValue("myRole"), element.attributeValue("partnerRole"));
				root.addActivity(partnerLink);
			} else if (element.getName().equals("receive")) {
				root.addActivity(new Receive(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("invoke")) {
				Invoke invoke = new Invoke(element.attributeValue("name"), element.attributeValue("inputVariable"), element.attributeValue("outputVariable"), element.attributeValue("partnerLink"),
						element.attributeValue("operation"));
				root.addActivity(invoke);
				parseProcessActivities(element.elements(), invoke, strukture);
			} else if (element.getName().equals("reply")) {
				root.addActivity(new Reply(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("variable")) {
				root.addActivity(new Variable(element.attributeValue("name"), element.attributeValue("messageType")));
			} else if (element.getName().equals("catch")) {
				Catch catchActivity = new Catch(element.attributeValue("faultName"), element.attributeValue("faultVariable"));
				root.addActivity(catchActivity);
				parseProcessActivities(element.elements(), catchActivity, strukture);
			} else if (element.getName().equals("throw")) {
				root.addActivity(new Throw(element.attributeValue("name"), element.attributeValue("faultVariable")));
			} else if (element.getName().equals("onEvent")) {
				OnEvent onEvent = new OnEvent(element.attributeValue("partnerLink"), element.attributeValue("operation"), element.attributeValue("portType"), element.attributeValue("variable"),
						element.attributeValue("messageType"));
				root.addActivity(onEvent);
				parseProcessActivities(element.elements(), onEvent, strukture);
			} else if (element.getName().equals("onAlarm")) {
				OnAlarm onAlarm = parseActivityOnAlarm(element);
				root.addActivity(onAlarm);
				parseProcessActivities(element.elements(), onAlarm, strukture);
			} else if (element.getName().equals("wait")) {
				root.addActivity(parseActivityWait(element));
			} else if (element.getName().equals("exit")) {
				root.addActivity(new Exit(element.attributeValue("name")));
			} else if (element.getName().equals("rethrow")) {
				root.addActivity(new Rethrow(element.attributeValue("name")));
			} else if (element.getName().equals("rethrow")) {
				root.addActivity(new Empty(element.attributeValue("name")));
			} else if (element.getName().equals("compensateScope")) {
				root.addActivity(new CompensateScope(element.attributeValue("name")));
			} else if (element.getName().equals("compensate")) {
				root.addActivity(new Compensate(element.attributeValue("name")));
			} else if (element.getName().equals("while")) {
				While whilee = parseActivityWhile(element);
				parseProcessActivities(element.elements(), whilee, strukture);
			} else if (element.getName().equals("onMessage")) {
				OnMessage onMessageActivity = new OnMessage(element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"), element
						.attributeValue("portType"), element.attributeValue("messageExchange"));
				root.addActivity(onMessageActivity);
				parseProcessActivities(element.elements(), onMessageActivity, strukture);
			} else if (element.getName().equals("forEach")) {
				parseActivityForEach(element, root, strukture);
			} else if (element.getName().equals("repeatUntil")) {
				RepeatUntil repeatUntil = new RepeatUntil(element.attributeValue("name"), element.elementTextTrim("condition"));
				root.addActivity(repeatUntil);
				parseProcessActivities(element.elements(), repeatUntil, strukture);
			} else if (element.getName().equals("if")) {
				If if1 = new If(element.attributeValue("name"), element.elementTextTrim("condition"));
				root.addActivity(if1);
				parseProcessActivities(element.elements(), if1, strukture);
			} else if (element.getName().equals("if")) {
				If if1 = new If(element.attributeValue("name"), element.elementTextTrim("condition"));
				root.addActivity(if1);
				parseProcessActivities(element.elements(), if1, strukture);
			} else if (element.getName().equals("elseif")) {
				If if1 = new If(element.attributeValue("name"), element.elementTextTrim("condition"));
				root.addActivity(if1);
				parseProcessActivities(element.elements(), if1, strukture);
			} else if (element.getName().equals("else")) {
				Else else1 = new Else();
				root.addActivity(else1);
				parseProcessActivities(element.elements(), else1, strukture);
			} else if (element.getName().equals("assign")) {
				root.addActivity(parseActivityAssign(element));
			}

		}
	}

	/**
	 * parse values for {@link Assign}
	 * 
	 * @param eAssign
	 * @return
	 */
	private final Assign parseActivityAssign(Element eAssign) {
		Assign assign = new Assign(eAssign.attributeValue("name"));

		List<?> list = eAssign.elements();
		for (Object e : list) {
			Element eOperation = (Element) e;
			if (eOperation.getName().equals("copy")) {
				assign.addCopy(new Copy(eOperation.attributeValue("from"), eOperation.attributeValue("to")));
			}
		}

		return assign;
	}

	/**
	 * parse values for {@link ForEach}
	 * 
	 * @param eForEach
	 * @param root
	 * @param structure
	 */
	private final void parseActivityForEach(Element eForEach, Activity root, OpenEsbBpelProcessStructure structure) {
		String startCounterValue = eForEach.elementTextTrim("startCounterValue");
		String finalCounterValue = eForEach.elementTextTrim("finalCounterValue");
		String branches = null;
		Element eCompletionCondition = eForEach.element("completionCondition");
		if (eCompletionCondition != null) {
			branches = eCompletionCondition.elementTextTrim("branches");
		}
		ForEach forEach = new ForEach(eForEach.attributeValue("name"), eForEach.attributeValue("parallel"), eForEach.attributeValue("counterName"), startCounterValue, finalCounterValue, branches);
		root.addActivity(forEach);
		parseProcessActivities(eForEach.elements(), root, structure);
	}

	/**
	 * parse values for activity {@link While}
	 * 
	 * @param eWhile
	 * @return parsed {@link While}
	 */
	private final While parseActivityWhile(Element eWhile) {
		Element eCondition = eWhile.element("condition");
		if (eCondition != null) {
			return new While(eWhile.attributeValue("name"), eCondition.getTextTrim(), null);
		}
		return new While(eWhile.attributeValue("name"), null, null);
	}

	/**
	 * parse values for activity {@link Wait}
	 * 
	 * @param eWait
	 * @return parsed {@link Wait}
	 */
	private final Wait parseActivityWait(Element eWait) {
		Wait wait = new Wait();
		Element e = eWait.element("for");
		if (e != null) {
			wait.setFor(e.getTextTrim());
		}
		e = eWait.element("until");
		if (e != null) {
			wait.setUntil(e.getTextTrim());
		}
		return wait;
	}

	/**
	 * parse values for activity {@link OnAlarm}
	 * 
	 * @param eOnAlaram
	 * @return parsed {@link OnAlarm}
	 */
	private final OnAlarm parseActivityOnAlarm(Element eOnAlaram) {
		OnAlarm onAlarm = new OnAlarm();
		Element e = eOnAlaram.element("for");
		if (e != null) {
			onAlarm.setFor(e.getTextTrim());
		}
		e = eOnAlaram.element("repeatEvery");
		if (e != null) {
			onAlarm.setRepeatEvery(e.getTextTrim());
		}
		e = eOnAlaram.element("until");
		if (e != null) {
			onAlarm.setUntil(e.getTextTrim());
		}
		return onAlarm;
	}

	/**
	 * parse partnerLinks in BPEL process
	 * 
	 * @param process
	 *            current BPEL process
	 * @param ePartnerLinks
	 *            element 'partnerLinks'
	 */
	private final void parsePartnerLinks(OpenEsbBpelProcess process, Element ePartnerLinks) {
		if (ePartnerLinks != null) {
			List<?> listParnterLinks = ePartnerLinks.elements("partnerLink");
			if (listParnterLinks != null) {
				for (Object e : listParnterLinks) {
					Element ePartnerLink = (Element) e;
					parseParnterLink(process, ePartnerLink);
				}
			}
		}
	}

	/**
	 * parse 'partnerlink' element
	 * 
	 * @param process
	 *            current BPEL process
	 * @param ePartnerLink
	 *            element 'partnerLink'
	 */
	private final void parseParnterLink(OpenEsbBpelProcess process, Element ePartnerLink) {
		process.addPartnerLinks(new PartnerLink(ePartnerLink.attributeValue("name"), ePartnerLink.attributeValue("initializePartnerRole"), ePartnerLink.attributeValue("partnerLinkType"), ePartnerLink
				.attributeValue("myRole"), ePartnerLink.attributeValue("partnerRole")));
	}

	/**
	 * parse imports in BPEL process
	 * 
	 * @param process
	 *            current BPEL process
	 * @param eImports
	 *            list of imports
	 */
	private final void parseImports(OpenEsbBpelProcess process, List<?> eImports) {
		if (eImports != null) {
			for (Object e : eImports) {
				Element eImport = (Element) e;
				process.addImport(new Import(eImport.attributeValue("location")));
			}

		}
	}

}

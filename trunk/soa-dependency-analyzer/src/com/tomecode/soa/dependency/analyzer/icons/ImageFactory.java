package com.tomecode.soa.dependency.analyzer.icons;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * Contains all icons in project
 * 
 * @author Tomas Frastia
 */
public final class ImageFactory {

	public static final ImageDescriptor refresh = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/refresh.png"));

	public static final ImageDescriptor trash = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/trash.png"));
	public static final Image TRASH = trash.createImage();

	public static final Image FILE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/file.png")).createImage();
	public static final Image FOLDER = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/folder.png")).createImage();

	public static final ImageDescriptor link_with_navigator_on = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/link_with_navigator_on.png"));
	public static final ImageDescriptor link_with_navigator_off = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/link_with_navigator_off.png"));
	public static final ImageDescriptor reload_layout = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/reloadLayout.png"));
	// public static final Image RELOAD_LAYOUT = reload_layout.createImage();

	public static final Image BPEL_PROCESS_STRUCTURE_TREE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/bpelProcessStructureView.png")).createImage();

	public static final Image DEPENDNECY_BY_OPERATION_TREE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/dependencyByOperationTree.png")).createImage();

	public static final Image GRAPH_VIEW = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/graphView.png")).createImage();

	public static final Image WORKSPACE_NAVIGATOR = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/workspaceNavigator.png")).createImage();

	public static final ImageDescriptor expand_in_new_graph = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/expandInNewGraph.png"));
	public static final Image EXPAND_IN_NEW_GRAPH = expand_in_new_graph.createImage();
	public static final ImageDescriptor expand_in_exists_graph = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/expandInExistGraph.png"));
	public static final Image EXPAND_IN_EXISTS_GRAPH = expand_in_new_graph.createImage();

	public static final Image MULTI_WORKSPACE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/multi_workspace.png")).createImage();
	public static final Image WORKSPACE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/workspace.png")).createImage();

	public static final ImageDescriptor open = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/open.png"));
	public static final Image OPEN = open.createImage();

	public static final ImageDescriptor zoom_in = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/zoom_in.png"));
	public static final Image ZOOM_IN = zoom_in.createImage();
	public static final ImageDescriptor zoom_out = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/zoom_out.png"));
	public static final Image ZOOM_OUT = zoom_out.createImage();
	public static final ImageDescriptor zoom_reset = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/zoom_reset.png"));
	public static final Image ZOOM_RESET = zoom_reset.createImage();

	public static final ImageDescriptor arrow_back = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/arrow_back.png"));
	public static final Image ARROW_BACK = arrow_back.createImage();
	public static final ImageDescriptor arrow_forward = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/arrow_forward.png"));
	public static final Image ARROW_FORWARD = arrow_forward.createImage();

	public static final Image ORACLE_10G_ASSIGN = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/assign.png")).createImage();
	public static final Image ORACLE_10G_SEQUENCE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/sequence.png")).createImage();
	public static final Image ORACLE_10G_SCOPE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/scope.png")).createImage();
	public static final Image ORACLE_10G_CATCH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/catch.png")).createImage();
	public static final Image ORACLE_10G_CATCHALL = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/catchall.png")).createImage();
	public static final Image ORACLE_10G_EMPTY = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/empty.png")).createImage();
	public static final Image ORACLE_10G_RECEIVE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/receive.png")).createImage();
	public static final Image ORACLE_10G_INVOKE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/invoke.png")).createImage();
	public static final Image ORACLE_10G_REPLY = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/reply.png")).createImage();
	public static final Image ORACLE_10G_SWITCH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/switch.png")).createImage();
	public static final Image ORACLE_10G_ONALARM = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/onAlarm.png")).createImage();
	public static final Image ORACLE_10G_ONMESSAGE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/onMessage.png")).createImage();
	public static final Image ORACLE_10G_COMPENSATIONHANDLER = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/compensationHandler.png"))
			.createImage();
	public static final Image ORACLE_10G_BPEL_PROCESS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/process.png")).createImage();
	public static final Image ORACLE_10G_BPELX_EXEC = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/exec.png")).createImage();
	public static final Image ORACLE_10G_PICK = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/pick.png")).createImage();
	public static final Image ORACLE_10G_FLOW = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/flow.png")).createImage();
	public static final Image ORACLE_10G_FLOWN = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/flowN.png")).createImage();
	public static final Image ORACLE_10G_COMPENSTATE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/compensate.png")).createImage();
	public static final Image ORACLE_10G_TERMINATE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/terminate.png")).createImage();
	public static final Image ORACLE_10G_THROW = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/throw.png")).createImage();
	public static final Image ORACLE_10G_ESB = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/esb.png")).createImage();
	public static final Image ORACLE_10G_SYSTEM = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/system.png")).createImage();
	public static final Image ORACLE_10G_SERVICE_GROUPE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/serviceGroupe.gif")).createImage();
	public static final Image ORACLE_10G_SERVICE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/service.gif")).createImage();
	public static final Image ORACLE_10G_EMAIL = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/email.png")).createImage();
	public static final Image ORACLE_10G_FAX = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/fax.png")).createImage();
	public static final Image ORACLE_10G_SMS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/sms.png")).createImage();
	public static final Image ORACLE_10G_VOICE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/voice.png")).createImage();
	public static final Image ORACLE_10G_PARTNERLINK = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/partnerLink.png")).createImage();
	public static final Image ORACLE_10G_VARIABLE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/variable.png")).createImage();
	public static final Image ORACLE_10G_HUMANTASK = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/humanTask.png")).createImage();
	public static final Image ORACLE_10G_TRANSFORM = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/transform.png")).createImage();
	public static final Image ORACLE_10G_WAIT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/wait.png")).createImage();
	public static final Image ORACLE_10G_WHILE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/while.png")).createImage();
	public static final Image ORACLE_10G_PAGER = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/pager.png")).createImage();
	public static final Image ORACLE_10G_OPERATION = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/oracle10g/icons/operation.gif")).createImage();
	public static final Image ORACLE_10G_SEARCH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/search.png")).createImage();
	public static final Image ORACLE_10G_ERROR = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/error.png")).createImage();
	public static final Image ORACLE_10G_EXIT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/exit.png")).createImage();
	public static final Image ORACLE_10G_ISSUES = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/issues.png")).createImage();
	public static final Image ORACLE_10G_BDA_SMALL = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/bpel_dependency_analyzer_small.png")).createImage();
	public static final Image ORACLE_10G_BDA = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/bpel_dependency_analyzer.png")).createImage();
	public static final Image ORACLE_10G_ABOUT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/about.png")).createImage();
	public static final Image ORACLE_10G_UNKNOWN = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/unknown.png")).createImage();
	public static final Image ORACLE_10G_UNKNOWN_BIG = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/unknown_big.png")).createImage();
	public static final Image ORACLE_10G_INFO = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/info.png")).createImage();
	public static final Image ORACLE_10G_EXPORT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/export.png")).createImage();
	public static final Image ORACLE_10G_EXPORT_BIG = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/icons/export_big.png")).createImage();

	public static final Image OPEN_ESB_BPEL_SEQUENCE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/sequence.png")).createImage();
	public static final Image OPEN_ESB_BPEL_SCOPE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/scope.png")).createImage();
	public static final Image OPEN_ESB_BPEL_FLOW = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/flow.png")).createImage();

	public static final Image OPEN_ESB_BPEL_PICK = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/pick.png")).createImage();
	public static final Image OPEN_ESB_BPEL_PARTNERLINK = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/partner.png")).createImage();
	public static final Image OPEN_ESB_BPEL_RECEIVEK = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/receive.png")).createImage();
	public static final Image OPEN_ESB_BPEL_INVOKE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/invoke.png")).createImage();
	public static final Image OPEN_ESB_BPEL_REPLY = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/reply.png")).createImage();
	public static final Image OPEN_ESB_BPEL_THROW = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/throw.png")).createImage();
	public static final Image OPEN_ESB_BPEL_WAIT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/wait.png")).createImage();
	public static final Image OPEN_ESB_BPEL_RETHROW = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/rethrow.png")).createImage();
	public static final Image OPEN_ESB_BPEL_EMPTY = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/empty.png")).createImage();
	public static final Image OPEN_ESB_BPEL_COMPENSATESCOPE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/compensatescope.png")).createImage();
	public static final Image OPEN_ESB_BPEL_COMPENSATE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/compensate.png")).createImage();
	public static final Image OPEN_ESB_BPEL_WHILE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/while.png")).createImage();
	public static final Image OPEN_ESB_BPEL_FOREACH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/foreach.png")).createImage();
	public static final Image OPEN_ESB_BPEL_REPEAT_UNTIL = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/repeatuntil.png")).createImage();
	public static final Image OPEN_ESB_BPEL_IF = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/if.png")).createImage();
	public static final Image OPEN_ESB_BPEL_ASSIGN = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/assign.png")).createImage();
	public static final Image OPEN_ESB_BPEL_EXIT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/exit.png")).createImage();
	public static final Image OPEN_ESB_BPEL_VARIABLE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/variable.png")).createImage();
	public static final Image OPEN_ESB_BPEL_ONEVENT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/on_event.png")).createImage();
	public static final Image OPEN_ESB_BPEL_ONALARM = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/on_alarm.png")).createImage();
	public static final Image OPEN_ESB_BPEL_CATCH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/catch.png")).createImage();
	public static final Image OPEN_ESB_BPEL_FAULT_HANDLERS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/fault_hanlders.png")).createImage();
	public static final Image OPEN_ESB_BPEL_CATCHALL = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/catch_all.png")).createImage();
	public static final Image OPEN_ESB_BPEL_EVENT_HANLDERS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/event_hanlders.png")).createImage();
	public static final Image OPEN_ESB_BPEL_TERMINATION_HANDLER = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/termination_hanlders.png"))
			.createImage();
	public static final Image OPEN_ESB_BPEL_ONMESSAGE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/on_message_hanlder.png")).createImage();
	public static final Image OPEN_ESB_BPEL_COMPENSATIO_HANLDER = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/compensation_hanlder.png"))
			.createImage();
	public static final Image OPEN_ESB_BPEL_ELSEIF = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/else_if.png")).createImage();
	public static final Image OPEN_ESB_BPEL_ELSE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/else.png")).createImage();
	public static final Image OPEN_ESB_BPEL_PROCESS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/openEsb/16/process.png")).createImage();

	public static final Image OSB_10G_STAGE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/stage.gif")).createImage();
	public static final Image OSB_10G_BRANCH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/branch.gif")).createImage();
	public static final Image OSB_10G_SERVICE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/service.gif")).createImage();
	public static final Image OSB_10G_PIPELINE_PARI_NODE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/pipelinePair.gif")).createImage();
	public static final Image OSB_10G_PIPELINE_REQUEST = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/pipelinePairRequest.gif")).createImage();
	public static final Image OSB_10G_PIPELINE_RESPONSE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/pipelinePairResponse.gif")).createImage();
	public static final Image OSB_10G_ROUTER = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/router.gif")).createImage();
	public static final Image OSB_10G_ASSIGN = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/assign.gif")).createImage();
	public static final Image OSB_10G_DELETE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/delete.gif")).createImage();
	public static final Image OSB_10G_INSERT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/insert.gif")).createImage();
	public static final Image OSB_10G_JAVA_CALLOUT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/javaCallout.gif")).createImage();
	public static final Image OSB_10G_MFL = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/mfl.gif")).createImage();
	public static final Image OSB_10G_RENAME = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/rename.gif")).createImage();
	public static final Image OSB_10G_REPLACE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/replace.gif")).createImage();
	public static final Image OSB_10G_VALIDATE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/validate.gif")).createImage();
	public static final Image OSB_10G_SKIP = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/skip.gif")).createImage();
	public static final Image OSB_10G_REPLY = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/reply.gif")).createImage();
	public static final Image OSB_10G_ELSEIF = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/ifthen.gif")).createImage();
	public static Image OSB_10G_IFTHENELSE = OSB_10G_ELSEIF;
	public static final Image OSB_10G_TRANSPORTHEADERS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/transportHeaders.gif")).createImage();
	public static final Image OSB_10G_WSCALLOUT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/WSCallout.gif")).createImage();
	public static final Image OSB_10G_ROUTING_OPTIONS = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/routingOptions.gif")).createImage();
	public static final Image OSB_10G_FOREACH = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/foreach.gif")).createImage();
	public static final Image OSB_10G_DYNAMIC_ROUTING = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/dynamicPublish.gif")).createImage();
	public static final Image OSB_10G_ROUTE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/route.gif")).createImage();
	public static final Image OSB_10G_ROUTING_TABLE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/routingTable.gif")).createImage();
	public static final Image OSB_10G_CASE = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/case.gif")).createImage();
	public static final Image OSB_10G_DEFAULT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/default.gif")).createImage();
	public static final Image OSB_10G_ALERT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/alert.gif")).createImage();;
	public static final Image OSB_10G_LOG = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/log.gif")).createImage();;
	public static final Image OSB_10G_REPORT = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/report.gif")).createImage();
	public static final Image OSB_10G_RAISE_ERROR = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/raiseError.gif")).createImage();
	public static final Image OSB_10G_BRANCH_NODE_CONDITION = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/branchCondition.gif")).createImage();
	public static final Image OSB_10G_BRANCH_OPERATION = ImageDescriptor.createFromURL(ImageFactory.class.getResource("/icons/com/tomecode/soa/osb10g/16/branchOperational.gif")).createImage();

	private ImageFactory() {
	}

}

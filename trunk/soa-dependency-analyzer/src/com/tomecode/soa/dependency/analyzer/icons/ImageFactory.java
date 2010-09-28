package com.tomecode.soa.dependency.analyzer.icons;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.main.Activator;

/**
 * 
 * Contains all icons in project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class ImageFactory {

	// public static final ImageDescriptor treeDependenciesWithOperations =
	// ImageDescriptor.createFromURL(ImageFactory.class
	// .getResource("/icons/com/tomecode/soa/icons/treeDependenciesWithOperation.png"));

	public static final ImageDescriptor properties = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/properties.png");
	public static final Image PROPERTIES = properties.createImage();

	public static final ImageDescriptor refresh = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/refresh.png");

	public static final ImageDescriptor trash = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/trash.png");
	public static final Image TRASH = trash.createImage();

	public static final Image FILE = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/file.png").createImage();
	public static final Image FOLDER = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/folder.png").createImage();

	public static final ImageDescriptor link_with_navigator_on = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/link_with_navigator_on.png");
	public static final ImageDescriptor link_with_navigator_off = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/link_with_navigator_off.png");
	public static final ImageDescriptor reload_layout = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/reloadLayout.png");
	// public static final Image RELOAD_LAYOUT = reload_layout.createImage();

	public static final ImageDescriptor service_structure_tree = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/bpelProcessStructureView.png");
	public static final Image BPEL_PROCESS_STRUCTURE_TREE = service_structure_tree.createImage();
	public static final Image ESB_PROCESS_STRUCTURE_TREE = service_structure_tree.createImage();

	public static final ImageDescriptor dependency_by_operation_tree = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/dependencyByOperationTree.png");
	public static final Image DEPENDNECY_BY_OPERATION_TREE = dependency_by_operation_tree.createImage();

	public static final ImageDescriptor graph_view = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/graphView.png");
	public static final Image GRAPH_VIEW = graph_view.createImage();

	public static final ImageDescriptor workspace_navigator = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/workspaceNavigator.png");
	public static final Image WORKSPACE_NAVIGATOR = workspace_navigator.createImage();

	public static final ImageDescriptor expand_in_new_graph = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/expandInNewGraph.png");
	public static final Image EXPAND_IN_NEW_GRAPH = expand_in_new_graph.createImage();
	public static final ImageDescriptor expand_in_exists_graph = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/expandInExistGraph.png");
	public static final Image EXPAND_IN_EXISTS_GRAPH = expand_in_new_graph.createImage();

	public static final Image MULTI_WORKSPACE = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/multi_workspace.png").createImage();
	public static final Image WORKSPACE = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/workspace.png").createImage();

	public static final ImageDescriptor openBig = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/openBig.png");
	public static final ImageDescriptor open = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/open.png");
	public static final Image OPEN = open.createImage();

	public static final ImageDescriptor zoom_in = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/zoom_in.png");
	public static final Image ZOOM_IN = zoom_in.createImage();
	public static final ImageDescriptor zoom_out = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/zoom_out.png");
	public static final Image ZOOM_OUT = zoom_out.createImage();
	public static final ImageDescriptor zoom_reset = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/zoom_reset.png");
	public static final Image ZOOM_RESET = zoom_reset.createImage();

	public static final ImageDescriptor arrow_back = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/arrow_back.png");
	public static final Image ARROW_BACK = arrow_back.createImage();
	public static final ImageDescriptor arrow_forward = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/arrow_forward.png");
	public static final Image ARROW_FORWARD = arrow_forward.createImage();

	public static final Image ORACLE_10G_ASSIGN = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/assign.png").createImage();
	public static final Image ORACLE_10G_SEQUENCE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/sequence.png").createImage();
	public static final Image ORACLE_10G_SCOPE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/scope.png").createImage();
	public static final Image ORACLE_10G_CATCH = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/catch.png").createImage();
	public static final Image ORACLE_10G_CATCHALL = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/catchall.png").createImage();
	public static final Image ORACLE_10G_EMPTY = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/empty.png").createImage();
	public static final Image ORACLE_10G_RECEIVE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/receive.png").createImage();
	public static final Image ORACLE_10G_INVOKE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/invoke.png").createImage();
	public static final Image ORACLE_10G_REPLY = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/reply.png").createImage();
	public static final Image ORACLE_10G_SWITCH = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/switch.png").createImage();
	public static final Image ORACLE_10G_ONALARM = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/onAlarm.png").createImage();
	public static final Image ORACLE_10G_ONMESSAGE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/onMessage.png").createImage();
	public static final Image ORACLE_10G_COMPENSATIONHANDLER = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/compensationHandler.png").createImage();
	public static final Image ORACLE_10G_BPEL_PROCESS = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/process.png").createImage();
	public static final Image ORACLE_10G_BPELX_EXEC = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/exec.png").createImage();
	public static final Image ORACLE_10G_PICK = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/pick.png").createImage();
	public static final Image ORACLE_10G_FLOW = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/flow.png").createImage();
	public static final Image ORACLE_10G_FLOWN = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/flowN.png").createImage();
	public static final Image ORACLE_10G_COMPENSTATE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/compensate.png").createImage();
	public static final Image ORACLE_10G_TERMINATE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/terminate.png").createImage();
	public static final Image ORACLE_10G_THROW = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/throw.png").createImage();
	public static final Image ORACLE_10G_ESB = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/esb.png").createImage();
	public static final Image ORACLE_10G_SYSTEM = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/system.png").createImage();
	public static final Image ORACLE_10G_SERVICE_GROUPE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/serviceGroupe.gif").createImage();
	public static final Image ORACLE_10G_SERVICE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/service.gif").createImage();
	public static final Image ORACLE_10G_EMAIL = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/email.png").createImage();
	public static final Image ORACLE_10G_FAX = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/fax.png").createImage();
	public static final Image ORACLE_10G_SMS = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/sms.png").createImage();
	public static final Image ORACLE_10G_VOICE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/voice.png").createImage();
	public static final Image ORACLE_10G_PARTNERLINK = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/partnerLink.png").createImage();
	public static final Image ORACLE_10G_VARIABLE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/variable.png").createImage();
	public static final Image ORACLE_10G_HUMANTASK = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/humanTask.png").createImage();
	public static final Image ORACLE_10G_TRANSFORM = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/transform.png").createImage();
	public static final Image ORACLE_10G_WAIT = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/wait.png").createImage();
	public static final Image ORACLE_10G_WHILE = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/while.png").createImage();
	public static final Image ORACLE_10G_PAGER = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/pager.png").createImage();
	public static final Image ORACLE_10G_OPERATION = Activator.getImageDescriptor("/icons/com/tomecode/soa/oracle10g/icons/operation.gif").createImage();
	public static final Image ORACLE_10G_SEARCH = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/search.png").createImage();
	public static final Image ORACLE_10G_ERROR = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/error.png").createImage();
	public static final Image ORACLE_10G_EXIT = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/exit.png").createImage();
	public static final Image ORACLE_10G_ISSUES = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/issues.png").createImage();
	public static final Image ORACLE_10G_BDA_SMALL = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/bpel_dependency_analyzer_small.png").createImage();
	public static final Image ORACLE_10G_BDA = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/bpel_dependency_analyzer.png").createImage();
	public static final Image ORACLE_10G_ABOUT = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/about.png").createImage();
	public static final Image ORACLE_10G_INFO = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/info.png").createImage();
	public static final ImageDescriptor export = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/export2.png");
	public static final ImageDescriptor exportBig = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/exportBig.png");

	public static final Image OPEN_ESB_BPEL_SEQUENCE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/sequence.png").createImage();
	public static final Image OPEN_ESB_BPEL_SCOPE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/scope.png").createImage();
	public static final Image OPEN_ESB_BPEL_FLOW = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/flow.png").createImage();

	public static final Image OPEN_ESB_BPEL_BPEL_MODULE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/bpelModule.png").createImage();
	public static final Image OPEN_ESB_BPEL_PICK = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/pick.png").createImage();
	public static final Image OPEN_ESB_BPEL_PARTNERLINK = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/partner.png").createImage();
	public static final Image OPEN_ESB_BPEL_RECEIVEK = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/receive.png").createImage();
	public static final Image OPEN_ESB_BPEL_INVOKE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/invoke.png").createImage();
	public static final Image OPEN_ESB_BPEL_REPLY = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/reply.png").createImage();
	public static final Image OPEN_ESB_BPEL_THROW = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/throw.png").createImage();
	public static final Image OPEN_ESB_BPEL_WAIT = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/wait.png").createImage();
	public static final Image OPEN_ESB_BPEL_RETHROW = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/rethrow.png").createImage();
	public static final Image OPEN_ESB_BPEL_EMPTY = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/empty.png").createImage();
	public static final Image OPEN_ESB_BPEL_COMPENSATESCOPE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/compensatescope.png").createImage();
	public static final Image OPEN_ESB_BPEL_COMPENSATE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/compensate.png").createImage();
	public static final Image OPEN_ESB_BPEL_WHILE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/while.png").createImage();
	public static final Image OPEN_ESB_BPEL_FOREACH = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/foreach.png").createImage();
	public static final Image OPEN_ESB_BPEL_REPEAT_UNTIL = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/repeatuntil.png").createImage();
	public static final Image OPEN_ESB_BPEL_IF = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/if.png").createImage();
	public static final Image OPEN_ESB_BPEL_ASSIGN = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/assign.png").createImage();
	public static final Image OPEN_ESB_BPEL_EXIT = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/exit.png").createImage();
	public static final Image OPEN_ESB_BPEL_VARIABLE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/variable.png").createImage();
	public static final Image OPEN_ESB_BPEL_ONEVENT = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/on_event.png").createImage();
	public static final Image OPEN_ESB_BPEL_ONALARM = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/on_alarm.png").createImage();
	public static final Image OPEN_ESB_BPEL_CATCH = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/catch.png").createImage();
	public static final Image OPEN_ESB_BPEL_FAULT_HANDLERS = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/fault_hanlders.png").createImage();
	public static final Image OPEN_ESB_BPEL_CATCHALL = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/catch_all.png").createImage();
	public static final Image OPEN_ESB_BPEL_EVENT_HANLDERS = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/event_hanlders.png").createImage();
	public static final Image OPEN_ESB_BPEL_TERMINATION_HANDLER = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/termination_hanlders.png").createImage();
	public static final Image OPEN_ESB_BPEL_ONMESSAGE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/on_message_hanlder.png").createImage();
	public static final Image OPEN_ESB_BPEL_COMPENSATIO_HANLDER = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/compensation_hanlder.png").createImage();
	public static final Image OPEN_ESB_BPEL_ELSEIF = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/else_if.png").createImage();
	public static final Image OPEN_ESB_BPEL_ELSE = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/else.png").createImage();
	public static final Image OPEN_ESB_BPEL_PROCESS = Activator.getImageDescriptor("/icons/com/tomecode/soa/openEsb/16/process.png").createImage();

	public static final Image OSB_10G_STAGE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/stage.gif").createImage();
	public static final Image OSB_10G_BRANCH = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/branch.gif").createImage();
	public static final Image OSB_10G_PROXY_SERVICE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/proxyService.gif").createImage();
	public static final Image OSB_10G_BUSINESS_SERVICE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/businessService.gif").createImage();
	public static final Image OSB_10G_PIPELINE_PARI_NODE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/pipelinePair.gif").createImage();
	public static final Image OSB_10G_PIPELINE_REQUEST = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/pipelinePairRequest.gif").createImage();
	public static final Image OSB_10G_PIPELINE_RESPONSE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/pipelinePairResponse.gif").createImage();
	public static final Image OSB_10G_ROUTER = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/router.gif").createImage();
	public static final Image OSB_10G_ASSIGN = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/assign.gif").createImage();
	public static final Image OSB_10G_DELETE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/delete.gif").createImage();
	public static final Image OSB_10G_INSERT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/insert.gif").createImage();
	public static final Image OSB_10G_JAVA_CALLOUT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/javaCallout.gif").createImage();
	public static final Image OSB_10G_MFL = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/mfl.gif").createImage();
	public static final Image OSB_10G_RENAME = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/rename.gif").createImage();
	public static final Image OSB_10G_REPLACE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/replace.gif").createImage();
	public static final Image OSB_10G_VALIDATE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/validate.gif").createImage();
	public static final Image OSB_10G_SKIP = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/skip.gif").createImage();
	public static final Image OSB_10G_REPLY = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/reply.gif").createImage();
	public static final Image OSB_10G_ELSEIF = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/if.gif").createImage();
	public static Image OSB_10G_IFTHENELSE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/ifthen.gif").createImage();
	public static final Image OSB_10G_TRANSPORTHEADERS = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/transportHeaders.gif").createImage();
	public static final Image OSB_10G_WSCALLOUT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/WSCallout.gif").createImage();
	public static final Image OSB_10G_ROUTING_OPTIONS = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/routingOptions.gif").createImage();
	public static final Image OSB_10G_FOREACH = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/foreach.gif").createImage();
	public static final Image OSB_10G_DYNAMIC_ROUTING = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/dynamicPublish.gif").createImage();
	public static final Image OSB_10G_ROUTE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/route.gif").createImage();
	public static final Image OSB_10G_ROUTING_TABLE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/routingTable.gif").createImage();
	public static final Image OSB_10G_CASE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/case.gif").createImage();
	public static final Image OSB_10G_DEFAULT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/default.gif").createImage();
	public static final Image OSB_10G_ALERT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/alert.gif").createImage();;
	public static final Image OSB_10G_LOG = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/log.gif").createImage();;
	public static final Image OSB_10G_REPORT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/report.gif").createImage();
	public static final Image OSB_10G_RAISE_ERROR = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/raiseError.gif").createImage();
	public static final Image OSB_10G_BRANCH_NODE_CONDITION = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/branchCondition.gif").createImage();
	public static final Image OSB_10G_BRANCH_OPERATION = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/branchOperational.gif").createImage();
	public static final Image OSB_10G_PROJECT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/project.gif").createImage();

	public static final Image OSB_10G_SPLITJOIN_ASSIGN = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/assign.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_ASSIGN_ACTION = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/assignAction.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_COPY = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/copy.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_DELETE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/delete.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_ELSE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/else.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_IF = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/if.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_FLOW = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/flow.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_FOR_EACH = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/foreach.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_INSERT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/insert.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_INVOKE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/invoke.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_JAVA_CALLOUT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/javaCallout.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_LOG = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/log.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_RECEIVE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/receive.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_VARIABLES = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/variables.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_SCOPE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/scope.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_WHILE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/while.png").createImage();
	public static final Image OSB_10G_SPLITJOIN = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/splitJoin.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_REPLACE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/replace.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_VARIABLE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/variable.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_RAISE_ERROR = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/raiseError.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_EXTERNAL_SERVICE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/externalService.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_REPLY = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/reply.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_LEFT_ARROW = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/leftArrow.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_VARIABLE_PRIVATE = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/privateVariable.gif").createImage();

	public static final Image OSB_10G_SPLITJOIN_LOGIC_FLYOUT = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/logicFlyout.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_CATCH = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/catch.gif").createImage();
	public static final Image OSB_10G_SPLITJOIN_CATCHALL = Activator.getImageDescriptor("/icons/com/tomecode/soa/osb10g/16/splitjoin/catchAll.gif").createImage();

	public static final Image UNKNOWN = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/unknown.png").createImage();
	public static final Image UNKNOWN_BIG = Activator.getImageDescriptor("/icons/com/tomecode/soa/icons/unknown_big.png").createImage();

	private ImageFactory() {
	}

}

package com.tomecode.soa.dependency.analyzer.view.graph;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.Activity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.ActivityDiagram;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.SequentialActivity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.Transition;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.ActivityPartFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Flow Graph for visualize dependencies between services/processes
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class FlowGraphView extends GraphicalEditor implements IEditorInput {

	public static final String ID = "view.flowGraph";

	/**
	 * active diagram
	 */
	private final ActivityDiagram activityDiagram;

	public FlowGraphView() {
		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		setEditDomain(defaultEditDomain);
		setTitleToolTip("Flow Graph");
		setTitleImage(ImageFactory.FLOW_GRAPH_VIEW);
		this.activityDiagram = new ActivityDiagram();
	}

	/**
	 * Initialize graph
	 */
	@Override
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setContents(activityDiagram);

		// SequentialActivity wakeup = new SequentialActivity();
		// Activity backToSleep = new Activity("Go back to sleep");
		// Activity turnOff = new Activity("Turn off alarm");
		// wakeup.setName("Wake up");
		// wakeup.addChild(new Activity("Hit snooze button"));
		// wakeup.addChild(backToSleep);
		// wakeup.addChild(turnOff);
		// wakeup.addChild(new Activity("Get out of bed"));
		//
		// activityDiagram.addChild(wakeup);
		//
		// SequentialActivity bathroom = new SequentialActivity();
		// bathroom.addChild(new Activity("Brush teeth"));
		// bathroom.addChild(new Activity("Take shower"));
		// bathroom.addChild(new Activity("Comb hair"));
		// bathroom.setName("Bathroom activities");
		// activityDiagram.addChild(bathroom);
		//
		// ParallelActivity relaxation = new ParallelActivity();
		// relaxation.addChild(new Activity("Watch cartoons"));
		// relaxation.addChild(new Activity("Power Yoga"));
		// relaxation.setName("Morning relaxation ritual");
		// activityDiagram.addChild(relaxation);
		//
		// Activity sleep, alarm, alarm2, clothes, spare, no, yes, drive;
		// activityDiagram.addChild(sleep = new Activity("Sleep....."));
		// activityDiagram.addChild(alarm = new Activity("Alarm!!!"));
		// activityDiagram.addChild(alarm2 = new Activity("Alarm!!!"));
		// activityDiagram.addChild(clothes = new Activity("Put on clothes"));
		// activityDiagram.addChild(spare = new
		// Activity("Is there time to spare?"));
		// activityDiagram.addChild(yes = new Activity("YES"));
		// activityDiagram.addChild(no = new Activity("NO"));
		// activityDiagram.addChild(drive = new Activity("Drive to work"));
		//
		// new Transition(sleep, alarm);
		// new Transition(alarm, wakeup);
		// new Transition(backToSleep, alarm2);
		// new Transition(alarm2, turnOff);
		// new Transition(wakeup, bathroom);
		// new Transition(bathroom, clothes);
		// new Transition(clothes, spare);
		// new Transition(spare, yes);
		// new Transition(spare, no);
		// new Transition(yes, relaxation);
		// new Transition(no, drive);
		// new Transition(relaxation, drive);
		// StructuredActivity root = new StructuredActivity();
		// ---------------------------
		// SequentialActivity root = new SequentialActivity();
		// root.setName("");
		//
		// SequentialActivity proxy1 = new SequentialActivity();
		// proxy1.setName("Proxy1");
		// ParallelActivity p1 = new ParallelActivity();
		// Activity p1Req1 = new Activity("Request");
		// Activity p1Res1 = new Activity("Response");
		// p1.addChild(p1Res1);
		// p1.addChild(p1Req1);
		// proxy1.addChild(p1);
		// root.addChild(proxy1);
		// activityDiagram.addChild(root);
		//
		// SequentialActivity root2 = new SequentialActivity();
		// SequentialActivity proxy2 = new SequentialActivity();
		// proxy2.setName("Proxy2");
		// ParallelActivity p2 = new ParallelActivity();
		// Activity p2Req1 = new Activity("Request");
		// Activity p2Res1 = new Activity("Response");
		// p2.addChild(p2Res1);
		// p2.addChild(p2Req1);
		//
		// proxy2.addChild(p2);
		// root2.addChild(proxy2);
		// activityDiagram.addChild(root2);
		//
		// Activity adapter = new Activity();
		// adapter.setName("Adapter1");
		// SequentialActivity root3 = new SequentialActivity();
		// root3.addChild(adapter);
		// activityDiagram.addChild(root3);
		//
		// new Transition(p2Res1, p1Res1);
		// new Transition(p1Req1, p2Req1);
		//
		// new Transition(p2Req1, adapter);
		// new Transition(adapter, p2Res1);
		//
		// SequentialActivity sq = new SequentialActivity();
		// sq.setName("s1");
		// activityDiagram.addChild(sq);
		//
		// Activity a1 = new Activity();
		// sq.addChild(a1);
		// Activity a2 = new Activity();
		// sq.addChild(a2);
		//
		// new Transition(a1, a2);
		// new Transition(a2, a1);
		// getGraphicalViewer().setContents(activityDiagram);
		// TODO Auto-generated method stub
		// super.initializeActionRegistry();
	}

	// SequentialActivity root = new SequentialActivity();
	// activityDiagram.addChild(root);
	// root.setName("");
	//
	// SequentialActivity proxy1 = new SequentialActivity();
	// proxy1.setName("Proxy1");
	// ParallelActivity p1 = new ParallelActivity();
	// Activity p1Req1 = new Activity("Request");
	// Activity p1Res1 = new Activity("Response");
	// p1.addChild(p1Req1);
	// p1.addChild(p1Res1);
	// proxy1.addChild(p1);
	// // activityDiagram.addChild(proxy1);
	//
	// SequentialActivity proxy2 = new SequentialActivity();
	// proxy2.setName("Proxy2");
	// ParallelActivity p2 = new ParallelActivity();
	// Activity p2Req1 = new Activity("Request");
	// Activity p2Res1 = new Activity("Response");
	// p2.addChild(p2Req1);
	// p2.addChild(p2Res1);
	// proxy2.addChild(p2);
	// // activityDiagram.addChild(proxy2);
	//
	// root.addChild(proxy1);
	// root.addChild(proxy2);
	//
	// Activity adapter = new Activity();
	// adapter.setName("Adapter1");
	//
	// root.addChild(adapter);
	//
	// new Transition(p1Req1, p2Req1);
	// new Transition(p2Res1, p1Res1);
	// // new Transition(p2Req1, p2Res1);
	//
	// new Transition(p2Req1, adapter);
	// new Transition(adapter, p2Res1);
	//
	// StructuredActivity s1 = new StructuredActivity();
	// s1.setName("aaa");
	// activityDiagram.addChild(s1);
	//
	// getGraphicalViewer().setContents(activityDiagram);

	protected final void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		getGraphicalViewer().setRootEditPart(new ScalableRootEditPart());
		getGraphicalViewer().setEditPartFactory(new ActivityPartFactory());
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "flow control";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "FLow Control";
	}

	public final void showGraph(Object source) {
		clearGraph();
		if (source instanceof Proxy) {
			showOsbProxy((Proxy) source);
		}
	}

	private final void showOsbProxy(Proxy proxy) {

		// ParallelActivity sourceSequensce = new ParallelActivity(null);
		// SequentialActivity sourceSequensce = new SequentialActivity(null);
		// sourceSequensce.setName(proxy.getName());

		// sourceSequensce.addChild(sourceRequest);
		// sourceSequensce.addChild(sourceResponse);

		// activityDiagram.addChild(sourceSequensce);

		Activity activityProxy = new Activity(proxy);
		activityDiagram.addChild(activityProxy);

		SequentialActivity flowRequest = new SequentialActivity("Flow");
		flowRequest.setName("Flow Request");

		// Activity sourceRequest = new Activity(null);
		// sourceRequest.setName("Request");
		// flowRequest.addChild(sourceRequest);

		new Transition(activityProxy, flowRequest);
		activityDiagram.addChild(flowRequest);

		ServiceDependencies serviceDependencies = proxy.getServiceDependencies();
		for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
			if (serviceDependency.getServices().isEmpty()) {
				// TODO: unknown
			} else {
				Service target = serviceDependency.getServices().get(0);
				SequentialActivity sequentialTarget = new SequentialActivity(null);
				sequentialTarget.setName(target.getName());

				OsbActivity osbActivity = serviceDependency.getActivity();
				Activity activityOSb = new Activity(osbActivity);
				activityOSb.setName(osbActivity.getName());
				flowRequest.addChild(activityOSb);
				// new Transition(sourceRequest, activityOSb);

				Activity request = new Activity(null);
				request.setName("Request");
				Activity response = new Activity(null);
				response.setName("Response");

				sequentialTarget.addChild(request);
				sequentialTarget.addChild(response);

				activityDiagram.addChild(sequentialTarget);
				new Transition(activityOSb, request);
				new Transition(response, activityOSb);
			}
		}

	}

	/**
	 * remove {@link MultiWorkspace} from {@link FlowGraphView}
	 * 
	 * @param multiWorkspace
	 */
	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {

	}

	/**
	 * remove {@link Workspace} from {@link FlowGraphView}
	 * 
	 * @param removeWorkspace
	 */
	public final void removeWorkspace(Workspace removeWorkspace) {

	}

	private final void clearGraph() {
		activityDiagram.clear();
		getGraphicalViewer().setContents(activityDiagram);
	}
}

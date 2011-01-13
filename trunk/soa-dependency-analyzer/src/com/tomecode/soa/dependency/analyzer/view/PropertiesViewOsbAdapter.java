package com.tomecode.soa.dependency.analyzer.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.BusinessService;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointEJB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.EndpointUNKNOWN;

/**
 * detail informations about selected adapter in OSB 10
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class PropertiesViewOsbAdapter extends ViewPart implements HideView {

	public static final String ID = "view.properties.osb.adapter";
	/**
	 * root content panel
	 */
	private Composite contentPanel;
	private StackLayout layout;
	/**
	 * empty page
	 */
	private Composite emptyPage;

	// ******************* BUSINESS SERVICE PAGE
	private Composite businessServicePage;
	private Text textBSName;
	private Text textBSTrasportProvider;
	private Group groupBSUri;

	// ******************* BUSINESS PAGE JMS
	private Composite businessServicePageJMS;
	private Text textBSNameJMS;
	private Text textBSTrasportProviderJMS;
	private Group groupBSUriJMS;
	private Text textBSResponseJMS;
	private Text textBSIsQueueJMS;

	// ******************* BUSINESS PAGE EJB
	private Composite businessServicePageEJB;
	private Text textBSNameEJB;
	private Text textBSclientJar;
	private Text textBSejbHome;
	private Text textBSejbObject;

	// ******************* PROXY PAGE
	private Composite proxyPage;
	private Text textProxyName;
	private Text textProxyTrasportProvider;
	private Group groupProxyUri;

	private Composite splitJoinPage;
	private Text textSJName;

	public PropertiesViewOsbAdapter() {
		setTitleImage(ImageFactory.PROPERTIES);
	}

	public void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	@Override
	public final void createPartControl(Composite parent) {
		contentPanel = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		contentPanel.setLayout(layout);

		emptyPage = new Composite(contentPanel, SWT.NONE);
		emptyPage.pack();
		initBusinesServicePage();
		initBusinesServicePageJMS();
		initBusinesServicePageEJB();
		initProxyPage();
		initSplitJoinPage();
		layout.topControl = businessServicePageJMS;// emptyPage;
		contentPanel.layout();
	}

	@Override
	public final void setFocus() {

	}

	/**
	 * create page for {@link BusinessService}
	 */
	private final void initBusinesServicePage() {
		businessServicePage = new Composite(contentPanel, SWT.NONE);
		businessServicePage.setLayout(new FillLayout());
		ScrolledComposite sc = new ScrolledComposite(businessServicePage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupBasic = GuiUtils.createGroupWithGrid(composite, "Business Service");
		GuiUtils.createLabelWithGrid(groupBasic, "Name ");
		textBSName = GuiUtils.createTextReadOnlyWithGrid(groupBasic);

		Group groupEndpoint = GuiUtils.createGroupWithGrid(composite, "Endpoint Config", 1);
		Composite csEndpoint = GuiUtils.createCompositeWithGrid(groupEndpoint, 2);
		GuiUtils.createLabelWithGrid(csEndpoint, "Trasport Provider ");
		textBSTrasportProvider = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		Composite compositeURI = GuiUtils.createCompositeWithGrid(groupEndpoint, 1);
		groupBSUri = GuiUtils.createGroupWithGrid(compositeURI, "URIs", 1);
		finishPage(composite, sc);
	}

	/**
	 * create page for {@link BusinessService} with JMS protocol
	 */
	private final void initBusinesServicePageJMS() {
		businessServicePageJMS = new Composite(contentPanel, SWT.NONE);
		businessServicePageJMS.setLayout(new FillLayout());
		ScrolledComposite sc = new ScrolledComposite(businessServicePageJMS, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupBasic = GuiUtils.createGroupWithGrid(composite, "Business Service", 1);
		Composite csBasic = GuiUtils.createCompositeWithGrid(groupBasic, 2);
		GuiUtils.createLabelWithGrid(csBasic, "Name ");
		textBSNameJMS = GuiUtils.createTextReadOnlyWithGrid(csBasic);

		Group groupEndpoint = GuiUtils.createGroupWithGrid(composite, "Endpoint Config", 1);
		Composite csEndpoint = GuiUtils.createCompositeWithGrid(groupEndpoint, 2);
		GuiUtils.createLabelWithGrid(csEndpoint, "Trasport Provider ");
		textBSTrasportProviderJMS = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		GuiUtils.createLabelWithGrid(csEndpoint, "Is Queue");
		textBSIsQueueJMS = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		Composite compositeURI = GuiUtils.createCompositeWithGrid(groupEndpoint, 1);
		groupBSUriJMS = GuiUtils.createGroupWithGrid(compositeURI, "URIs", 1);

		Composite compositeURIResponse = GuiUtils.createCompositeWithGrid(groupEndpoint, 1);
		Group groupBSResponseJMS = GuiUtils.createGroupWithGrid(compositeURIResponse, "Response URI", 1);
		textBSResponseJMS = GuiUtils.createTextReadOnlyWithGrid(groupBSResponseJMS);
		finishPage(composite, sc);
	}

	/**
	 * create page for {@link BusinessService} with EJB protocol
	 */
	private final void initBusinesServicePageEJB() {
		businessServicePageEJB = new Composite(contentPanel, SWT.NONE);
		businessServicePageEJB.setLayout(new FillLayout());
		ScrolledComposite sc = new ScrolledComposite(businessServicePageEJB, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupBasic = GuiUtils.createGroupWithGrid(composite, "Business Service", 1);
		Composite csBasic = GuiUtils.createCompositeWithGrid(groupBasic, 2);
		GuiUtils.createLabelWithGrid(csBasic, "Name ");
		textBSNameEJB = GuiUtils.createTextReadOnlyWithGrid(csBasic);

		Group groupEndpoint = GuiUtils.createGroupWithGrid(composite, "EJB", 1);
		Composite csEndpoint = GuiUtils.createCompositeWithGrid(groupEndpoint, 2);
		GuiUtils.createLabelWithGrid(csEndpoint, "Client JAR ");
		textBSclientJar = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		GuiUtils.createLabelWithGrid(csEndpoint, "EJB Home ");
		textBSejbHome = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		GuiUtils.createLabelWithGrid(csEndpoint, "EJB Object ");
		textBSejbObject = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		finishPage(composite, sc);
	}

	/**
	 * create page for {@link Proxy}
	 */
	private final void initProxyPage() {
		proxyPage = new Composite(contentPanel, SWT.NONE);
		proxyPage.setLayout(new FillLayout());
		ScrolledComposite sc = new ScrolledComposite(proxyPage, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupBasic = GuiUtils.createGroupWithGrid(composite, "Proxy Service");
		GuiUtils.createLabelWithGrid(groupBasic, "Name ");
		textProxyName = GuiUtils.createTextReadOnlyWithGrid(groupBasic);
		Group groupEndpoint = GuiUtils.createGroupWithGrid(composite, "Endpoint Config", 1);
		Composite csEndpoint = GuiUtils.createCompositeWithGrid(groupEndpoint, 2);
		GuiUtils.createLabelWithGrid(csEndpoint, "Trasport Provider ");
		textProxyTrasportProvider = GuiUtils.createTextReadOnlyWithGrid(csEndpoint);
		Composite compositeURI = GuiUtils.createCompositeWithGrid(groupEndpoint, 1);
		groupProxyUri = GuiUtils.createGroupWithGrid(compositeURI, "URIs", 1);
		finishPage(composite, sc);

	}

	/**
	 * create page for {@link SplitJoin}
	 */
	private final void initSplitJoinPage() {
		splitJoinPage = new Composite(contentPanel, SWT.NONE);
		splitJoinPage.setLayout(new FillLayout());
		ScrolledComposite sc = new ScrolledComposite(splitJoinPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupBasic = GuiUtils.createGroupWithGrid(composite, "SPlit Join");
		GuiUtils.createLabelWithGrid(groupBasic, "Name ");
		textSJName = GuiUtils.createTextReadOnlyWithGrid(groupBasic);

		finishPage(composite, sc);
	}

	private void finishPage(Composite composite, ScrolledComposite sc) {
		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);
	}

	/**
	 * show data about selected {@link Service}
	 * 
	 * @param data
	 */
	public final void show(Object data) {
		if (data == null) {
			showContent(emptyPage);
		} else if (data instanceof BusinessService) {
			BusinessService businessService = (BusinessService) data;
			fillPageForBusinessService(businessService);
		} else if (data instanceof Proxy) {
			Proxy proxy = (Proxy) data;
			textProxyName.setText(proxy.getName());
			EndpointConfig endpointConfig = proxy.getEndpointConfig();

			if (endpointConfig.getProtocol() == ProviderProtocol.UNKNOWN) {
				textProxyTrasportProvider.setText(((EndpointUNKNOWN) endpointConfig).getProviderId());
			} else {
				textProxyTrasportProvider.setText(endpointConfig.getProtocol().toString());
			}

			fillUriList(groupProxyUri, endpointConfig.getUris());
			proxyPage.pack(true);
			showContent(proxyPage);
		} else if (data instanceof SplitJoin) {
			SplitJoin splitJoin = (SplitJoin) data;
			textSJName.setText(splitJoin.getName());
			showContent(splitJoinPage);
		} else {
			showContent(emptyPage);
		}
	}

	private final void fillPageForBusinessService(BusinessService businessService) {

		if (businessService.getEndpointConfig().getProtocol() == ProviderProtocol.JMS) {
			textBSNameJMS.setText(businessService.getName());
			EndpointJms jms = (EndpointJms) businessService.getEndpointConfig();
			textBSTrasportProviderJMS.setText(jms.getProtocol().toString());
			textBSIsQueueJMS.setText(String.valueOf(jms.getProviderSpecificJms().isQueue()));
			fillUriList(groupBSUriJMS, jms.getUris());
			String response = jms.getProviderSpecificJms().getResponseURI() == null ? "" : jms.getProviderSpecificJms().getResponseURI();
			textBSResponseJMS.setText(response);
			businessServicePageJMS.pack(true);

			showContent(businessServicePageJMS);
		} else if (businessService.getEndpointConfig().getProtocol() == ProviderProtocol.EJB) {
			textBSNameEJB.setText(businessService.getName());
			EndpointEJB ejb = (EndpointEJB) businessService.getEndpointConfig();
			textBSclientJar.setText(ejb.getProviderSpecificEJB().getClientJar());
			textBSejbHome.setText(ejb.getProviderSpecificEJB().getEjbHome());
			textBSejbObject.setText(ejb.getProviderSpecificEJB().getEjbObject());

			showContent(businessServicePageEJB);
		} else if (businessService.getEndpointConfig().getProtocol() == ProviderProtocol.UNKNOWN) {
			textBSName.setText(businessService.getName());
			EndpointConfig endpointConfig = businessService.getEndpointConfig();
			textBSTrasportProvider.setText(((EndpointUNKNOWN) endpointConfig).getProviderId());
			fillUriList(groupBSUri, endpointConfig.getUris());
			businessServicePage.pack(true);
			showContent(businessServicePage);
		} else {
			textBSName.setText(businessService.getName());
			EndpointConfig endpointConfig = businessService.getEndpointConfig();
			textBSTrasportProvider.setText(endpointConfig.getProtocol().toString());
			fillUriList(groupBSUri, endpointConfig.getUris());
			businessServicePage.pack(true);
			showContent(businessServicePage);
		}
	}

	private final void fillUriList(Group group, List<String> uris) {
		for (Control control : group.getChildren()) {
			control.dispose();
		}

		for (String uri : uris) {
			GuiUtils.createTextWithGrid(group, uri);
		}
	}

	private final void showContent(Composite composite) {
		layout.topControl = composite;
		contentPanel.layout();
	}

}

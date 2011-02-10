package com.tomecode.soa.dependency.analyzer.view;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.AddDataToExistsGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Info about selected items in {@link VisualGraphView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class PropertiesView extends ViewPart implements HideView {

	public static final String ID = "view.properties";

	/**
	 * data from which will be shown properties
	 */
	private Object dataForProperties;

	private Composite contentPanel;
	private StackLayout layout;

	private Composite emptyPage;

	private Composite dynamicPage;

	/**
	 * Constructor
	 */
	public PropertiesView() {
		setTitleImage(ImageFactory.PROPERTIES);
	}

	@Override
	public final void createPartControl(Composite parent) {
		contentPanel = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		contentPanel.setLayout(layout);

		emptyPage = new Composite(contentPanel, SWT.NONE);
		emptyPage.pack();
		dynamicPage = new Composite(contentPanel, SWT.NONE);
		dynamicPage.pack();
		layout.topControl = emptyPage;
		contentPanel.layout();
	}

	private void finishPage(Composite composite, ScrolledComposite sc) {
		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);
	}

	@Override
	public final void setFocus() {

	}

	public void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	/**
	 * hide view
	 */
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	/**
	 * show properties from object
	 * 
	 * @param data
	 */
	public final void show(Object data) {
		dataForProperties = data;
		if (dataForProperties == null) {
			showContent(emptyPage);
		} else if (dataForProperties.getClass().getAnnotation(PropertyGroupView.class) != null) {
			createDynamicPage(dataForProperties);
		} else {
			showContent(emptyPage);
		}
	}

	private final void createDynamicPage(Object data) {
		dynamicPage.dispose();
		dynamicPage = new Composite(contentPanel, SWT.NONE);
		dynamicPage.setLayout(new FillLayout());
		dynamicPage.pack();

		ScrolledComposite sc = new ScrolledComposite(dynamicPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);
		createDynamicParent(data, composite);

		finishPage(composite, sc);
		showContent(dynamicPage);
	}

	/**
	 * create dynamic content
	 * 
	 * @param data
	 * @param parentComposite
	 */
	private final void createDynamicParent(Object data, Composite parentComposite) {
		PropertyGroupView propertyGroupView = data.getClass().getAnnotation(PropertyGroupView.class);
		if (propertyGroupView != null) {
			Group group = GuiUtils.createGroupWithGrid(parentComposite, propertyGroupView.type());
			if (propertyGroupView.name().trim().length() != 0) {
				createTextWithLable(group, "Type", propertyGroupView.name());
			}

			createDymaicContent(data, group);

			List<Object> insideObjects = findInsiseObject(data);
			for (Object inside : insideObjects) {
				addContentForInside(inside, group);
			}

			Object parentObject = getMethodValue(data, data.getClass().getDeclaredMethods(), propertyGroupView.parentMethod());
			if (parentObject != null) {
				if (parentObject instanceof ArrayList<?>) {
					List<?> objs = (List<?>) parentObject;
					for (Object o : objs) {
						createDynamicParent(o, parentComposite);
					}
				} else {
					createDynamicParent(parentObject, parentComposite);
				}
			} else {
				if (!data.getClass().getSuperclass().toString().equals("class java.lang.Object")) {
					parentObject = getMethodValue(data, data.getClass().getSuperclass().getDeclaredMethods(), propertyGroupView.parentMethod());
					if (parentObject != null) {
						createDynamicParent(parentObject, parentComposite);
					}
				}
			}
		}
	}

	private final void addContentForInside(Object inside, Group group) {
		PropertyViewData propertyViewData = inside.getClass().getAnnotation(PropertyViewData.class);
		if (propertyViewData != null) {
			if (propertyViewData.value() != "") {
				createTextWithLable(group, propertyViewData.title(), propertyViewData.value());
			}
		}
		createDymaicContent(inside, group);
	}

	private final void createDymaicContent(Object data, Group group) {
		createDymaicContentFromField(data, data.getClass().getDeclaredFields(), group);
		if (!data.getClass().getSuperclass().toString().equals("class java.lang.Object")) {
			createDymaicContentFromField(data, data.getClass().getSuperclass().getDeclaredFields(), group);
		}
		createDynamicContentFromMethod(data, data.getClass().getDeclaredMethods(), group);
		if (!data.getClass().getSuperclass().toString().equals("class java.lang.Object")) {
			createDynamicContentFromMethod(data, data.getClass().getSuperclass().getDeclaredMethods(), group);
		}
	}

	/**
	 * create text and label from {@link Field}
	 * 
	 * @param data
	 * @param fields
	 * @param group
	 */
	private final void createDymaicContentFromField(Object data, Field[] fields, Group group) {
		for (Field field : fields) {
			PropertyViewData propertyViewData = field.getAnnotation(PropertyViewData.class);
			if (propertyViewData != null) {

				if (!propertyViewData.value().equals("")) {
					createTextWithLable(group, propertyViewData.title(), propertyViewData.value());
				} else {
					Object value = getObjectValue(data, field);

					if (value instanceof List<?>) {
						List<?> list = (List<?>) value;
						for (Object v : list) {
							createTextWithLable(group, propertyViewData.title(), v.toString());
						}
					} else {
						createTextWithLable(group, propertyViewData.title(), (value == null ? "" : value.toString()));
					}
				}
			}
		}
	}

	/**
	 * create text and lable from {@link Method}
	 * 
	 * @param data
	 * @param methods
	 * @param group
	 */
	private final void createDynamicContentFromMethod(Object data, Method[] methods, Group group) {
		for (Method method : methods) {
			PropertyViewData propertyViewData = method.getAnnotation(PropertyViewData.class);
			if (propertyViewData != null) {
				Object value = getMethodValue(data, method);
				createTextWithLable(group, propertyViewData.title(), (value == null ? "" : value.toString()));
			}
		}
	}

	private final Object getMethodValue(Object owner, Method method) {
		try {
			method.setAccessible(true);
			return method.invoke(owner, new Object[] {});
		} catch (Exception e) {
		} finally {
			method.setAccessible(false);
		}
		return null;
	}

	private final void createTextWithLable(Composite parent, String title, String value) {
		GuiUtils.createLabelWithGrid(parent, title);
		Text text = GuiUtils.createTextReadOnlyWithGrid(parent);
		text.setText(value);
	}

	private final Object getMethodValue(Object data, Method[] methods, String methodName) {
		for (Method method : methods) {
			try {
				if (method.getName().equals(methodName)) {
					return invokeMethod(data, method);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private final List<Object> findInsiseObject(Object parentObject) {
		List<Object> list = new ArrayList<Object>();
		Field[] fields = parentObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(AddDataToExistsGroupView.class) != null) {
				Object o = getObjectValue(parentObject, field);
				if (o != null) {
					list.add(o);
				}
			}
		}
		fields = parentObject.getClass().getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(AddDataToExistsGroupView.class) != null) {
				Object o = getObjectValue(parentObject, field);
				if (o != null) {
					list.add(o);
				}
			}
		}

		return list;
	}

	private final Object invokeMethod(Object owner, Method method) {
		try {
			method.setAccessible(true);
			return method.invoke(owner, new Object[] {});
		} catch (Exception e) {
		} finally {
			method.setAccessible(false);
		}
		return null;
	}

	private final Object getObjectValue(Object parentObject, Field field) {
		try {
			field.setAccessible(true);
			Object value = field.get(parentObject);
			if (value != null) {
				return value;
			}
		} catch (Exception e) {

		} finally {
			field.setAccessible(false);
		}

		return "";
	}

	private final void showContent(Composite composite) {
		layout.topControl = composite;
		contentPanel.layout();
	}

	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		Object multiWorkspaceInTree = findMultiWorkspaceInTree();
		if (multiWorkspace.equals(multiWorkspaceInTree)) {
			showContent(emptyPage);
		}
	}

	private final Object findMultiWorkspaceInTree() {
		if (dataForProperties == null) {
			return null;
		} else if (dataForProperties instanceof MultiWorkspace) {
			return dataForProperties;
		} else if (dataForProperties instanceof Workspace) {
			return ((Workspace) dataForProperties).getMultiWorkspace();
		} else if (dataForProperties instanceof Project) {
			return ((Project) dataForProperties).getWorkpsace().getMultiWorkspace();
		}
		return null;
	}

	private final Workspace findWorkspaceInTree() {
		if (dataForProperties == null) {
			return null;
		} else if (dataForProperties instanceof Workspace) {
			return ((Workspace) dataForProperties);
		} else if (dataForProperties instanceof Project) {
			return ((Project) dataForProperties).getWorkpsace();
		}

		return null;
	}

	public final void removeWorkspace(Workspace workspace) {
		Workspace workspaceInTree = findWorkspaceInTree();
		if (workspace.equals(workspaceInTree)) {
			showContent(emptyPage);
		}
	}
}

package com.tomecode.soa.dependency.analyzer.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.tomecode.soa.dependency.analyzer.gui.wizards.AddNewProjectToWorkspaceWizard.AddNewProjectToWorkspaceConfig;
import com.tomecode.soa.dependency.analyzer.gui.wizards.OpenNewWorkspaceWizard.WorkspaceConfig;
import com.tomecode.soa.openesb.bpel.parser.OpenEsbMWorkspaceParser;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gMWorkspaceParser;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.ora.suite10g.parser.Ora10gMWorkspaceParser;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * 
 * 
 * Singleton object - contains all parsed {@link MultiWorkspace}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class ApplicationManager {

	private OraSB10gMWorkspaceParser oraSB10gMWorkspaceParser;

	private static ApplicationManager me;

	/**
	 * list of all parsed {@link MultiWorkspace}
	 */
	private List<MultiWorkspace> multiWorkspaces;

	private ApplicationManager() {
		multiWorkspaces = new ArrayList<MultiWorkspace>();
		oraSB10gMWorkspaceParser = new OraSB10gMWorkspaceParser();
	}

	public final List<MultiWorkspace> getMultiWorkspaces() {
		return multiWorkspaces;
	}

	/**
	 * get singleton object
	 * 
	 * @return
	 */
	public final static ApplicationManager getInstance() {
		if (me == null) {
			me = new ApplicationManager();
			me.init();
		}
		return me;
	}

	/**
	 * workspace file
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	private final File getWorkspaceFile() throws MalformedURLException {
		String path = System.getProperty("osgi.configuration.area");
		URL url = new URL(path + File.separator + "workspaces.xml");
		return new File(url.getFile());
	}

	private final void init() {
		try {
			File file = getWorkspaceFile();
			if (file.exists()) {
				Document document = new SAXReader().read(file);
				parseWorkspaces(document.getRootElement());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * write all loaded workspaces to workspace.xml
	 */
	private final void writeAppData() {
		XMLWriter output = null;
		try {
			File file = getWorkspaceFile();
			if (!file.exists()) {
				file.createNewFile();
			}

			Document document = DocumentHelper.createDocument();
			document.addElement("workspaces");
			Element rootElement = document.getRootElement();

			writeData(rootElement);

			output = new XMLWriter(new FileWriter(file), OutputFormat.createPrettyPrint());
			output.write(document);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * write data to XML
	 * 
	 * @param rootElement
	 */
	private final void writeData(Element rootElement) {
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			Element eRoot = DocumentHelper.createElement("multi-workspace");
			rootElement.add(eRoot);

			eRoot.addAttribute("name", multiWorkspace.getName());
			if (multiWorkspace.getType() == WorkspaceType.ORACLE_1OG) {
				eRoot.addAttribute("type", "oracle-10g");
			} else if (multiWorkspace.getType() == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
				eRoot.addAttribute("type", "oracle-10g-osb");
			} else if (multiWorkspace.getType() == WorkspaceType.OPEN_ESB) {
				eRoot.addAttribute("type", "open-esb");
			}

			// multi-workspace path
			Element ePath = DocumentHelper.createElement("path");
			eRoot.add(ePath);
			ePath.addText(multiWorkspace.getFile().toString());

			writeWorkspace(eRoot, multiWorkspace.getWorkspaces());
		}
	}

	/**
	 * write list of {@link Workspace}
	 * 
	 * @param eRoot
	 * @param workspaces
	 */
	private final void writeWorkspace(Element eRoot, List<Workspace> workspaces) {
		Element eWorkspaces = DocumentHelper.createElement("workspaces");
		eRoot.add(eWorkspaces);

		for (Workspace workspace : workspaces) {
			Element eWorkspace = DocumentHelper.createElement("workspace");
			eWorkspaces.add(eWorkspace);

			Element eWorkspaceName = DocumentHelper.createElement("name");
			eWorkspaceName.setText(workspace.getName());
			eWorkspace.add(eWorkspaceName);

			Element eWorkspacePath = DocumentHelper.createElement("path");
			eWorkspacePath.setText(workspace.getFile().toString());
			eWorkspace.add(eWorkspacePath);

			writeProjects(eWorkspace, workspace.getProjects());
		}
	}

	/**
	 * write list of {@link Project}
	 * 
	 * @param eRoot
	 * @param projects
	 */
	private final void writeProjects(Element eRoot, List<Project> projects) {
		Element eProjects = DocumentHelper.createElement("projects");
		eRoot.add(eProjects);

		for (Project project : projects) {
			Element eProject = DocumentHelper.createElement("project");
			eProjects.add(eProject);
			if (project.getType() == ProjectType.ORACLE_SERVICE_BUS_1OG) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
				eProject.addAttribute("isJar", String.valueOf(oraSB10gProject.isAsJar()));
			}

			Element ePath = DocumentHelper.createElement("path");
			eProject.add(ePath);
			ePath.setText(project.getFile().toString());

		}
	}

	/**
	 * parse mutl-workspace element
	 * 
	 * @param element
	 * @throws ServiceParserException
	 */
	private final void parseWorkspaces(Element element) throws ServiceParserException {
		List<?> elements = element.elements("multi-workspace");
		if (elements != null) {
			for (Object o : elements) {
				Element eMultiWorkspace = (Element) o;
				String name = eMultiWorkspace.attributeValue("name");
				String type = eMultiWorkspace.attributeValue("type");
				String path = eMultiWorkspace.elementTextTrim("path");

				if ("oracle-10g".equalsIgnoreCase(type)) {
					Ora10gMWorkspaceParser ora10gParser = new Ora10gMWorkspaceParser();
					MultiWorkspace multiWorkspace = ora10gParser.parse(name, new File(path));
					multiWorkspaces.add(multiWorkspace);
				} else if ("oracle-10g-osb".equalsIgnoreCase(type)) {
					MultiWorkspace multiWorkspace = restoreOSB10g(eMultiWorkspace.element("workspaces"), name, path);

					OraSB10gMWorkspaceParser parser = new OraSB10gMWorkspaceParser();
					parser.parse(multiWorkspace);
					multiWorkspaces.add(multiWorkspace);
				} else if ("open-esb".equals(type)) {
					OpenEsbMWorkspaceParser openEsbParser = new OpenEsbMWorkspaceParser();
					MultiWorkspace multiWorkspace = openEsbParser.parse(name, new File(path));
					multiWorkspaces.add(multiWorkspace);
				}
			}
		}
	}

	/**
	 * {@link OraSB10gMultiWorkspace}
	 * 
	 * @param eWorkspaces
	 * @param name
	 * @param path
	 * @return
	 */
	private final OraSB10gMultiWorkspace restoreOSB10g(Element eWorkspaces, String name, String path) {
		OraSB10gMultiWorkspace multiWorkspace = new OraSB10gMultiWorkspace(name, new File(path));
		if (eWorkspaces != null) {
			List<?> eWorkspace = eWorkspaces.elements("workspace");
			for (Object o : eWorkspace) {
				Element element = (Element) o;

				OraSB10gWorkspace workspace = new OraSB10gWorkspace(element.elementTextTrim("name"), new File(element.elementTextTrim("path")));
				multiWorkspace.addWorkspace(workspace);

				Element eProjects = element.element("projects");
				if (eProjects != null) {
					List<?> list = eProjects.elements("project");
					for (Object op : list) {
						Element ee = (Element) op;
						boolean isJar = Boolean.parseBoolean(ee.attributeValue("isJar"));
						OraSB10gProject project = new OraSB10gProject(new File(ee.elementTextTrim("path")), isJar);
						workspace.addProject(project);
					}
				}
			}
		}

		return multiWorkspace;
	}

	/**
	 * get {@link MultiWorkspace} by type
	 * 
	 * @param type
	 * @return
	 */
	public final List<MultiWorkspace> getMultiWorkspaces(WorkspaceType type) {
		List<MultiWorkspace> newMultiWorkspaces = new ArrayList<MultiWorkspace>();
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == type) {
				newMultiWorkspaces.add(multiWorkspace);
			}
		}

		return newMultiWorkspaces;

	}

	/**
	 * create new {@link Ora10gMWorkspaceParser}
	 * 
	 * @return
	 */
	public final Ora10gMWorkspaceParser newOra10gMultiWorkspace() {
		return new Ora10gMWorkspaceParser();
	}

	/**
	 * 
	 * 
	 * parse {@link OpenEsbMultiWorkspace}
	 * 
	 * @param config
	 * @return
	 * @throws ServiceParserException
	 */
	public final OpenEsbMultiWorkspace parseEsbMultiWorkspace(WorkspaceConfig config) throws ServiceParserException {
		if (config.isNewMultiWorkspace()) {
			OpenEsbMWorkspaceParser parser = new OpenEsbMWorkspaceParser();
			OpenEsbMultiWorkspace multiWorkspace = parser.parse(config.getMultiWorkspaceName(), config.getWorkspaceDir());
			this.multiWorkspaces.add(multiWorkspace);

			writeAppData();
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects

					writeAppData();
					return (OpenEsbMultiWorkspace) multiWorkspace;
				}
			}
		}

		throw new ServiceParserException("not found multi workspace with name: " + config.getMultiWorkspaceName(), true);
	}

	/**
	 * 
	 * parse {@link Ora10gMultiWorkspace}
	 * 
	 * 
	 * @param config
	 * @return
	 * @throws ServiceParserException
	 */
	public final Ora10gMultiWorkspace parseOra10gMultiWorkspace(WorkspaceConfig config) throws ServiceParserException {
		Ora10gMWorkspaceParser parser = new Ora10gMWorkspaceParser();
		if (config.isNewMultiWorkspace()) {
			Ora10gMultiWorkspace ora10gMultiWorkspace = parser.parse(config.getMultiWorkspaceName(), config.getWorkspaceDir());
			this.multiWorkspaces.add(ora10gMultiWorkspace);

			writeAppData();
			return ora10gMultiWorkspace;
		}
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspace;
					Ora10gWorkspace newWorkspace = parser.parseWorkspace(config.getWorkspaceDir());
					ora10gMultiWorkspace.addWorkspace(newWorkspace);
					parser.analyseDependnecies(ora10gMultiWorkspace);
					writeAppData();
					return ora10gMultiWorkspace;
				}
			}
		}
		throw new ServiceParserException("not found multi workspace with name: " + config.getMultiWorkspaceName(), true);
	}

	public final MultiWorkspace parseOraSB10gMultiWorkspace(WorkspaceConfig config) throws ServiceParserException {
		if (config.isNewMultiWorkspace()) {
			OraSB10gMultiWorkspace multiWorkspace = oraSB10gMWorkspaceParser.parse(config.getMultiWorkspaceName(), config.getWorkspaceDir());
			this.multiWorkspaces.add(multiWorkspace);
			writeAppData();
			return multiWorkspace;
		}
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
					writeAppData();
					return (OraSB10gMultiWorkspace) multiWorkspace;
				}
			}
		}
		throw new ServiceParserException("not found multi workspace with name: " + config.getMultiWorkspaceName(), true);
	}

	public final MultiWorkspace refreshMultiWorkspace(MultiWorkspace multiWorkspace) {
		if (multiWorkspace.getType() == WorkspaceType.ORACLE_1OG) {
			return refreshOra10gMultiWorkspace((Ora10gMultiWorkspace) multiWorkspace);
		}

		return null;
	}

	public final Ora10gMultiWorkspace refreshOra10gMultiWorkspace(MultiWorkspace multiWorkspace) {
		for (int i = 0; i <= multiWorkspaces.size() - 1; i++) {
			if (multiWorkspaces.get(i).equals(multiWorkspace)) {
				Ora10gMWorkspaceParser parser = new Ora10gMWorkspaceParser();
				Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspaces.get(i);
				parser.analyseDependnecies(ora10gMultiWorkspace);
				return ora10gMultiWorkspace;
			}
		}
		return null;
	}

	/**
	 * remove {@link MultiWorkspace} from list of {@link MultiWorkspace}
	 * 
	 * @param removeMultiWorkpsace
	 * @return removed {@link MultiWorkspace}
	 */
	public final MultiWorkspace removeMultiWorkspace(Object removeMultiWorkpsace) {
		for (int i = 0; i <= multiWorkspaces.size() - 1; i++) {
			if (multiWorkspaces.get(i).equals(removeMultiWorkpsace)) {
				MultiWorkspace rmMultiWorkspace = multiWorkspaces.get(i);
				multiWorkspaces.remove(i);
				// writeWorkspaces();

				writeAppData();
				return rmMultiWorkspace;
			}

		}
		return null;
	}

	/**
	 * remove {@link Workspace} from list current {@link MultiWorkspace}
	 * 
	 * @param removeWorkspace
	 * @return
	 */
	public final Workspace removeWorkspace(Object removeWorkspace) {
		try {
			for (MultiWorkspace multiWorkspace : multiWorkspaces) {
				if (multiWorkspace instanceof Ora10gMultiWorkspace) {
					Workspace workspace = ((Ora10gMultiWorkspace) multiWorkspace).removeWorkspace((Workspace) removeWorkspace);
					if (workspace != null) {
						return workspace;
					}
				} else if (multiWorkspace instanceof OpenEsbMultiWorkspace) {
					Workspace workspace = ((OpenEsbMultiWorkspace) multiWorkspace).removeWorkspace((Workspace) removeWorkspace);
					if (workspace != null) {
						return workspace;
					}
				} else if (multiWorkspace instanceof OraSB10gMultiWorkspace) {
					Workspace workspace = ((OraSB10gMultiWorkspace) multiWorkspace).removeWorkspace((Workspace) removeWorkspace);
					if (workspace != null) {
						return workspace;
					}
				}
			}
			return null;
		} finally {
			writeAppData();
		}
	}

	/**
	 * add new project to workspace
	 * 
	 * @param config
	 */
	public final void addProject(AddNewProjectToWorkspaceConfig config) {
		WorkspaceType type = config.getSelectedWorkspace().getType();
		if (type == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
			OraSB10gProject project = oraSB10gMWorkspaceParser.addNewProject(config.isAsFolder(), config.getPath());
			OraSB10gWorkspace workspace = (OraSB10gWorkspace) config.getSelectedWorkspace();
			workspace.addProject(project);
		}
		writeAppData();
	}

}

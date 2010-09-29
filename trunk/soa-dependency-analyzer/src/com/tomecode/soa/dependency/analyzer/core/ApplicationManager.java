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
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.tomecode.soa.dependency.analyzer.gui.displays.OpenNewWorkspaceWizard.WorkspaceConfig;
import com.tomecode.soa.openesb.bpel.parser.OpenEsbMWorkspaceParser;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gMWorkspaceParser;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.parser.Ora10gMWorkspaceParser;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.parser.ServiceParserException;
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
	private final void writeWorkspaces() {
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

			output = new XMLWriter(new FileWriter(file));
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
	 * write data to xml
	 * 
	 * @param rootElement
	 */
	private final void writeData(Element rootElement) {
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			Element eMultiWorkspace = DocumentHelper.createElement("multi-workspace");
			rootElement.add(eMultiWorkspace);

			eMultiWorkspace.addAttribute("name", multiWorkspace.getName());
			if (multiWorkspace.getType() == WorkspaceType.ORACLE_1OG) {
				eMultiWorkspace.addAttribute("type", "oracle-10g");
			} else if (multiWorkspace.getType() == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
				eMultiWorkspace.addAttribute("type", "oracle-10g-osb");
			} else if (multiWorkspace.getType() == WorkspaceType.OPEN_ESB) {
				eMultiWorkspace.addAttribute("type", "open-esb");
			}

			Element ePath = DocumentHelper.createElement("path");
			eMultiWorkspace.add(ePath);
			ePath.addText(multiWorkspace.getFile().toString());
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
					OraSB10gMWorkspaceParser osb10gParser = new OraSB10gMWorkspaceParser();
					MultiWorkspace multiWorkspace = osb10gParser.parse(name, new File(path));
					multiWorkspaces.add(multiWorkspace);
				} else if ("open-esb".equals(type)) {
					OpenEsbMWorkspaceParser openEsbParser = new OpenEsbMWorkspaceParser();
					MultiWorkspace multiWorkspace = openEsbParser.parse(name, new File(path));
					multiWorkspaces.add(multiWorkspace);
				}
			}
		}
	}

	public final List<MultiWorkspace> getMultiWorkspaces(WorkspaceType type) {
		List<MultiWorkspace> newMultiWorkspaces = new ArrayList<MultiWorkspace>();
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == type) {
				newMultiWorkspaces.add(multiWorkspace);
			}
		}

		return newMultiWorkspaces;

	}

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

			writeWorkspaces();
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects

					writeWorkspaces();
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

			writeWorkspaces();
			return ora10gMultiWorkspace;
		}
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspace;
					Ora10gWorkspace newWorkspace = parser.parseWorkspace(config.getWorkspaceDir());
					ora10gMultiWorkspace.addWorkspace(newWorkspace);
					parser.analyseDependnecies(ora10gMultiWorkspace);
					writeWorkspaces();
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

			writeWorkspaces();
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
					writeWorkspaces();
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
			//	writeWorkspaces();
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
	}
}

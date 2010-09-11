package com.tomecode.soa.dependency.analyzer.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * 
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

	private final void init() {
		try {
			File f = new File("/Users/tomasfrastia/projects/jdev10gWorkspace/10gSoaBpel");

			Ora10gMultiWorkspace multiWorkspace = new Ora10gMWorkspaceParser().parse("test-soa-suite-10g", f);
			multiWorkspaces.add(multiWorkspace);

			f = new File("/Users/tomasfrastia/projects/soaToolsWorkspace/bpel-samples/open-esb/bpelModules");
			OpenEsbMultiWorkspace openEsbMultiWorkspace = new OpenEsbMWorkspaceParser().parse(f, "test-open-esb");

			multiWorkspaces.add(openEsbMultiWorkspace);

			f = new File("/Users/tomasfrastia/Downloads/The_Definitive_Guide_to_SOA_Oracle_reg_Service_Bus_Second_Edition-4472");
			OraSB10gMultiWorkspace oraSB10gMultiWorkspace = new OraSB10gMWorkspaceParser().parse("test-osb-10g", f);

			multiWorkspaces.add(oraSB10gMultiWorkspace);
		} catch (ServiceParserException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
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
	 * parse {@link OpenEsbMultiWorkspace}
	 * 
	 * @param config
	 * @return
	 * @throws ServiceParserException
	 */
	public final OpenEsbMultiWorkspace parseEsbMultiWorkspace(WorkspaceConfig config) throws ServiceParserException {
		if (config.isNewMultiWorkspace()) {
			OpenEsbMWorkspaceParser parser = new OpenEsbMWorkspaceParser();
			OpenEsbMultiWorkspace multiWorkspace = parser.parse(config.getWorkspaceDir(), config.getMultiWorkspaceName());
			this.multiWorkspaces.add(multiWorkspace);
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
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
			return ora10gMultiWorkspace;
		}
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspace;
					Ora10gWorkspace newWorkspace = parser.parseWorkspace(config.getWorkspaceDir());
					ora10gMultiWorkspace.addWorkspace(newWorkspace);
					parser.analyseDependnecies(ora10gMultiWorkspace);
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
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getMultiWorkspaceName())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
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
				return multiWorkspaces.remove(i);
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

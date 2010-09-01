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
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.workspace.MultiWorkspace;
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

			Ora10gMultiWorkspace multiWorkspace = new Ora10gMWorkspaceParser().parse(f);
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
		if (config.isNewMWorkspace()) {
			OpenEsbMWorkspaceParser parser = new OpenEsbMWorkspaceParser();
			OpenEsbMultiWorkspace multiWorkspace = parser.parse(config.getWorkspaceDir(), config.getMWorkspaceNew());
			this.multiWorkspaces.add(multiWorkspace);
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getExistMWorkspace())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
					return (OpenEsbMultiWorkspace) multiWorkspace;
				}
			}
		}

		throw new ServiceParserException("not found multi workspace with name: " + config.getExistMWorkspace(), true);
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
		if (config.isNewMWorkspace()) {
			Ora10gMWorkspaceParser parser = new Ora10gMWorkspaceParser();
			Ora10gMultiWorkspace ora10gMultiWorkspace = parser.parse(config.getWorkspaceDir());
			this.multiWorkspaces.add(ora10gMultiWorkspace);
			return ora10gMultiWorkspace;
		}
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getExistMWorkspace())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
					return (Ora10gMultiWorkspace) multiWorkspace;
				}
			}

		}

		throw new ServiceParserException("not found multi workspace with name: " + config.getExistMWorkspace(), true);
	}

	public final MultiWorkspace parseOraSB10gMultiWorkspace(WorkspaceConfig config) throws ServiceParserException {
		if (config.isNewMWorkspace()) {
			OraSB10gMultiWorkspace multiWorkspace = oraSB10gMWorkspaceParser.parse(config.getMWorkspaceNew(), config.getWorkspaceDir());
			this.multiWorkspaces.add(multiWorkspace);
			return multiWorkspace;
		}

		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			if (multiWorkspace.getType() == config.getWorkspaceType()) {
				if (multiWorkspace.getName().equalsIgnoreCase(config.getExistMWorkspace())) {
					// /TODO: merger workspace... analyze dependencies between
					// exist projects
					return (OraSB10gMultiWorkspace) multiWorkspace;
				}
			}
		}

		throw new ServiceParserException("not found multi workspace with name: " + config.getExistMWorkspace(), true);

	}

	public final void refershOrale10g(Ora10gMultiWorkspace multiWorkspace) {

	}

	public final void refreshOpenEsb(OpenEsbMultiWorkspace selectedNode) {

	}

}

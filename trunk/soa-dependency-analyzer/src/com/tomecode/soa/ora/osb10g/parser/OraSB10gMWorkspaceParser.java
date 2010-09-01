package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;

/**
 * Oracle Service Bus 10g Multi Workspace parser
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gMWorkspaceParser extends AbstractParser {

	public static final void main(String[] arg) {
		OraSB10gMWorkspaceParser parser = new OraSB10gMWorkspaceParser();
		OraSB10gMultiWorkspace multiWorkspace = parser.parse("ahoj", new File("/Users/tomasfrastia/Downloads/The_Definitive_Guide_to_SOA_Oracle_reg_Service_Bus_Second_Edition-4472/"));
		// "/Users/tomasfrastia/Downloads/The_Definitive_Guide_to_SOA_Oracle_reg_Service_Bus_Second_Edition-4472/Security_SB")
		multiWorkspace.toString();
	}

	private final OraSB10gProjectParser projectParser;

	public OraSB10gMWorkspaceParser() {
		projectParser = new OraSB10gProjectParser();
	}

	public final OraSB10gMultiWorkspace parse(String name, File file) {
		OraSB10gMultiWorkspace multiWorkspace = new OraSB10gMultiWorkspace(name, file);
		OraSB10gWorkspace workspace = new OraSB10gWorkspace(name, file);

		multiWorkspace.addWorkspace(workspace);

		File[] files = file.listFiles();
		if (files != null) {
			for (File projectFile : files) {
				if (projectFile.isDirectory()) {
					OraSB10gProject project = projectParser.parse(projectFile);
					workspace.addProject(project);
				}
			}
		}

		toString();
		return multiWorkspace;
	}

}

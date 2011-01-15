package com.tomecode.soa.dependency.analyzer.tree.node;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.project.Project;

/**
 * 
 * Simple helper tree node for ESB project - wrapper treeNode for
 * {@link Ora10gEsbProject} which does not show project dependencies
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EsbServiceNode implements DependencyNode, ImageFace {

	private static final long serialVersionUID = -2232894399903425396L;

	private Ora10gEsbProject esbProject;

	/**
	 * Constructor
	 * 
	 * @param esbProject
	 */
	public EsbServiceNode(Ora10gEsbProject esbProject) {
		this.esbProject = esbProject;
	}

	public int getChildCount() {
		return esbProject.getBasicEsbNodes().size();
	}

	public final boolean hasChildren() {
		return !esbProject.getBasicEsbNodes().isEmpty();
	}

	public final Object[] getChildren() {
		return esbProject.getBasicEsbNodes().toArray();
	}

	public final String toString() {
		return esbProject.toString();
	}

	@Override
	public Project getProject() {
		return esbProject;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_ESB;
	}

	@Override
	public String getToolTip() {
		return null;
	}

}

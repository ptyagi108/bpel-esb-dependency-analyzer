package com.tomecode.soa.ora.suite10g.esb.protocols.db;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.protocols.EsbAdapter;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * DB protocol for {@link EsbSvc}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "FTP Adapter")
public final class FTPAdapter extends EsbAdapter implements ImageFace, Node<FTPAdapter> {

	@PropertyViewData(title = "File Type:")
	private String fileType;

	@PropertyViewData(title = "Physical Directory:")
	private String physicalDirectory;

	@PropertyViewData(title = "Interaction Spec:")
	private String interactionSpec;

	@PropertyViewData(title = "File Naming Convention:")
	private String fileNamingConvention;

	@PropertyViewData(title = "Number Messages:")
	private String numberMessages;

	@PropertyViewData(title = "Qpaque Schema:")
	private String qpaqueSchema;

	public FTPAdapter(String locations) {
		super(locations);
	}

	@Override
	public final FTPAdapter getObj() {
		return this;
	}

	@Override
	public Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FTP_SERVER_SMALL;
		}
		return ImageFactory.FTP_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "FTP Repository";
	}

	/**
	 * @return the fileType
	 */
	public final String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public final void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the physicalDirectory
	 */
	public final String getPhysicalDirectory() {
		return physicalDirectory;
	}

	/**
	 * @param physicalDirectory
	 *            the physicalDirectory to set
	 */
	public final void setPhysicalDirectory(String physicalDirectory) {
		this.physicalDirectory = physicalDirectory;
	}

	/**
	 * @return the interactionSpec
	 */
	public final String getInteractionSpec() {
		return interactionSpec;
	}

	/**
	 * @param interactionSpec
	 *            the interactionSpec to set
	 */
	public final void setInteractionSpec(String interactionSpec) {
		this.interactionSpec = interactionSpec;
	}

	/**
	 * @return the fileNamingConvention
	 */
	public final String getFileNamingConvention() {
		return fileNamingConvention;
	}

	/**
	 * @param fileNamingConvention
	 *            the fileNamingConvention to set
	 */
	public final void setFileNamingConvention(String fileNamingConvention) {
		this.fileNamingConvention = fileNamingConvention;
	}

	/**
	 * @return the numberMessages
	 */
	public final String getNumberMessages() {
		return numberMessages;
	}

	/**
	 * @param numberMessages
	 *            the numberMessages to set
	 */
	public final void setNumberMessages(String numberMessages) {
		this.numberMessages = numberMessages;
	}

	/**
	 * @return the qpaqueSchema
	 */
	public final String getQpaqueSchema() {
		return qpaqueSchema;
	}

	/**
	 * @param qpaqueSchema
	 *            the qpaqueSchema to set
	 */
	public final void setQpaqueSchema(String qpaqueSchema) {
		this.qpaqueSchema = qpaqueSchema;
	}

}

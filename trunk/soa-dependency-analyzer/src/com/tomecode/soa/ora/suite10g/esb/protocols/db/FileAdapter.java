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
 * FILE adapter from {@link EsbSvc}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "File Adapter")
public final class FileAdapter extends EsbAdapter implements Node<FileAdapter>, ImageFace {

	@PropertyViewData(title = "Physical Directory:")
	private String physicalDirectory;

	@PropertyViewData(title = "Activation Spec:")
	private String activationSpec;

	@PropertyViewData(title = "Interaction Spec:")
	private String interactionSpec;

	@PropertyViewData(title = "File Naming Convention:")
	private String fileNamingConvention;

	@PropertyViewData(title = "Number Messages:")
	private String numberMessages;

	@PropertyViewData(title = "Qpaque Schema:")
	private String qpaqueSchema;

	@PropertyViewData(title = "Delete File:")
	private String deleteFile;

	@PropertyViewData(title = "Polling Frequency:")
	private String pollingFrequency;

	@PropertyViewData(title = "Minimum Age:")
	private String minimumAge;

	public FileAdapter(String locations) {
		super(locations);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FILE_SERVER_SMALL;
		}
		return ImageFactory.FILE_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "FILE Adapter";
	}

	@Override
	public final FileAdapter getObj() {
		return this;
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

	/**
	 * @return the deleteFile
	 */
	public final String getDeleteFile() {
		return deleteFile;
	}

	/**
	 * @param deleteFile
	 *            the deleteFile to set
	 */
	public final void setDeleteFile(String deleteFile) {
		this.deleteFile = deleteFile;
	}

	/**
	 * @return the pollingFrequency
	 */
	public final String getPollingFrequency() {
		return pollingFrequency;
	}

	/**
	 * @param pollingFrequency
	 *            the pollingFrequency to set
	 */
	public final void setPollingFrequency(String pollingFrequency) {
		this.pollingFrequency = pollingFrequency;
	}

	/**
	 * @return the minimumAge
	 */
	public final String getMinimumAge() {
		return minimumAge;
	}

	/**
	 * @param minimumAge
	 *            the minimumAge to set
	 */
	public final void setMinimumAge(String minimumAge) {
		this.minimumAge = minimumAge;
	}

	/**
	 * @return the activationSpec
	 */
	public final String getActivationSpec() {
		return activationSpec;
	}

	/**
	 * @param activationSpec
	 *            the activationSpec to set
	 */
	public final void setActivationSpec(String activationSpec) {
		this.activationSpec = activationSpec;
	}

}

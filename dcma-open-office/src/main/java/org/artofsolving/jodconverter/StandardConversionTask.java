/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

//
// JODConverter - Java OpenDocument Converter
// Copyright 2009 Art of Solving Ltd
// Copyright 2004-2009 Mirko Nasato
//
// JODConverter is free software: you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// JODConverter is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General
// Public License along with JODConverter.  If not, see
// <http://www.gnu.org/licenses/>.
//
package org.artofsolving.jodconverter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.artofsolving.jodconverter.document.DocumentFamily;
import org.artofsolving.jodconverter.document.DocumentFormat;

import com.sun.star.lang.XComponent;

/**
 * This is standard conversion class.
 * @author Ephesoft
 * @version 1.0
 * @see org.artofsolving.jodconverter.document.DocumentFamily
 */
public class StandardConversionTask extends AbstractConversionTask {

	/**
	 * outputFormat DocumentFormat.
	 */
	private final DocumentFormat outputFormat;

	/**
	 * defaultLoadProperties Map<String, ?>.
	 */
	private Map<String, ?> defaultLoadProperties;
	
	/**
	 * inputFormat DocumentFormat.
	 */
	private DocumentFormat inputFormat;

	/**
	 * Constructor.
	 * @param inputFile File
	 * @param outputFile File
	 * @param outputFormat DocumentFormat
	 */ 
	public StandardConversionTask(File inputFile, File outputFile, DocumentFormat outputFormat) {
		super(inputFile, outputFile);
		this.outputFormat = outputFormat;
	}

	/**
	 * Constructor.
	 * @param inputFileURL String 
	 * @param outputFile File
	 * @param outputFormat DocumentFormat
	 */
	public StandardConversionTask(String inputFileURL, File outputFile, DocumentFormat outputFormat) {
		super(inputFileURL, outputFile);
		this.outputFormat = outputFormat;
	}

	/**
	 * To set Default Load Properties.
	 * @param defaultLoadProperties Map<String, ?>
	 */
	public void setDefaultLoadProperties(Map<String, ?> defaultLoadProperties) {
		this.defaultLoadProperties = defaultLoadProperties;
	}

	/**
	 * To set input format.
	 * @param inputFormat DocumentFormat
	 */
	public void setInputFormat(DocumentFormat inputFormat) {
		this.inputFormat = inputFormat;
	}

	@Override
	protected Map<String, ?> getLoadProperties(File inputFile) {
		Map<String, Object> loadProperties = new HashMap<String, Object>();
		if (defaultLoadProperties != null) {
			loadProperties.putAll(defaultLoadProperties);
		}
		if (inputFormat != null && inputFormat.getLoadProperties() != null) {
			loadProperties.putAll(inputFormat.getLoadProperties());
		}
		return loadProperties;
	}

	@Override
	protected Map<String, ?> getStoreProperties(File outputFile, XComponent document) {
		DocumentFamily family = OfficeDocumentUtils.getDocumentFamily(document);
		return outputFormat.getStoreProperties(family);
	}

}

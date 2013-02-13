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

package com.ephesoft.dcma.script.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.core.annotation.PostProcess;
import com.ephesoft.dcma.core.annotation.PreProcess;
import com.ephesoft.dcma.da.id.BatchInstanceID;
import com.ephesoft.dcma.da.service.BatchInstanceService;
import com.ephesoft.dcma.script.ScriptExecutor;
import com.ephesoft.dcma.util.BackUpFileService;

/**
 * This service is used to call the scripts on the basis of plug-in name. Scripts are placed at some pre-defined location and this
 * plug-in will invoke the scripts. This plug-in will compile the scripts at run time and execute it. This service plug-in can be used
 * after any plug-in.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.script.service.ScriptService
 */
public class ScriptServiceImpl implements ScriptService {

	/**
	 * LOGGER to print the logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptServiceImpl.class);

	/**
	 * scriptExecutor {@link ScriptExecutor}.
	 */
	@Autowired
	private ScriptExecutor scriptExecutor;

	/**
	 * Instance of {@link BatchInstanceService}.
	 */
	@Autowired
	private BatchInstanceService batchInstanceService;

	/**
	 * Pre-processing method.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 */
	@PreProcess
	public void preProcess(final BatchInstanceID batchInstanceID, final String pluginWorkflow) {
		Assert.notNull(batchInstanceID);
		final String batchInstanceIdentifier = batchInstanceID.getID();
		BackUpFileService.backUpBatch(batchInstanceIdentifier, batchInstanceService
				.getSystemFolderForBatchInstanceId(batchInstanceIdentifier));
	}

	/**
	 * Post-processing method.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 */
	@PostProcess
	public void postProcess(final BatchInstanceID batchInstanceID, final String pluginWorkflow) {
		Assert.notNull(batchInstanceID);
		final String batchInstanceIdentifier = batchInstanceID.getID();
		BackUpFileService.backUpBatch(batchInstanceIdentifier, pluginWorkflow, batchInstanceService
				.getSystemFolderForBatchInstanceId(batchInstanceIdentifier));
	}

	/**
	 * This method will compile and execute all the scripts for input plug-in name placed at some pre defined location.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 * @param nameOfPluginScript {@link String}
	 * @throws DCMAException in case of error
	 */
	@Override
	public void executeScript(final BatchInstanceID batchInstanceID, final String pluginWorkflow, final String pluginScriptName)
			throws DCMAException {
		executeScript(batchInstanceID, pluginWorkflow, pluginScriptName, null, null);
	}

	/**
	 * This method will compile and execute all the scripts for input plug-in name placed at some pre defined location.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 * @param nameOfPluginScript {@link String}
	 * @param docIdentifier {@link String}
	 * @param scriptVariableName {@link String}(in case of script execution on value change it is field Name and in case of function
	 *            key script execution it is methodName
	 * @throws DCMAException in case of error
	 */
	@Override
	public void executeScript(final BatchInstanceID batchInstanceID, final String pluginWorkflow, final String pluginScriptName,
			final String docIdentifier, final String scriptVariableName) throws DCMAException {
		try {
			scriptExecutor.extractFields(batchInstanceID.getID(), pluginScriptName, docIdentifier, scriptVariableName);
		} catch (Exception e) {
			LOGGER.error("The result of execution for ScriptsService was unsuccessful.", e.getMessage());
			throw new DCMAException("The result of execution for ScriptsService was unsuccessful.", e);
		}
	}
}

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

package com.ephesoft.dcma.gwt.admin.bm.client.event.batch;

import com.ephesoft.dcma.gwt.core.client.util.Action;
import com.ephesoft.dcma.gwt.core.shared.BatchClassDTO;
import com.google.gwt.event.shared.GwtEvent;

/**
 * This class is used to create a batch class event when the user saves or cancels the changes made in a Batch Class.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.google.gwt.event.shared.GwtEvent
 */
public class BatchClassEvent extends GwtEvent<BatchClassHandler> {

	/**
	 * Type of the batch class event.
	 */
	public static Type<BatchClassHandler> type = new Type<BatchClassHandler>();

	/**
	 * Action taken.
	 */
	private final Action action;
	
	/**
	 * Batch Class the user is working on.
	 */
	private BatchClassDTO batchClass;

	/**
	 * Constructor.
	 * 
	 * @param action Action
	 */
	public BatchClassEvent(final Action action) {
		super();
		this.action = action;
	}

	/**
	 * Constructor.
	 * 
	 * @param action Action
	 * @param batchClass BatchClassDTO
	 */
	public BatchClassEvent(final Action action, final BatchClassDTO batchClass) {
		super();
		this.batchClass = batchClass;
		this.action = action;
	}

	@Override
	protected void dispatch(final BatchClassHandler handler) {
		switch (action) {
			case SAVE:
				handler.onSave(this);
				break;
			case CANCEL:
				handler.onCancel(this);
				break;
			default:
				break;
		}
	}

	/**
	 * To get Associated Type.
	 * 
	 * @return com.google.gwt.event.shared.GwtEvent.Type<BatchClassHandler>
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BatchClassHandler> getAssociatedType() {
		return type;
	}

	/**
	 * To get Batch Class.
	 * 
	 * @return BatchClassDTO
	 */
	public BatchClassDTO getBatchClass() {
		return batchClass;
	}
}

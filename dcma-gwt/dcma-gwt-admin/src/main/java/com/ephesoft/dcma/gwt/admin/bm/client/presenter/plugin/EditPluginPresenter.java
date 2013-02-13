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

package com.ephesoft.dcma.gwt.admin.bm.client.presenter.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ephesoft.dcma.gwt.admin.bm.client.BatchClassManagementController;
import com.ephesoft.dcma.gwt.admin.bm.client.MessageConstants;
import com.ephesoft.dcma.gwt.admin.bm.client.i18n.BatchClassManagementConstants;
import com.ephesoft.dcma.gwt.admin.bm.client.i18n.PluginNameConstants;
import com.ephesoft.dcma.gwt.admin.bm.client.presenter.AbstractBatchClassPresenter;
import com.ephesoft.dcma.gwt.admin.bm.client.view.plugin.EditPluginView;
import com.ephesoft.dcma.gwt.core.client.validator.ValidatableWidget;
import com.ephesoft.dcma.gwt.core.shared.BatchClassPluginConfigDTO;
import com.ephesoft.dcma.gwt.core.shared.ConfirmationDialogUtil;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * The presenter for view that shows edit plugin.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.gwt.admin.bm.client.presenter.AbstractBatchClassPresenter
 */
public class EditPluginPresenter extends AbstractBatchClassPresenter<EditPluginView> {

	/**
	 * docFieldWidgets List<EditableWidgetStorage>.
	 */
	private List<EditableWidgetStorage> docFieldWidgets;

	/**
	 * MAX_VISIBLE_ITEM_COUNT int.
	 */
	public static final int MAX_VISIBLE_ITEM_COUNT = 4;

	/**
	 * Constructor.
	 * 
	 * @param controller BatchClassManagementController
	 * @param view EditPluginView
	 */
	public EditPluginPresenter(BatchClassManagementController controller, EditPluginView view) {
		super(controller, view);
	}

	/**
	 * To show view in case of cancel click.
	 */
	public void onCancel() {
		controller.getMainPresenter().getPluginViewPresenter().bind();
		controller.getMainPresenter().getPluginViewPresenter().showPluginViewDetail();
	}

	/**
	 * To show view in case of save click.
	 */
	public void onSave() {
		boolean fieldsValid = false;
		Collection<BatchClassPluginConfigDTO> values = controller.getSelectedPlugin().getBatchClassPluginConfigs();
		for (EditableWidgetStorage editableWidgetStorage : docFieldWidgets) {
			if (editableWidgetStorage.isValidatable()) {
				if (!editableWidgetStorage.isListBox()) {
					String textBoxValue = editableWidgetStorage.getTextBoxWidget().getWidget().getText();
					if (!editableWidgetStorage.getTextBoxWidget().validate()) {
						if (editableWidgetStorage.mandatory) {// mandatory fields
							if ((textBoxValue != null && textBoxValue.isEmpty())) {
								ConfirmationDialogUtil.showConfirmationDialogError(MessageConstants.MANDATORY_FIELDS_ERROR_MSG);
								fieldsValid = true;
								break;
							} else if (textBoxValue != null) {
								ConfirmationDialogUtil.showConfirmationDialogError(MessageConstants.FIELD_NOT_VALID_ERROR_MSG);
								fieldsValid = true;
								break;
							}
						} else {// non-mandatory fields
							if (!(textBoxValue != null && textBoxValue.isEmpty())) {
								ConfirmationDialogUtil.showConfirmationDialogError(MessageConstants.FIELD_NOT_VALID_ERROR_MSG);
								fieldsValid = true;
								break;
							}
						}
					}
				} else {
					if (editableWidgetStorage.getListBoxwidget().getSelectedIndex() == -1) {
						ConfirmationDialogUtil.showConfirmationDialogError(MessageConstants.MANDATORY_FIELDS_ERROR_MSG);
						fieldsValid = true;
						break;
					}
				}
			}
		}
		if (!fieldsValid) {
			int index = 0;
			for (BatchClassPluginConfigDTO batchClassPluginConfigDTO : values) {
				if (docFieldWidgets.get(index).listBox) {
					if (docFieldWidgets.get(index).getListBoxwidget().isMultipleSelect()) {
						StringBuffer selectedItem = new StringBuffer();
						Integer numberOfItemSelected = docFieldWidgets.get(index).getListBoxwidget().getItemCount();
						for (int i = 0; i < numberOfItemSelected; i++) {
							if (docFieldWidgets.get(index).getListBoxwidget().isItemSelected(i)) {
								selectedItem.append(docFieldWidgets.get(index).getListBoxwidget().getItemText(i)).append(BatchClassManagementConstants.SEMICOLON);
							}
						}
						String selectedItemString = selectedItem.toString();
						selectedItemString = selectedItemString.substring(0, selectedItemString.length() - 1);
						batchClassPluginConfigDTO.setValue(selectedItemString);
					}

					else {
						batchClassPluginConfigDTO.setValue(docFieldWidgets.get(index).getListBoxwidget().getItemText(
								docFieldWidgets.get(index).getListBoxwidget().getSelectedIndex()));
					}
				} else {
					batchClassPluginConfigDTO.setValue(docFieldWidgets.get(index).getTextBoxWidget().getWidget().getValue());
				}
				index++;
			}

			controller.getMainPresenter().getPluginViewPresenter().bind();
			controller.getMainPresenter().getPluginViewPresenter().showPluginViewDetail();

		}
	}

	/**
	 * To set properties.
	 */
	public void setProperties() {
		ListBox hocrToPdfListBox = null;
		ListBox createMultipagePdfOptimizationSwitchListBox = null;
		ListBox tabbedPdfOptimizationSwitchListBox = null;
		docFieldWidgets = new ArrayList<EditableWidgetStorage>();
		int row = 0;
		Collection<BatchClassPluginConfigDTO> values = controller.getSelectedPlugin().getBatchClassPluginConfigs();
		if (values != null) {
			for (final BatchClassPluginConfigDTO batchClassPluginConfig : values) {
				view.formatRow(row);
				view.addWidget(row, 0, new Label(batchClassPluginConfig.getDescription() + BatchClassManagementConstants.COLON));

				if (batchClassPluginConfig.isMandatory()) {
					view.addWidgetStar(row, 1);
				}
				if (batchClassPluginConfig.getSampleValue() != null && !batchClassPluginConfig.getSampleValue().isEmpty()) {
					if (batchClassPluginConfig.getSampleValue().size() > 1) {
						// Create a listBox
						if (batchClassPluginConfig.isMultivalue()) {
							// Create a multiple select list box
							List<String> sampleValueList = batchClassPluginConfig.getSampleValue();
							int max_visible_item_count = MAX_VISIBLE_ITEM_COUNT;
							if (sampleValueList.size() < MAX_VISIBLE_ITEM_COUNT) {
								max_visible_item_count = sampleValueList.size();
							}
							ListBox fieldValue = view.addMultipleSelectListBox(row, sampleValueList, max_visible_item_count,
									batchClassPluginConfig.getValue());
							view.addWidget(row, 2, fieldValue);
							docFieldWidgets.add(new EditableWidgetStorage(fieldValue));
						} else {
							// Create a drop down
							final ListBox fieldValue = view.addDropDown(row, batchClassPluginConfig.getSampleValue(),
									batchClassPluginConfig.getValue());
							fieldValue.setName(batchClassPluginConfig.getPluginConfig().getFieldName());
							if (batchClassPluginConfig.getPluginConfig().getFieldName().equalsIgnoreCase(
									PluginNameConstants.EXPORT_PLUGIN_TYPE_DESC)) {
								hocrToPdfListBox = fieldValue;
								fieldValue.addChangeHandler(new ChangeHandler() {

									@Override
									public void onChange(ChangeEvent arg0) {
										enableHocrToPdfProps(fieldValue);
									}
								});
							}
							if (batchClassPluginConfig.getPluginConfig().getFieldName().equalsIgnoreCase(
									PluginNameConstants.CREATE_MULTIPAGE_PDF_OPTIMIZATION_SWITCH)) {
								createMultipagePdfOptimizationSwitchListBox = fieldValue;
								fieldValue.addChangeHandler(new ChangeHandler() {

									@Override
									public void onChange(ChangeEvent arg0) {
										enableCreateMultipageOptimizationProps(fieldValue);
									}
								});
							}
							if (batchClassPluginConfig.getPluginConfig().getFieldName().equalsIgnoreCase(
									PluginNameConstants.TABBED_PDF_OPTIMIZATION_SWITCH)) {
								createMultipagePdfOptimizationSwitchListBox = fieldValue;
								fieldValue.addChangeHandler(new ChangeHandler() {

									@Override
									public void onChange(ChangeEvent arg0) {
										enableTabbedPdfOptimizationProps(fieldValue);
									}
								});
							}
							view.addWidget(row, 2, fieldValue);
							EditableWidgetStorage editableWidgetStorage = new EditableWidgetStorage(fieldValue);
							editableWidgetStorage.setValidatable(Boolean.FALSE);
							docFieldWidgets.add(editableWidgetStorage);
						}
					} else {
						// Create a read only text box
						ValidatableWidget<TextBox> validatableTextBox = view.addTextBox(row, batchClassPluginConfig, true);
						view.addWidget(row, 2, validatableTextBox.getWidget());
						EditableWidgetStorage editableWidgetStorage = new EditableWidgetStorage(validatableTextBox);
						editableWidgetStorage.setValidatable(Boolean.FALSE);
						docFieldWidgets.add(editableWidgetStorage);
					}
				} else {
					// Create a text box
					ValidatableWidget<TextBox> validatableTextBox = view.addTextBox(row, batchClassPluginConfig, false);
					view.addWidget(row, 2, validatableTextBox.getWidget());
					EditableWidgetStorage editableWidgetStorage = new EditableWidgetStorage(validatableTextBox);
					if (batchClassPluginConfig.isMandatory()) {
						editableWidgetStorage.setMandatory(true);
					}
					docFieldWidgets.add(editableWidgetStorage);
				}
				row++;
			}
			row++;
			// view.addButtons(row);
		}
		enableHocrToPdfProps(hocrToPdfListBox);
		enableCreateMultipageOptimizationProps(createMultipagePdfOptimizationSwitchListBox);
		enableTabbedPdfOptimizationProps(tabbedPdfOptimizationSwitchListBox);
	}

	/**
	 * This is editable widget storage class.
	 * 
	 * @author Ephesoft
	 * @version 1.0
	 */
	private static class EditableWidgetStorage {

		/**
		 * widget ValidatableWidget<TextBox>.
		 */
		private final ValidatableWidget<TextBox> widget;

		/**
		 * listBox boolean.
		 */
		private final boolean listBox;

		/**
		 * listBoxwidget ListBox.
		 */
		private final ListBox listBoxwidget;

		/**
		 * validatable boolean.
		 */
		private boolean validatable;

		/**
		 * mandatory boolean.
		 */
		private boolean mandatory;

		/**
		 * Constructor.
		 * 
		 * @param widget ValidatableWidget<TextBox>
		 */
		public EditableWidgetStorage(ValidatableWidget<TextBox> widget) {
			this(widget, false, null, true, false);
		}

		/**
		 * Constructor.
		 * 
		 * @param listBoxwidget ListBox
		 */
		public EditableWidgetStorage(ListBox listBoxwidget) {
			this(null, true, listBoxwidget, true, false);
		}

		/**
		 * Constructor.
		 * 
		 * @param widget ValidatableWidget<TextBox>
		 * @param listBox boolean
		 * @param listBoxwidget ListBox
		 * @param validatable boolean
		 * @param mandatory boolean
		 */
		public EditableWidgetStorage(ValidatableWidget<TextBox> widget, boolean listBox, ListBox listBoxwidget, boolean validatable,
				boolean mandatory) {
			super();
			this.widget = widget;
			this.listBox = listBox;
			this.listBoxwidget = listBoxwidget;
			this.validatable = validatable;
			this.mandatory = mandatory;
		}

		/**
		 * To get Text Box Widget.
		 * 
		 * @return ValidatableWidget<TextBox>
		 */
		public ValidatableWidget<TextBox> getTextBoxWidget() {
			return widget;
		}

		/**
		 * To get List Box widget.
		 * 
		 * @return ListBox
		 */
		public ListBox getListBoxwidget() {
			return listBoxwidget;
		}

		/**
		 * To check whether list box or not.
		 * 
		 * @return boolean
		 */
		public boolean isListBox() {
			return listBox;
		}

		/**
		 * To check whether validatable.
		 * 
		 * @return boolean
		 */
		public boolean isValidatable() {
			return validatable;
		}

		/**
		 * To set validatable.
		 * 
		 * @param isValidatable boolean
		 */
		public void setValidatable(boolean isValidatable) {
			this.validatable = isValidatable;
		}

		/**
		 * To set mandatory.
		 * 
		 * @param isMandatory boolean
		 */
		public void setMandatory(boolean isMandatory) {
			this.mandatory = isMandatory;
		}
	}

	/**
	 * Processing to be done on load of this presenter.
	 */
	@Override
	public void bind() {
		if (controller.getSelectedPlugin() != null) {
			view.setView();
			setProperties();
			int index = 0;
			if ((docFieldWidgets != null) && (docFieldWidgets.size() != 0)) {
				while ((index < docFieldWidgets.size() && !docFieldWidgets.get(index).listBox)
						&& docFieldWidgets.get(index).widget.getWidget().isReadOnly()) {
					index++;
				}
				if (index < docFieldWidgets.size()) {
					if (docFieldWidgets.get(index).listBox) {
						docFieldWidgets.get(index).listBoxwidget.setFocus(true);
					} else {
						docFieldWidgets.get(index).widget.getWidget().setFocus(true);
					}
				}
			}
		}
	}

	/**
	 * To handle events.
	 * 
	 * @param eventBus HandlerManager
	 */
	@Override
	public void injectEvents(HandlerManager eventBus) {
		// to be used in case of event handling
	}

	private void enableCreateMultipageOptimizationProps(final ListBox fieldValue) {
		if (fieldValue != null) {
			for (EditableWidgetStorage editableWidgetStorage : docFieldWidgets) {
				if (fieldValue.getItemText(fieldValue.getSelectedIndex()).equalsIgnoreCase(PluginNameConstants.ON_STRING)) {
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getTextBoxWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName().equals(
									PluginNameConstants.CREATE_MULTIPAGE_PDF_OPTIMIZATION_PARAMETERS)) {
						editableWidgetStorage.widget.getWidget().setEnabled(true);
					}
				} else {
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getTextBoxWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName().equals(
									PluginNameConstants.CREATE_MULTIPAGE_PDF_OPTIMIZATION_PARAMETERS)) {
						editableWidgetStorage.widget.getWidget().setEnabled(false);
					}
				}
			}
		}

	}

	private void enableTabbedPdfOptimizationProps(final ListBox fieldValue) {
		if (fieldValue != null) {
			for (EditableWidgetStorage editableWidgetStorage : docFieldWidgets) {
				if (fieldValue.getItemText(fieldValue.getSelectedIndex()).equalsIgnoreCase(PluginNameConstants.ON_STRING)) {
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getTextBoxWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName().equals(
									PluginNameConstants.TABBED_PDF_OPTIMIZATION_PARAMETERS)) {
						editableWidgetStorage.widget.getWidget().setEnabled(true);
					}
				} else {
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getTextBoxWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName().equals(
									PluginNameConstants.TABBED_PDF_OPTIMIZATION_PARAMETERS)) {
						editableWidgetStorage.widget.getWidget().setEnabled(false);
					}
				}
			}
		}

	}

	private void enableHocrToPdfProps(final ListBox fieldValue) {
		if (fieldValue != null) {
			for (EditableWidgetStorage editableWidgetStorage : docFieldWidgets) {
				if (fieldValue.getItemText(fieldValue.getSelectedIndex()).equalsIgnoreCase(PluginNameConstants.GHOSTSCRPT)) {
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getListBoxwidget() != null
							&& editableWidgetStorage.getListBoxwidget().getName() != null
							&& editableWidgetStorage.getListBoxwidget().getName()
									.equals(PluginNameConstants.EXPORT_COLORED_OUTPUT_PDF)) {
						editableWidgetStorage.listBoxwidget.setEnabled(false);
					}
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getListBoxwidget() != null
							&& editableWidgetStorage.getListBoxwidget().getName() != null
							&& editableWidgetStorage.getListBoxwidget().getName().equals(
									PluginNameConstants.EXPORT_SEARCHABLE_OUTPUT_PDF)) {
						editableWidgetStorage.listBoxwidget.setEnabled(false);
					}
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getTextBoxWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName().equals(
									PluginNameConstants.GHOSTSCRIPT_PARAMETERS)) {
						editableWidgetStorage.widget.getWidget().setEnabled(true);
					}
				} else {
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getListBoxwidget() != null
							&& editableWidgetStorage.getListBoxwidget().getName() != null
							&& editableWidgetStorage.getListBoxwidget().getName()
									.equals(PluginNameConstants.EXPORT_COLORED_OUTPUT_PDF)) {
						editableWidgetStorage.listBoxwidget.setEnabled(true);
					}
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getListBoxwidget() != null
							&& editableWidgetStorage.getListBoxwidget().getName() != null
							&& editableWidgetStorage.getListBoxwidget().getName().equals(
									PluginNameConstants.EXPORT_SEARCHABLE_OUTPUT_PDF)) {
						editableWidgetStorage.listBoxwidget.setEnabled(true);
					}
					if (editableWidgetStorage != null
							&& editableWidgetStorage.getTextBoxWidget() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName() != null
							&& editableWidgetStorage.getTextBoxWidget().getWidget().getName().equals(
									PluginNameConstants.GHOSTSCRIPT_PARAMETERS)) {
						editableWidgetStorage.widget.getWidget().setEnabled(false);
					}
				}
			}
		}
	}
}

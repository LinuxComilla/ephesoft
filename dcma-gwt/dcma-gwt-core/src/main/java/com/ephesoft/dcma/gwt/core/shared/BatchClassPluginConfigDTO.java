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

package com.ephesoft.dcma.gwt.core.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ephesoft.dcma.core.common.DataType;
import com.ephesoft.dcma.core.common.LocationType;
import com.google.gwt.user.client.rpc.IsSerializable;

public class BatchClassPluginConfigDTO implements IsSerializable {

	private BatchClassPluginDTO batchClassPlugin;

	private PluginConfigurationDTO pluginConfig;

	private String name;

	private String value;

	private String description;

	private List<String> sampleValue;

	private String identifier;

	private String qualifier;

	//private BatchClassPluginConfigDTO parent;

	//private Collection<BatchClassPluginConfigDTO> children;

	private Map<String, BatchClassPluginConfigDTO> batchClassPluginConfigsMap = new LinkedHashMap<String, BatchClassPluginConfigDTO>();
	
	private Collection<KVPageProcessDTO> kvPageProcessDTOs;
	
	private boolean multivalue;
	
	private boolean mandatory;
	
	
	private DataType dataType;
    private Integer orderNumber;
	public BatchClassPluginConfigDTO getChildById(final String identifier) {
		return batchClassPluginConfigsMap.get(identifier);
	}

	public BatchClassPluginDTO getBatchClassPlugin() {
		return batchClassPlugin;
	}

	public void setBatchClassPlugin(final BatchClassPluginDTO batchClassPlugin) {
		this.batchClassPlugin = batchClassPlugin;
	}

	public PluginConfigurationDTO getPluginConfig() {
		return pluginConfig;
	}

	public void setPluginConfig(final PluginConfigurationDTO pluginConfig) {
		this.pluginConfig = pluginConfig;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
		pluginConfig.setDirty(true);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<String> getSampleValue() {
		return sampleValue;
	}

	public void setSampleValue(final List<String> sampleValue) {
		this.sampleValue = sampleValue;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(final String qualifier) {
		this.qualifier = qualifier;
	}

	public boolean isMultivalue() {
		return multivalue;
	}
	public void setMultivalue(final boolean multivalue) {
		this.multivalue = multivalue;
	}
	
	
	public boolean isMandatory() {
		return mandatory;
	}

	
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	public DataType getDataType() {
		return dataType;
	}
	
	public void setDataType(final DataType dataType) {
		this.dataType = dataType;
	}
	
	public Collection<KVPageProcessDTO> getKvPageProcessDTOs(final boolean includeDeleted) {
		Collection<KVPageProcessDTO> kVPageProcessDTO;
		if (includeDeleted){
			kVPageProcessDTO = kvPageProcessDTOs;
		}else{
			kVPageProcessDTO = getKvPageProcessDTOs();
		}
		return kVPageProcessDTO;
	}
	
	public Collection<KVPageProcessDTO> getKvPageProcessDTOs() {
		final List<KVPageProcessDTO> kvPageProcessDTOList = new ArrayList<KVPageProcessDTO>();
		if (kvPageProcessDTOs != null) {
			for (final KVPageProcessDTO kvPageProcessDTO : kvPageProcessDTOs) {
				if (!(kvPageProcessDTO.getIsDeleted())) {
					kvPageProcessDTOList.add(kvPageProcessDTO);
				}
			}
		}
		return kvPageProcessDTOList;
	}
	
	public void setKvPageProcessDTOs(final List<KVPageProcessDTO> kvPageProcessDTOs) {
		this.kvPageProcessDTOs = kvPageProcessDTOs;
	}
	public void addKVPageProcessDTO(final KVPageProcessDTO kvPageProcessDTO) {
		if(kvPageProcessDTOs == null) {
			kvPageProcessDTOs = new ArrayList<KVPageProcessDTO>();
		}
		kvPageProcessDTOs.add(kvPageProcessDTO);
	}
	
	public void removeKVPageProcessDTO(final KVPageProcessDTO kvPageProcessDTO) {
		if(kvPageProcessDTOs == null) {
			kvPageProcessDTOs = new ArrayList<KVPageProcessDTO>();
		}
		kvPageProcessDTOs.remove(kvPageProcessDTO);
	}
	
	public KVPageProcessDTO getKVPageProcessByKeyAndDataTypeAndLocation(final String keyPattern, final String valuePattern,
			final LocationType locationType) {
		final Collection<KVPageProcessDTO> kvPageProcessDTOList = kvPageProcessDTOs;
		KVPageProcessDTO pageProcessDTO = null;
		if (kvPageProcessDTOList != null && !kvPageProcessDTOList.isEmpty()) {
			for (final KVPageProcessDTO kvPageProcessDTO : kvPageProcessDTOList) {
				if (kvPageProcessDTO.getKeyPattern() != null && kvPageProcessDTO.getKeyPattern().equals(keyPattern)
						&& kvPageProcessDTO.getValuePattern() != null && kvPageProcessDTO.getValuePattern().equals(valuePattern)
						&& kvPageProcessDTO.getLocationType() != null
						&& kvPageProcessDTO.getLocationType().name().equals(locationType.name())) {
					pageProcessDTO = kvPageProcessDTO;
				}
			}
		}
		return pageProcessDTO;
	}

	public boolean checkKVPageProcessDetails(final String keyPattern, final String valuePattern, final LocationType locationType) {
		boolean validDetails = false;
		if (getKVPageProcessByKeyAndDataTypeAndLocation(keyPattern, valuePattern, locationType) != null){
			validDetails = true;
		}
		return validDetails;
	}

	public void setOrderNumber(final Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

}

/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.thirdparty.web;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class DataCustodianListController extends BaseController {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = Routes.THIRD_PARTY_DATA_CUSTODIAN_LIST, method = RequestMethod.GET)
	public String index(ModelMap model) throws JAXBException {
		List<Long> allIds = resourceService
				.findAllIds(ApplicationInformation.class);
		List<ApplicationInformation> applicationInformations = new ArrayList<ApplicationInformation>();
		for (Long id : allIds) {
			applicationInformations.add(resourceService.findById(id,
					ApplicationInformation.class));
		}
		model.put("applicationInformationList", applicationInformations);
		return "/RetailCustomer/DataCustodianList/index";
	}

	public void setApplicationInformationService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getApplicationInformationService() {
		return this.resourceService;
	}

}

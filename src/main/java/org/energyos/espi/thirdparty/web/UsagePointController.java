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

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UsagePointController extends BaseController {

	@Autowired
	private UsagePointService usagePointService;

	@Autowired
	private ApplicationInformationService applicationInformationService;

	@Autowired
	private ResourceService resourceService;

	@ModelAttribute
	public List<UsagePoint> usagePoints(Principal principal) {
		return usagePointService
				.findAllByRetailCustomer(currentCustomer(principal));
	}

	@RequestMapping(value = Routes.USAGE_POINT_INDEX, method = RequestMethod.GET)
	public String index(ModelMap model, Principal principal) {
		return "/customer/usagepoints/index";
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = Routes.USAGE_POINT_SHOW, method = RequestMethod.GET)
	public String show(@PathVariable Long retailCustomerId,
			@PathVariable Long usagePointId, ModelMap model) {
		try {

			resourceService.testById(usagePointId, UsagePoint.class);
			// because of the lazy loading from DB it's easier to build a bag
			// and hand it off
			// in a separate transaction, fill up a display bag lazily - do it
			// in a private method
			// so the transaction is scoped appropriately.

			HashMap<String, Object> displayBag = buildDisplayBag(
					retailCustomerId, usagePointId);

			model.put("displayBag", displayBag);

			return "/customer/usagepoints/show";
		} catch (Exception e) {

			// got to do a dummy DB access to satify the transaction rollback
			// needs ...
			resourceService.findById(1L, ApplicationInformation.class);
			System.out.printf("UX Error: %s\n", e.toString());
			model.put("errorString", e.toString());
			try {
				// try again (and maybe we can catch the rollback error ...
				return "/customer/error";
			} catch (Exception ex) {
				return "/customer/error";
			}
		}
	}

	/*
	 * @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class},
	 * noRollbackFor = {javax.persistence.NoResultException.class,
	 * org.springframework.dao.EmptyResultDataAccessException.class }) (readOnly
	 * = true)
	 * 
	 * @RequestMapping(value = Routes.USAGE_POINT_SHOW_TP, method =
	 * RequestMethod, produces = "application/atom+xml") @ResponseBody public
	 * String show(@PathVariable("UsagePointHashedId") String
	 * usagePointHashedId, ModelMap model, Principal principal) throws
	 * JAXBException { RetailCustomer currentCustomer =
	 * currentCustomer(principal); try {
	 * 
	 * UsagePoint usagePoint =
	 * usagePointRESTRepository.findByHashedId(currentCustomer.getId(),
	 * usagePointHashedId); // because of the lazy loading from DB it's easier
	 * to build a bag and hand it off // in a separate transaction, fill up a
	 * display bag lazily - do it in a private method // so the transaction is
	 * scoped appropriately.
	 * 
	 * HashMap<String, Object> displayBag =
	 * buildDisplayBag(usagePoint.getRetailCustomer().getId(),
	 * usagePoint.getId());
	 * 
	 * model.put("displayBag", displayBag);
	 * 
	 * return "/usagepoints/show";
	 * 
	 * } catch (Exception e) { System.out.printf("UX Error: %s\n",
	 * e.toString()); List<UsagePoint> usagePointList =
	 * usagePointRESTRepository.
	 * findAllByRetailCustomerId(currentCustomer.getId());
	 * model.put("usagePointList", usagePointList); return "/usagepoints/index";
	 * } }
	 */
	@Transactional(readOnly = true)
	private HashMap<String, Object> buildDisplayBag(Long retailCustomerId,
			Long usagePointId) {

		HashMap<String, Object> displayBag = new HashMap<String, Object>();
		UsagePoint usagePoint = resourceService.findById(usagePointId,
				UsagePoint.class);
		displayBag.put("Description", usagePoint.getDescription());
		displayBag.put("ServiceCategory", usagePoint.getServiceCategory());
		displayBag.put("Uri", usagePoint.getSelfHref());
		displayBag.put("usagePointId", usagePoint.getId());
		// put the meterReadings
		@SuppressWarnings("rawtypes")
		List<HashMap> meterReadings = new ArrayList<HashMap>();
		Iterator<MeterReading> it = usagePoint.getMeterReadings().iterator();
		while (it.hasNext()) {
			HashMap<String, Object> mrBag = new HashMap<String, Object>();
			MeterReading mr = it.next();
			mrBag.put("Description", mr.getDescription());
			// TODO remove the 1L assumption in ApplicationInformation
			String thirdPartyNotifyURI = resourceService.findById(1L,
					ApplicationInformation.class).getThirdPartyNotifyUri();
			String uriTail = "/RetailCustomer/" + retailCustomerId
					+ "/UsagePoint/" + usagePointId + "/MeterReading/"
					+ mr.getId() + "/show";
			mrBag.put("Uri",
					thirdPartyNotifyURI.replace("/espi/1_1/Notification", "")
							+ uriTail);
			mrBag.put("ReadingType", mr.getReadingType().getDescription());
			meterReadings.add(mrBag);
		}
		displayBag.put("MeterReadings", meterReadings);
		// find the summary rollups
		List<ElectricPowerQualitySummary> qualitySummaryList = usagePoint
				.getElectricPowerQualitySummaries();
		List<ElectricPowerUsageSummary> usageSummaryList = usagePoint
				.getElectricPowerUsageSummaries();
		displayBag.put("QualitySummaryList", qualitySummaryList);
		displayBag.put("UsageSummaryList", usageSummaryList);

		TimeConfiguration timeConfiguration = usagePoint
				.getLocalTimeParameters();
		displayBag.put("localTimeParameters", timeConfiguration);

		return displayBag;
	}

	/*
	 * public void setUsagePointRESTRepository(UsagePointRESTRepository
	 * usagePointRESTRepository) { this.usagePointRESTRepository =
	 * usagePointRESTRepository; }
	 */

	public void setApplicationInformationService(
			ApplicationInformationService applicationInformationService) {
		this.applicationInformationService = applicationInformationService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
}

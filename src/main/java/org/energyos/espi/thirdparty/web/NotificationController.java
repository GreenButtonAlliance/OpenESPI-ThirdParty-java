/*
 * Copyright 2013 EnergyOS.org
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

import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.thirdparty.domain.BatchList;
import org.energyos.espi.thirdparty.service.BatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class NotificationController extends BaseController {

    @Autowired
    private BatchListService batchListService;

    @Autowired
    public Jaxb2Marshaller marshaller;

    @RequestMapping(value = Routes.THIRD_PARTY_NOTIFICATION, method = RequestMethod.POST)
    public void notification(HttpServletResponse response, InputStream inputStream) throws IOException {
        BatchList batchList = (BatchList)marshaller.unmarshal(new StreamSource(inputStream));
        batchListService.persist(batchList);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void setBatchListService(BatchListService batchListService) {
        this.batchListService = batchListService;
    }

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }
}

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

import org.energyos.espi.thirdparty.domain.BatchList;
import org.energyos.espi.thirdparty.service.UpdateService;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NotificationControllerTests {

    @Test
    public void notification_updatesResources() throws Exception {
        NotificationController controller = new NotificationController();
        UpdateService updateService = mock(UpdateService.class);
        controller.setUpdateService(updateService);

        BatchList batchList = new BatchList();
        batchList.getResourceURIs().add("resourceURI1");
        batchList.getResourceURIs().add("resourceURI2");

        controller.notification(batchList);

        verify(updateService).update("resourceURI1");
        verify(updateService).update("resourceURI2");
    }
}

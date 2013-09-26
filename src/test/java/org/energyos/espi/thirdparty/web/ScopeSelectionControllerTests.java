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

import org.energyos.espi.thirdparty.domain.Configuration;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScopeSelectionControllerTests {

    @Test
    public void post_scopeSelection_redirects() throws Exception {
        String url = "DataCustodianURL";

        ScopeSelectionController controller = new ScopeSelectionController();

        String redirectURL = controller.scopeSelection("1", 1L, url);

        assertEquals(String.format("redirect:%s?scope=%s&scope=%s&ThirdPartyID=%s", url, Configuration.SCOPES[0], Configuration.SCOPES[1],
                Configuration.THIRD_PARTY_CLIENT_ID), redirectURL);
    }

    @Test
    public void get_scopeSelection_displaysScopeSelectionView() throws Exception {
        ScopeSelectionController controller = new ScopeSelectionController();

        assertEquals("/RetailCustomer/ScopeSelection", controller.scopeSelection(new String [] {}, new ModelMap()));
    }

    @Test
    public void get_scopeSelection_setsScopeListModel() throws Exception {
        ScopeSelectionController controller = new ScopeSelectionController();

        ModelMap model = new ModelMap();

        controller.scopeSelection(new String [] {"scope1", "scope2"}, model);

        assertTrue(((List<String>)model.get("scopeList")).size() > 0);
    }
}

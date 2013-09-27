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

package org.energyos.espi.thirdparty.utils;

import org.energyos.espi.thirdparty.domain.IdentifiedObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

public class EspiMarshaller {

    private EspiMarshaller() {
    }

    private static JAXBContext jaxbContext;
    private static Unmarshaller unmarshaller;

    public static <T extends IdentifiedObject> JAXBElement<T> unmarshal(String xml) throws JAXBException {
        return (JAXBElement<T>) getUnmarshaller().unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }

    private static Unmarshaller getUnmarshaller() throws JAXBException {
        if (unmarshaller == null) {
            JAXBContext jaxbContext = getJaxbContext();
            unmarshaller = jaxbContext.createUnmarshaller();
        }

        return unmarshaller;
    }

    private static JAXBContext getJaxbContext() throws JAXBException {
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance("org.energyos.espi.thirdparty.domain");
        }

        return jaxbContext;
    }
}

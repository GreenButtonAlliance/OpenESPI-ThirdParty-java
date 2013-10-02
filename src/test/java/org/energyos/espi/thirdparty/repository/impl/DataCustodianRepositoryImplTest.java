package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.repository.DataCustodianRepository;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class DataCustodianRepositoryImplTest {

    @Autowired
    DataCustodianRepository repository;

    @Test
    @Transactional
    public void findById() throws Exception {
        DataCustodian dataCustodian = new DataCustodian();
        dataCustodian.setDescription("Description");
        dataCustodian.setUrl("URL");
        repository.persist(dataCustodian);

        assertEquals(dataCustodian.getId(), repository.findById(dataCustodian.getId()).getId());
    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        repository.persist(dataCustodian);

        assertEquals(2, repository.findAll().size());
    }
}

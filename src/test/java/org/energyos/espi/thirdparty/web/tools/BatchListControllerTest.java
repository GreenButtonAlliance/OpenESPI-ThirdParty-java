package org.energyos.espi.thirdparty.web.tools;

import static org.energyos.espi.common.test.EspiFactory.newBatchList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.service.BatchListService;
import org.energyos.espi.thirdparty.BaseTest;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ui.ModelMap;

public class BatchListControllerTest extends BaseTest {
    private MockHttpServletResponse response = new MockHttpServletResponse();

    BatchListController controller;

    @Mock
    private BatchListService batchListService;

    @Mock
    Jaxb2Marshaller marshaller;

    @Test
    public void index() throws IOException {
        controller = new BatchListController();
        List<BatchList> batchListList = new ArrayList<>();
        batchListList.add(newBatchList());

        when(batchListService.findAll()).thenReturn(batchListList);
        controller.setBatchListService(batchListService);

        controller.index(mock(ModelMap.class));

        verify(batchListService).findAll();
    }
}

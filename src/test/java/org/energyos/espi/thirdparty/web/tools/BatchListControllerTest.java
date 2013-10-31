package org.energyos.espi.thirdparty.web.tools;

import org.energyos.espi.thirdparty.BaseTest;
import org.energyos.espi.thirdparty.domain.BatchList;
import org.energyos.espi.thirdparty.service.BatchListService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.energyos.espi.thirdparty.utils.factories.EspiFactory.newBatchList;
import static org.mockito.Mockito.*;

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

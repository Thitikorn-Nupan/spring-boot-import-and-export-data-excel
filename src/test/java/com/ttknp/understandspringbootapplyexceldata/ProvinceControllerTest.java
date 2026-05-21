package com.ttknp.understandspringbootapplyexceldata;

import com.ttknp.understandspringbootapplyexceldata.controllers.ProvinceController;
import com.ttknp.understandspringbootapplyexceldata.entities.Province;
import com.ttknp.understandspringbootapplyexceldata.services.ProvinceService;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
// Mockito: mock(Class) creates a mock instance of the given type. Use it to stub method calls and/or verify interactions.
import static org.mockito.Mockito.mock;
// Mockito: when(...) is used to define a stubbed behavior for a call on a mock; pair with thenReturn(...) to provide the response.
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProvinceControllerTest {
    @Test
    void reads_shouldReturnList() throws Exception {
        // Create a mock ProvinceService. No real logic runs; calls can be stubbed below.
        ProvinceService mockService = mock(ProvinceService.class);
        // Stub the mock: when getProvincesFromExcel() is called on the mock, return the provided list.
        when(mockService.getProvincesFromExcel())
                .thenReturn(List.of(new Province(10, "Bangkok", "กรุงเทพมหานคร", 1000000L, 1568)));

        // Construct the controller under test (real instance).
        ProvinceController controller = new ProvinceController();

        // Inject the mock into the controller's private field named "provinceService" using reflection.
        // This avoids needing a Spring context and lets the controller use the mocked service.
        ReflectionTestUtils.setField(controller, "provinceService", mockService);

        // Build MockMvc for standalone controller testing (no Spring context required).
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Perform GET /api/province/reads and assert HTTP 200 and that the first element's nameEn is "Bangkok".
        mvc.perform(get("/api/province/reads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nameEn").value("Bangkok"));
    }

    @Test
    void save_shouldReturnTrue() throws Exception {
        // Create another mock ProvinceService for this test.
        ProvinceService mockService = mock(ProvinceService.class);
        // Stub the saveProvincesToExcelAbsOrRootPath(...) call to return true when invoked with the given path.
        when(mockService.saveProvincesToExcelAbsOrRootPath("src/main/resources/excel/provinces_of_thailand_v2.xlsx"))
                .thenReturn(true);

        ProvinceController controller = new ProvinceController();
        // Inject the mock so the controller will call the stubbed method instead of real IO.
        ReflectionTestUtils.setField(controller, "provinceService", mockService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Perform GET /api/province/save and expect the body to be the string "true" and HTTP 200.
        mvc.perform(get("/api/province/save"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

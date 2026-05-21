package com.ttknp.understandspringbootapplyexceldata;

import com.ttknp.understandspringbootapplyexceldata.controllers.TeacherController;
import com.ttknp.understandspringbootapplyexceldata.entities.Teacher;
import com.ttknp.understandspringbootapplyexceldata.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Date;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeacherControllerTest {

    @Test
    void reads_shouldReturnList() throws Exception {
        TeacherService mockService = mock(TeacherService.class);
        when(mockService.readTeachersFormExcelAbsOrRootPath(anyString()))
                .thenReturn(List.of(new Teacher(5, "Bob", 30, "m", 1200.0, new Date())));

        TeacherController controller = new TeacherController();
        ReflectionTestUtils.setField(controller, "teacherService", mockService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/teacher/reads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob"));
    }

    @Test
    void save_shouldReturnTrue() throws Exception {
        TeacherService mockService = mock(TeacherService.class);
        when(mockService.saveTeacherToExcelAbsOrRootPath(anyString())).thenReturn(true);

        TeacherController controller = new TeacherController();
        ReflectionTestUtils.setField(controller, "teacherService", mockService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/teacher/save"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

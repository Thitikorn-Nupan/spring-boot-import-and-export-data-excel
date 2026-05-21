package com.ttknp.understandspringbootapplyexceldata;

import com.ttknp.understandspringbootapplyexceldata.controllers.StudentController;
import com.ttknp.understandspringbootapplyexceldata.entities.Student;
import com.ttknp.understandspringbootapplyexceldata.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest {

    @Test
    void reads_shouldReturnList() throws Exception {
        StudentService mockService = mock(StudentService.class);
        when(mockService.readStudentsFormExcelAbsOrRootPath(anyString()))
                .thenReturn(List.of(new Student(1, "Alice", 20, "f")));

        // instantiate controller and inject mock service
        StudentController controller = new StudentController();
        ReflectionTestUtils.setField(controller, "studentService", mockService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/student/reads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void save_shouldReturnTrue() throws Exception {
        StudentService mockService = mock(StudentService.class);
        when(mockService.saveStudentToExcelAbsOrRootPath(anyString())).thenReturn(true);

        StudentController controller = new StudentController();
        ReflectionTestUtils.setField(controller, "studentService", mockService);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/student/save"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

package org.oth.apppractice;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.oth.apppractice.DTO.UserDTO;
import org.oth.apppractice.Exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class, GlobalExceptionHandler.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private UserController userController;

    @MockitoBean
    private UserService userService;

    /**
     * Test {@link UserController#getUsers()}.
     *
     * <p>Method under test: {@link UserController#getUsers()}
     */
    @Test
    @DisplayName("Test getUsers()")
    @Tag("MaintainedByDiffblue")
    void testGetUsers() throws Exception {
        // Arrange
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/User");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    /**
     * Test {@link UserController#getUserById(Long)}.
     *
     * <p>Method under test: {@link UserController#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long)")
    @Tag("MaintainedByDiffblue")
    void testGetUserById() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(1L, "Name", "jane.doe@example.org", 1);
        when(userService.findById(Mockito.<Long>any())).thenReturn(userDTO);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/User/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(
                        content()
                                .string(
                                        "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"age\":1}"));
    }

    /**
     * Test {@link UserController#addUser(UserDTO)}.
     *
     * <p>Method under test: {@link UserController#addUser(UserDTO)}
     */
    @Test
    @DisplayName("Test addUser(UserDTO)")
    @Tag("MaintainedByDiffblue")
    void testAddUser() throws Exception {
        // Arrange
        doNothing().when(userService).saveUser(Mockito.<UserDTO>any());

        MockHttpServletRequestBuilder contentTypeResult =
                MockMvcRequestBuilders.post("/api/v1/User").contentType(MediaType.APPLICATION_JSON);

        JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
        UserDTO userDTO = new UserDTO(1L, "Name", "jane.doe@example.org", 1);
        String content = jsonMapper.writeValueAsString(userDTO);

        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }

    /**
     * Test {@link UserController#deleteUser(Long)}.
     *
     * <p>Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long)")
    @Tag("MaintainedByDiffblue")
    void testDeleteUser() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(Mockito.<Long>any());

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/api/v1/User/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    /**
     * Test {@link UserController#updateUser(Long, String, String)}.
     *
     * <p>Method under test: {@link UserController#updateUser(Long, String, String)}
     */
    @Test
    @DisplayName("Test updateUser(Long, String, String)")
    @Tag("MaintainedByDiffblue")
    void testUpdateUser() throws Exception {
        // Arrange
        doNothing()
                .when(userService)
                .updateUser(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/api/v1/User/update/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }
}

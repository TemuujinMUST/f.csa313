package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseRegistrationServiceTest {

    private static final String COURSE_ID = "F.CSA313";
    private static final String PREREQ_TAKEN = "F.CSA201";
    private static final String PREREQ_MISSING = "F.CSA202";

    private static final Student ACTIVE = new Student("S100", "s100@must.edu.mn", true, List.of(PREREQ_TAKEN));
    private static final Student INACTIVE = new Student("S200", "s200@must.edu.mn", false, List.of(PREREQ_TAKEN));

    @Mock
    StudentRepository repository;
    @Mock
    EmailSender emailSender;
    CourseRegistrationService service;

    @BeforeEach
    void setUp() {
        service = new CourseRegistrationService(repository, emailSender);
    }

    // A1
    @Test
    void register_activeStudentWithPrerequisites_savesAndSendsEmail() {
        when(repository.findStudent("S100")).thenReturn(ACTIVE);
        when(repository.courseExists(COURSE_ID)).thenReturn(true);
        when(repository.prerequisites(COURSE_ID)).thenReturn(List.of(PREREQ_TAKEN));

        String result = service.register("S100", COURSE_ID);

        assertEquals("OK", result);
        verify(repository).saveRegistration("S100", COURSE_ID);
        verify(emailSender).send(eq("s100@must.edu.mn"), anyString(), contains(COURSE_ID));
    }

    // A2
    @Test
    void register_unknownStudent_returnsErrorWithoutSavingOrEmailing() {
        when(repository.findStudent("S404")).thenReturn(null);

        String result = service.register("S404", COURSE_ID);

        assertEquals("ERROR_NO_STUDENT", result);
        verify(repository, never()).saveRegistration(anyString(), anyString());
        verify(emailSender, never()).send(anyString(), anyString(), anyString());
    }

    // A3
    @Test
    void register_inactiveStudent_returnsErrorWithoutTouchingEmail() {
        when(repository.findStudent("S200")).thenReturn(INACTIVE);

        String result = service.register("S200", COURSE_ID);

        assertEquals("ERROR_INACTIVE_STUDENT", result);
        verify(repository, never()).saveRegistration(anyString(), anyString());
        verifyNoInteractions(emailSender);
    }

    // A4
    @Test
    void register_missingOnePrerequisite_returnsErrorWithoutSaving() {
        when(repository.findStudent("S100")).thenReturn(ACTIVE);
        when(repository.courseExists(COURSE_ID)).thenReturn(true);
        when(repository.prerequisites(COURSE_ID)).thenReturn(List.of(PREREQ_TAKEN, PREREQ_MISSING));

        String result = service.register("S100", COURSE_ID);

        assertEquals("ERROR_PREREQUISITES", result);
        verify(repository, never()).saveRegistration(anyString(), anyString());
        verifyNoInteractions(emailSender);
    }

    // A5
    @Test
    void register_emailSenderThrows_stillReturnsOkAndSaves() {
        when(repository.findStudent("S100")).thenReturn(ACTIVE);
        when(repository.courseExists(COURSE_ID)).thenReturn(true);
        doThrow(new RuntimeException("SMTP down"))
                .when(emailSender).send(anyString(), anyString(), anyString());

        String result = service.register("S100", COURSE_ID);

        assertEquals("OK", result);
        verify(repository).saveRegistration("S100", COURSE_ID);
    }

    // A6
    @Test
    void register_nullArgument_throwsWithoutTouchingDependencies() {
        assertThrows(IllegalArgumentException.class, () -> service.register(null, COURSE_ID));
        assertThrows(IllegalArgumentException.class, () -> service.register("S100", null));

        verifyNoInteractions(repository, emailSender);
    }

    // A7
    @Test
    void register_success_emailBodyContainsCourseId() {
        when(repository.findStudent("S100")).thenReturn(ACTIVE);
        when(repository.courseExists(COURSE_ID)).thenReturn(true);

        service.register("S100", COURSE_ID);

        ArgumentCaptor<String> body = ArgumentCaptor.forClass(String.class);
        verify(emailSender).send(eq("s100@must.edu.mn"), anyString(), body.capture());
        assertTrue(body.getValue().contains(COURSE_ID), "и-мэйлийн бие: " + body.getValue());
    }

    // A8
    @ParameterizedTest(name = "{0}, {1} -> {2}")
    @CsvSource({
            "S404, F.CSA313, ERROR_NO_STUDENT",
            "S200, F.CSA313, ERROR_INACTIVE_STUDENT",
            "S100, F.NONE,   ERROR_NO_COURSE",
    })
    void register_invalidRequest_returnsExpectedErrorWithoutSavingOrEmailing(
            String studentId, String courseId, String expected) {
        lenient().when(repository.findStudent("S404")).thenReturn(null);
        lenient().when(repository.findStudent("S200")).thenReturn(INACTIVE);
        lenient().when(repository.findStudent("S100")).thenReturn(ACTIVE);
        lenient().when(repository.courseExists(COURSE_ID)).thenReturn(true);
        lenient().when(repository.courseExists("F.NONE")).thenReturn(false);

        String result = service.register(studentId, courseId);

        assertEquals(expected, result);
        verify(repository, never()).saveRegistration(anyString(), anyString());
        verifyNoInteractions(emailSender);
    }
}

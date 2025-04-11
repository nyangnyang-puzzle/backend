package nyang.puzzlebackend.api.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import nyang.puzzlebackend.global.error.ErrorContent;
import org.junit.jupiter.api.Test;

class ApiResponseTest {

    @Test
    void testSuccessWithNoData() {
        // Given & When
        ApiResponse<String> response = ApiResponse.ok("data");
        
        // Then
        assertEquals("success", response.result());
        assertNotNull(response.data());
        assertNull(response.error());
    }
    
    @Test
    void testSuccessWithData() {
        // Given
        String testData = "Test data";
        
        // When
        ApiResponse<String> response = ApiResponse.ok(testData);
        
        // Then
        assertEquals("success", response.result());
        assertEquals(testData, response.data());
        assertNull(response.error());
    }
    
    @Test
    void testError() {
        // Given
        ErrorContent errorContent = new ErrorContent("E001", "Test error message");
        
        // When
        ApiResponse<String> response = ApiResponse.error(errorContent);
        
        // Then
        assertEquals("error", response.result());
        assertNull(response.data());
        assertEquals(errorContent, response.error());
        assertEquals("E001", response.error().code());
        assertEquals("Test error message", response.error().message());
    }
}
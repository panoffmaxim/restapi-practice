package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.dto.ClientRequestDto;
import com.epam.laboratory.restapipractice.dto.ClientResponseDto;
import com.epam.laboratory.restapipractice.service.ClientCacheService;
import com.epam.laboratory.restapipractice.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @InjectMocks
    ClientController clientController;
    @Mock
    private ClientService clientService;
    @Mock
    private ClientCacheService clientCacheService;
    @Mock
    private MessageSource messageSource;

//    @Test
//    public void testCreateClient() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//        ClientRequestDto clientRequestDto = new ClientRequestDto();
//        clientRequestDto.setClientName("Jon");
//        clientRequestDto.setPhone("555-666");
//        ClientResponseDto expectedResponse = new ClientResponseDto();
//        expectedResponse.setId(1L);
//        expectedResponse.setClientName("Jon");
//        expectedResponse.setPhone("555-666");
//
//        when(clientService.registration(any(ClientRequestDto.class))).thenReturn(expectedResponse);
//
//        ResponseEntity<ClientResponseDto> responseEntity = clientController.create(clientRequestDto);
//
//        verify(clientCacheService, atLeastOnce()).deleteAllClientsFromCache();
//        verify(clientService, times(1)).registration(clientRequestDto);
//        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
//        assert responseEntity.getBody() == expectedResponse;
//    }

    @Test
    public void testReadClient() {
        Long clientId = 1L;
        when(clientService.getClient(clientId)).thenReturn(new ClientResponseDto(1L, "John", "555-666"));
        ResponseEntity<ClientResponseDto> response = clientController.read(clientId);
        verify(clientService, times(1)).getClient(clientId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getId() == 1L;
        assert response.getBody().getClientName().equals("John");
    }

    @Test
    public void testUpdateClient() {
        Long clientId = 1L;
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        ClientResponseDto updatedClientResponse = new ClientResponseDto();
        when(clientService.updateClient(clientRequestDto)).thenReturn(updatedClientResponse);
        ResponseEntity<ClientResponseDto> response = clientController.update(clientId, clientRequestDto);
        verify(clientService, times(1)).updateClient(clientRequestDto);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == updatedClientResponse;
    }

//    @Test
//    public void testDeleteClient() {
//        Long clientId = 1L;
//        doNothing().when(clientService).deleteClient(clientId);
//        ResponseEntity<Void> response = clientController.delete(clientId);
//        verify(clientService, times(1)).deleteClient(clientId);
//        verify(clientCacheService, times(1)).deleteAllClientsFromCache();
//        assert response.getStatusCode() == HttpStatus.OK;
//    }
}

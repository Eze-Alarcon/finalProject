package org.ezequiel.proyectofinal.features.sales.service.impl;

import org.ezequiel.proyectofinal.core.exceptions.ResourceNotFoundException;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperRequestDTO;
import org.ezequiel.proyectofinal.features.sales.dto.ShipperResponseDTO;
import org.ezequiel.proyectofinal.features.sales.entity.Shipper;
import org.ezequiel.proyectofinal.features.sales.mapper.ShipperMapper;
import org.ezequiel.proyectofinal.features.sales.repository.ShipperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipperServiceImplTest {

    @Mock
    private ShipperRepository shipperRepository;

    @Mock
    private ShipperMapper shipperMapper;

    @InjectMocks
    private ShipperServiceImpl shipperService;

    // --- findAll ---

    @Test
    void findAll_ShouldReturnListOfShippers() {
        // Arrange (Given)
        Shipper shipper = new Shipper((short) 1, "Speedy Express", "(503) 555-9831");
        ShipperResponseDTO responseDTO = new ShipperResponseDTO((short) 1, "Speedy Express", "(503) 555-9831");

        when(shipperRepository.findAll()).thenReturn(List.of(shipper));
        when(shipperMapper.toResponseDTO(shipper)).thenReturn(responseDTO);

        // Act (When)
        List<ShipperResponseDTO> result = shipperService.findAll();

        // Assert (Then)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Speedy Express", result.get(0).getCompanyName());
        verify(shipperRepository).findAll();
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnShipper() {
        // Arrange (Given)
        short id = 1;
        Shipper shipper = new Shipper(id, "Speedy Express", "(503) 555-9831");
        ShipperResponseDTO responseDTO = new ShipperResponseDTO(id, "Speedy Express", "(503) 555-9831");

        when(shipperRepository.findById(id)).thenReturn(Optional.of(shipper));
        when(shipperMapper.toResponseDTO(shipper)).thenReturn(responseDTO);

        // Act (When)
        ShipperResponseDTO result = shipperService.findById(id);

        // Assert (Then)
        assertNotNull(result);
        assertEquals(id, result.getShipperId());
        verify(shipperRepository).findById(id);
    }

    @Test
    void findById_WhenNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange (Given)
        short id = 99;
        when(shipperRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert (When & Then)
        assertThrows(ResourceNotFoundException.class, () -> shipperService.findById(id));
        verify(shipperRepository).findById(id);
    }

    // --- save ---

    @Test
    void save_ShouldSaveAndReturnShipper() {
        // Arrange (Given)
        ShipperRequestDTO requestDTO = new ShipperRequestDTO("Speedy Express", "(503) 555-9831");
        Shipper shipper = new Shipper(null, "Speedy Express", "(503) 555-9831");
        Shipper savedShipper = new Shipper((short) 1, "Speedy Express", "(503) 555-9831");
        ShipperResponseDTO responseDTO = new ShipperResponseDTO((short) 1, "Speedy Express", "(503) 555-9831");

        when(shipperMapper.toEntity(requestDTO)).thenReturn(shipper);
        when(shipperRepository.save(shipper)).thenReturn(savedShipper);
        when(shipperMapper.toResponseDTO(savedShipper)).thenReturn(responseDTO);

        // Act (When)
        ShipperResponseDTO result = shipperService.save(requestDTO);

        // Assert (Then)
        assertNotNull(result);
        assertEquals((short) 1, result.getShipperId());
        verify(shipperRepository).save(shipper);
    }

    // --- update ---

    @Test
    void update_WhenExists_ShouldUpdateAndReturnShipper() {
        // Arrange (Given)
        short id = 1;
        ShipperRequestDTO requestDTO = new ShipperRequestDTO("Updated Name", "(503) 555-9831");
        Shipper existingShipper = new Shipper(id, "Old Name", "(503) 555-9831");
        Shipper updatedShipper = new Shipper(id, "Updated Name", "(503) 555-9831");
        ShipperResponseDTO responseDTO = new ShipperResponseDTO(id, "Updated Name", "(503) 555-9831");

        when(shipperRepository.findById(id)).thenReturn(Optional.of(existingShipper));
        when(shipperRepository.save(existingShipper)).thenReturn(updatedShipper);
        when(shipperMapper.toResponseDTO(updatedShipper)).thenReturn(responseDTO);

        // Act (When)
        ShipperResponseDTO result = shipperService.update(id, requestDTO);

        // Assert (Then)
        assertNotNull(result);
        assertEquals("Updated Name", result.getCompanyName());
        verify(shipperMapper).updateEntityFromDTO(requestDTO, existingShipper);
        verify(shipperRepository).save(existingShipper);
    }

    @Test
    void update_WhenNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange (Given)
        short id = 99;
        ShipperRequestDTO requestDTO = new ShipperRequestDTO("Updated Name", "(503) 555-9831");
        when(shipperRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert (When & Then)
        assertThrows(ResourceNotFoundException.class, () -> shipperService.update(id, requestDTO));
        verify(shipperRepository, never()).save(any());
    }

    // --- delete ---

    @Test
    void delete_WhenExists_ShouldDeleteShipper() {
        // Arrange (Given)
        short id = 1;
        when(shipperRepository.existsById(id)).thenReturn(true);

        // Act (When)
        shipperService.delete(id);

        // Assert (Then)
        verify(shipperRepository).deleteById(id);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange (Given)
        short id = 99;
        when(shipperRepository.existsById(id)).thenReturn(false);

        // Act & Assert (When & Then)
        assertThrows(ResourceNotFoundException.class, () -> shipperService.delete(id));
        verify(shipperRepository, never()).deleteById(id);
    }
}

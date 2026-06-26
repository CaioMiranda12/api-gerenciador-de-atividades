package com.caiocesar.gerenciador_de_atividades.service;

import com.caiocesar.gerenciador_de_atividades.dto.group.CreateGroupDTO;
import com.caiocesar.gerenciador_de_atividades.dto.group.GroupDTO;
import com.caiocesar.gerenciador_de_atividades.exception.ResourceNotFoundException;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void createGroup_shouldReturnGroupDTO() {
        CreateGroupDTO dto = new CreateGroupDTO("Grupo Teste");

        Group savedGroup = new Group();
        savedGroup.setId(1L);
        savedGroup.setName("Grupo Teste");
        savedGroup.setActivities(new ArrayList<>());

        when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);

        GroupDTO result = groupService.createGroup(dto);

        assertNotNull(result);
        assertEquals("Grupo Teste", result.getName());
        assertEquals(1L, result.getId());
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    void getGroupById_shouldReturnGroupDTO_whenExists() {
        Group group = new Group();
        group.setId(1L);
        group.setName("Test group");
        group.setActivities(new ArrayList<>());

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        GroupDTO result = groupService.getGroupById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Grupo Teste", result.getName());
    }

    @Test
    void getGroupById_shouldThrowException_whenNotFound() {
        when(groupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupService.getGroupById(99L));
    }

    @Test
    void deleteGroup_shouldThrowException_whenNotFound() {
        when(groupRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> groupService.deleteGroup(99L));
        verify(groupRepository, never()).deleteById(any());
    }

    @Test
    void deleteGroup_shouldDelete_whenExists() {
        when(groupRepository.existsById(1L)).thenReturn(true);

        groupService.deleteGroup(1L);

        verify(groupRepository, times(1)).deleteById(1L);
    }
}
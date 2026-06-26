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
    void createGroup_deveRetornarGroupDTO() {
        CreateGroupDTO dto = new CreateGroupDTO("Grupo Teste");

        Group grupoSalvo = new Group();
        grupoSalvo.setId(1L);
        grupoSalvo.setName("Grupo Teste");
        grupoSalvo.setActivities(new ArrayList<>());

        when(groupRepository.save(any(Group.class))).thenReturn(grupoSalvo);

        GroupDTO resultado = groupService.createGroup(dto);

        assertNotNull(resultado);
        assertEquals("Grupo Teste", resultado.getName());
        assertEquals(1L, resultado.getId());
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    void getGroupById_deveRetornarGroupDTO_quandoExistir() {
        Group grupo = new Group();
        grupo.setId(1L);
        grupo.setName("Grupo Teste");
        grupo.setActivities(new ArrayList<>());

        when(groupRepository.findById(1L)).thenReturn(Optional.of(grupo));

        GroupDTO resultado = groupService.getGroupById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Grupo Teste", resultado.getName());
    }

    @Test
    void getGroupById_deveLancarExcecao_quandoNaoExistir() {
        when(groupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupService.getGroupById(99L));
    }

    @Test
    void deleteGroup_deveLancarExcecao_quandoNaoExistir() {
        when(groupRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> groupService.deleteGroup(99L));
        verify(groupRepository, never()).deleteById(any());
    }

    @Test
    void deleteGroup_deveDeletar_quandoExistir() {
        when(groupRepository.existsById(1L)).thenReturn(true);

        groupService.deleteGroup(1L);

        verify(groupRepository, times(1)).deleteById(1L);
    }
}
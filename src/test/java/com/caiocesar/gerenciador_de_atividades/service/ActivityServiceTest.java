package com.caiocesar.gerenciador_de_atividades.service;

import com.caiocesar.gerenciador_de_atividades.dto.activity.ActivityDTO;
import com.caiocesar.gerenciador_de_atividades.dto.activity.CreateActivityDTO;
import com.caiocesar.gerenciador_de_atividades.exception.ResourceNotFoundException;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Activity;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.ActivityRepository;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void createActivity_deveRetornarActivityDTO() {
        CreateActivityDTO dto = new CreateActivityDTO("Atividade Teste", LocalDate.now(), 1L);

        Group grupo = new Group();
        grupo.setId(1L);
        grupo.setName("Grupo Teste");
        grupo.setActivities(new ArrayList<>());

        Activity atividadeSalva = new Activity();
        atividadeSalva.setId(1L);
        atividadeSalva.setDescription("Atividade Teste");
        atividadeSalva.setDueDate(LocalDate.now());
        atividadeSalva.setCompleted(false);
        atividadeSalva.setGroup(grupo);
        atividadeSalva.setPosition(0);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(grupo));
        when(activityRepository.countByGroupId(1L)).thenReturn(0);
        when(activityRepository.save(any(Activity.class))).thenReturn(atividadeSalva);

        ActivityDTO resultado = activityService.createActivity(dto);

        assertNotNull(resultado);
        assertEquals("Atividade Teste", resultado.getDescription());
        assertEquals(1L, resultado.getGroupId());
        assertFalse(resultado.isCompleted());
    }

    @Test
    void createActivity_deveLancarExcecao_quandoGroupNaoExistir() {
        CreateActivityDTO dto = new CreateActivityDTO("Atividade Teste", LocalDate.now(), 99L);

        when(groupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> activityService.createActivity(dto));
        verify(activityRepository, never()).save(any());
    }

    @Test
    void deleteActivity_deveLancarExcecao_quandoNaoExistir() {
        when(activityRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> activityService.deleteActivity(99L));
        verify(activityRepository, never()).deleteById(any());
    }

    @Test
    void deleteActivity_deveDeletar_quandoExistir() {
        when(activityRepository.existsById(1L)).thenReturn(true);

        activityService.deleteActivity(1L);

        verify(activityRepository, times(1)).deleteById(1L);
    }
}
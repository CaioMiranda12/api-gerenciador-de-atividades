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
    void createActivity_shouldReturnActivityDTO() {
        CreateActivityDTO dto = new CreateActivityDTO("Test activity", LocalDate.now(), 1L);

        Group group = new Group();
        group.setId(1L);
        group.setName("Test group");
        group.setActivities(new ArrayList<>());

        Activity savedActivity = new Activity();
        savedActivity.setId(1L);
        savedActivity.setDescription("Test activity");
        savedActivity.setDueDate(LocalDate.now());
        savedActivity.setCompleted(false);
        savedActivity.setGroup(group);
        savedActivity.setPosition(0);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(activityRepository.countByGroupId(1L)).thenReturn(0);
        when(activityRepository.save(any(Activity.class))).thenReturn(savedActivity);

        ActivityDTO result = activityService.createActivity(dto);

        assertNotNull(result);
        assertEquals("Test activity", result.getDescription());
        assertEquals(1L, result.getGroupId());
        assertFalse(result.isCompleted());
    }

    @Test
    void createActivity_shouldThrowException_whenGroupNotFound() {
        CreateActivityDTO dto = new CreateActivityDTO("Test activity", LocalDate.now(), 99L);

        when(groupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> activityService.createActivity(dto));
        verify(activityRepository, never()).save(any());
    }

    @Test
    void deleteActivity_shouldThrowException_whenNotFound() {
        when(activityRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> activityService.deleteActivity(99L));
        verify(activityRepository, never()).deleteById(any());
    }

    @Test
    void deleteActivity_shouldDelete_whenExists() {
        when(activityRepository.existsById(1L)).thenReturn(true);

        activityService.deleteActivity(1L);

        verify(activityRepository, times(1)).deleteById(1L);
    }
}
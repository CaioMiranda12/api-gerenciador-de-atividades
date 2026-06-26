package com.caiocesar.gerenciador_de_atividades.controller;

import com.caiocesar.gerenciador_de_atividades.dto.activity.ActivityDTO;
import com.caiocesar.gerenciador_de_atividades.dto.activity.CreateActivityDTO;
import com.caiocesar.gerenciador_de_atividades.dto.activity.ReorderActivityDTO;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Activity;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.ActivityRepository;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import com.caiocesar.gerenciador_de_atividades.service.ActivityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityRepository activityRepository;
    private final GroupRepository groupRepository;

    @PostMapping
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody CreateActivityDTO createActivityDTO){
        ActivityDTO createdActivity = activityService.createActivity(createActivityDTO);
        return ResponseEntity.status(201).body(createdActivity);
    }

    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities(){
        List<ActivityDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activityDTO){
        ActivityDTO updatedActivity = activityService.updateActivity(id, activityDTO);
        return ResponseEntity.ok(updatedActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reorder")
    public void reorderActivities(@RequestBody List<ReorderActivityDTO> reorderedActivities) {
        for (ReorderActivityDTO dto : reorderedActivities) {
            Activity activity = activityRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

            if (dto.getGroupId() != null) {
                Group newGroup = groupRepository.findById(dto.getGroupId())
                        .orElseThrow(() -> new EntityNotFoundException("Group not found"));
                activity.setGroup(newGroup);
            }

            activity.setPosition(dto.getPosition());
            activityRepository.save(activity);
        }
    }
}

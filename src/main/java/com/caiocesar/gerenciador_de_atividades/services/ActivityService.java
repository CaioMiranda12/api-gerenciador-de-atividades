package com.caiocesar.gerenciador_de_atividades.services;

import com.caiocesar.gerenciador_de_atividades.dto.activity.ActivityDTO;
import com.caiocesar.gerenciador_de_atividades.dto.activity.CreateActivityDTO;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Activity;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.ActivityRepository;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final GroupRepository groupRepository;

    public ActivityDTO createActivity(CreateActivityDTO createActivityDTO){
        Group group = groupRepository.findById(createActivityDTO.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        int lastPosition = activityRepository.countByGroupId(group.getId());

        Activity activity = new Activity();
        activity.setDescription(createActivityDTO.getDescription());
        activity.setDueDate(createActivityDTO.getDueDate());
        activity.setCompleted(false);
        activity.setGroup(group);
        activity.setPosition(lastPosition);

        Activity savedActivity = activityRepository.save(activity);
        return new ActivityDTO(savedActivity);
    }

    public List<ActivityDTO> getAllActivities(){
        List<Activity> activities = activityRepository.findAll();
        List<ActivityDTO> activityDTOS = new ArrayList<>();

        activities.forEach(activity -> activityDTOS.add(new ActivityDTO(activity)));

        return activityDTOS;
    }

    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO){
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activityDTO.getDescription() != null){
            activity.setDescription(activityDTO.getDescription());
        }

        if(activityDTO.getDueDate() != null){
            activity.setDueDate(activityDTO.getDueDate());
        }

        activity.setCompleted(activityDTO.isCompleted());

        if(activityDTO.getGroupId() != null){
            if(activity.getGroup() == null || !activity.getGroup().getId().equals(activityDTO.getGroupId())){
                Group newGroup = groupRepository.findById(activityDTO.getGroupId())
                        .orElseThrow(() -> new EntityNotFoundException("New group not found"));

                activity.setGroup(newGroup);
            }
        }

        Activity updatedActivity = activityRepository.save(activity);
        return new ActivityDTO(updatedActivity);
    }

    public void deleteActivity(Long id){
        if(!activityRepository.existsById(id)){
            throw new EntityNotFoundException("Activity not found");
        }

        activityRepository.deleteById(id);
    }

    @Transactional
    public void reorderActivities(List<ActivityDTO> reorderedActivities) {
        for (ActivityDTO dto : reorderedActivities) {
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

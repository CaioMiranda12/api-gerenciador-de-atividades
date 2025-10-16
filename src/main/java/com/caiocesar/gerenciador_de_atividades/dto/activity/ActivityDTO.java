package com.caiocesar.gerenciador_de_atividades.dto.activity;

import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private Long groupId;
    private String groupName;
    private Integer position;

    public ActivityDTO(Activity activity){
        this.id = activity.getId();
        this.description = activity.getDescription();
        this.dueDate = activity.getDueDate();
        this.completed = activity.isCompleted();
        this.groupId = activity.getGroup().getId();
        this.groupName = activity.getGroup().getName();
        this.position = activity.getPosition();
    }

}

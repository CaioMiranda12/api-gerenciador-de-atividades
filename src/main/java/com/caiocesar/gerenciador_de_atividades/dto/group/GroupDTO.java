package com.caiocesar.gerenciador_de_atividades.dto.group;

import com.caiocesar.gerenciador_de_atividades.dto.activity.ActivityDTO;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private List<ActivityDTO> activities;

    public GroupDTO(Group group){
        this.id = group.getId();
        this.name = group.getName();
        this.activities = group.getActivities()
                .stream()
                .map(ActivityDTO::new)
                .collect(Collectors.toList());
    }
}

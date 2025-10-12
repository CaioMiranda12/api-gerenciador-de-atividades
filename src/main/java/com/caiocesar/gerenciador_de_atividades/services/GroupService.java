package com.caiocesar.gerenciador_de_atividades.services;

import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Group createGroup(String name){
        Group group = new Group();
        group.setName(name);
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id){
        if(!groupRepository.existsById(id)){
            throw new EntityNotFoundException("Group not found");
        }
        groupRepository.deleteById(id);
    }

    public Group updateGroup(Long id, String newName){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.setName(newName);
        return groupRepository.save(group);
    }

}

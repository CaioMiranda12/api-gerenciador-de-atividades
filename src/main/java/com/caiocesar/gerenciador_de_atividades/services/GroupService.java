package com.caiocesar.gerenciador_de_atividades.services;

import com.caiocesar.gerenciador_de_atividades.dto.group.CreateGroupDTO;
import com.caiocesar.gerenciador_de_atividades.dto.group.GroupDTO;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupDTO createGroup(CreateGroupDTO createGroupDTO){
        Group group = new Group();
        group.setName(createGroupDTO.getName());

        Group savedGroup = groupRepository.save(group);
        return new GroupDTO(savedGroup);
    }

    public List<GroupDTO> getAllGroups(){
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();

        groups.forEach(group -> groupDTOS.add(new GroupDTO(group)));

        return groupDTOS;

    }

    public GroupDTO updateGroup(Long id, CreateGroupDTO createGroupDTO){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        group.setName(createGroupDTO.getName());
        Group updatedGroup = groupRepository.save(group);
        return new GroupDTO(updatedGroup);
    }

    public void deleteGroup(Long id){
        if(!groupRepository.existsById(id)){
            throw new EntityNotFoundException("Group not found");
        }
        groupRepository.deleteById(id);
    }

    public GroupDTO getGroupById(Long id){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        return new GroupDTO(group);
    }
}

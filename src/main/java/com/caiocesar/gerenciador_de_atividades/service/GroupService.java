package com.caiocesar.gerenciador_de_atividades.service;

import com.caiocesar.gerenciador_de_atividades.dto.group.CreateGroupDTO;
import com.caiocesar.gerenciador_de_atividades.dto.group.GroupDTO;
import com.caiocesar.gerenciador_de_atividades.exception.ResourceNotFoundException;
import com.caiocesar.gerenciador_de_atividades.infrastructure.entity.Group;
import com.caiocesar.gerenciador_de_atividades.infrastructure.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(readOnly = true)
    public List<GroupDTO> getAllGroups(){
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();

        groups.forEach(group -> groupDTOS.add(new GroupDTO(group)));

        return groupDTOS;

    }

    public GroupDTO updateGroup(Long id, CreateGroupDTO createGroupDTO){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        group.setName(createGroupDTO.getName());
        Group updatedGroup = groupRepository.save(group);
        return new GroupDTO(updatedGroup);
    }

    public void deleteGroup(Long id){
        if(!groupRepository.existsById(id)){
            throw new ResourceNotFoundException("Group not found");
        }
        groupRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public GroupDTO getGroupById(Long id){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return new GroupDTO(group);
    }
}

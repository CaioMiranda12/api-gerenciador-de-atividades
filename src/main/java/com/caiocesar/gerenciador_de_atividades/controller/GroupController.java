package com.caiocesar.gerenciador_de_atividades.controller;

import com.caiocesar.gerenciador_de_atividades.dto.group.CreateGroupDTO;
import com.caiocesar.gerenciador_de_atividades.dto.group.GroupDTO;
import com.caiocesar.gerenciador_de_atividades.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupDTO createGroupDTO){
        GroupDTO newGroup = groupService.createGroup(createGroupDTO);
        return ResponseEntity.status(201).body(newGroup);
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups(){
        List<GroupDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody CreateGroupDTO createGroupDTO){
        GroupDTO updatedGroup = groupService.updateGroup(id, createGroupDTO);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id){
        GroupDTO group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

}

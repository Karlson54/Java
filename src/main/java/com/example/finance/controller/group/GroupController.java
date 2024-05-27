package com.example.finance.controller.group;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.finance.dto.group.GroupDTO;
import com.example.finance.service.group.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDTO> getAllGroups() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.findById(id);
        if (groupDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groupDTO);
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        GroupDTO createdGroup = groupService.create(groupDTO);
        return ResponseEntity.ok(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        GroupDTO updatedGroup = groupService.update(id, groupDTO);
        if (updatedGroup == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

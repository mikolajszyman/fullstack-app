package com.example.pasir_szymanowski_mikolaj.controller;

import com.example.pasir_szymanowski_mikolaj.dto.GroupDTO;
import com.example.pasir_szymanowski_mikolaj.model.Group;
import com.example.pasir_szymanowski_mikolaj.service.GroupService;
import com.example.pasir_szymanowski_mikolaj.service.GroupTransactionService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupGraphQLController {

    private final GroupService groupService;

    public GroupGraphQLController(GroupService groupService){
        this.groupService=groupService;
    }

    @QueryMapping
    public List<Group> groups(){
        return groupService.getAllGroups();
    }

    @MutationMapping
    public Group createGroup(@Valid @Argument GroupDTO groupDTO){
        return groupService.createGroup(groupDTO);
    }

    @MutationMapping
    public Boolean deleteGroup(@Argument Long id){
        groupService.deleteGroup(id);
        return true;
    }
}

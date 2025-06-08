package com.example.pasir_szymanowski_mikolaj.service;

import com.example.pasir_szymanowski_mikolaj.dto.MembershipDTO;
import com.example.pasir_szymanowski_mikolaj.model.Group;
import com.example.pasir_szymanowski_mikolaj.model.Membership;
import com.example.pasir_szymanowski_mikolaj.model.User;
import com.example.pasir_szymanowski_mikolaj.repository.GroupRepository;
import com.example.pasir_szymanowski_mikolaj.repository.MembershipRepository;
import com.example.pasir_szymanowski_mikolaj.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public MembershipService(MembershipRepository membershipRepository, GroupRepository groupRepository, UserRepository userRepository){
        this.membershipRepository=membershipRepository;
        this.groupRepository=groupRepository;
        this.userRepository=userRepository;
    }

    public List<Membership> getGroupMembers(Long groupId){
        return membershipRepository.findByGroupId(groupId);
    }

    public Membership addMember(MembershipDTO membershipDTO){
        User user = userRepository.findByEmail(membershipDTO.getUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleiono użytkownika o emailu: "+membershipDTO.getUserEmail()));
        Group group = groupRepository.findById(membershipDTO.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleiono grupy o id: "+membershipDTO.getGroupId()));

        boolean alreadyMember = membershipRepository.findByGroupId(group.getId()).stream()
                .anyMatch(membership -> membership.getUser().getId()==(user.getId()));

        if(alreadyMember){
            throw new IllegalArgumentException("Użytkownik jest już członkiem tej grupy.");
        }

        Membership membership = new Membership();
        membership.setUser(user);
        membership.setGroup(group);
        return membershipRepository.save(membership);
    }

    public void removeMember(Long membershipId){
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new EntityNotFoundException("Członkostwo nie istnieje."));
        User currentUser = getCurrentUser();
        User groupOwner = membership.getGroup().getOwner();

        if(!(currentUser.getId()==groupOwner.getId())){
            throw new SecurityException("Tylko włąściciel grupy może usuwać członków.");
        }

        membershipRepository.deleteById(membershipId);
    }

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkowniak: "+email));
    }
}

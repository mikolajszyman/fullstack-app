package com.example.pasir_szymanowski_mikolaj.service;

import com.example.pasir_szymanowski_mikolaj.dto.GroupTransactionDTO;
import com.example.pasir_szymanowski_mikolaj.model.*;
import com.example.pasir_szymanowski_mikolaj.repository.DebtRepository;
import com.example.pasir_szymanowski_mikolaj.repository.GroupRepository;
import com.example.pasir_szymanowski_mikolaj.repository.MembershipRepository;
import com.example.pasir_szymanowski_mikolaj.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupTransactionService {

    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final DebtRepository debtRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public GroupTransactionService(GroupRepository groupRepository, MembershipRepository membershipRepository, DebtRepository debtRepository, TransactionRepository transactionRepository, TransactionService transactionService){
        this.groupRepository=groupRepository;
        this.membershipRepository=membershipRepository;
        this.debtRepository=debtRepository;
        this.transactionRepository=transactionRepository;
        this.transactionService=transactionService;
    }

    public void addGroupTransaction(GroupTransactionDTO dto, User currentUser){
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono grupy o ID: "+dto.getGroupId()));

        List<Membership> members = membershipRepository.findByGroupId(group.getId());
        List<Long> selectedUserIds = dto.getSelectedUserIds();

        if(members.isEmpty()){
            throw new IllegalArgumentException("Brak członków w grupie!");
        }

        if(selectedUserIds == null || selectedUserIds.isEmpty()){
            throw new IllegalArgumentException("Nie wybrano żadnych użytkowników");
        }

        double amountPerUser = dto.getAmount() / selectedUserIds.size();

        Transaction transaction = new Transaction(
                dto.getAmount(),
                TransactionType.EXPENSE,
                "Transakcja grupowa",
                "Transakcja grupowa o nazwie "+dto.getTitle(),
                transactionService.getCurrentUser()
        );

        transactionRepository.save(transaction);

        for(Membership member : members){
            User debtor = member.getUser();
            if(!(debtor.getId()==currentUser.getId()) && selectedUserIds.contains(debtor.getId())){
                Debt debt = new Debt();
                debt.setDebtor(debtor);
                debt.setCreditor(currentUser);
                debt.setGroup(group);
                debt.setAmount(amountPerUser);
                debt.setTitle(dto.getTitle());
                debtRepository.save(debt);
            }
        }
    }
}

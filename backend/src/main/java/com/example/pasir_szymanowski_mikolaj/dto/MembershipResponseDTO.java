package com.example.pasir_szymanowski_mikolaj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResponseDTO {
    private Long id;
    private Long userId;
    private Long groupId;
    private String userEmail;
}

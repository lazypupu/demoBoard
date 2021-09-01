package com.example.demoboard.dto;

import com.example.demoboard.domain.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdate;
    private LocalDateTime modifiedate;

    public MemberEntity toEntity(){
        MemberEntity memberEntity = MemberEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .build();
        return memberEntity;
    }

    @Builder
    public MemberDto(Long id, String email, String password, LocalDateTime createdate, LocalDateTime modifiedate){
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdate = createdate;
        this.modifiedate = modifiedate;
    }
}

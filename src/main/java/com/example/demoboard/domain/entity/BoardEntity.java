package com.example.demoboard.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자의 접근권한 설정, 파라미터가 없는 기본 생성자를 추가
@Getter
@Entity //객체를 매핑할 엔티티라고 JPA에게 알려주는 역할을 하는 어노테이션
@Table(name = "createboard") //엔티티클래스와 매핑되는 테이블정보를 명시
public class BoardEntity extends TimeEntity{

    @Id // 테이블의 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키로 대체키를 사용할 떄 기본키 값 생성 전략 명시
    private Long id;

    @Column(length = 10, nullable = false) //컬럼 매핑
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder //빌더 패턴 클래스 생성(Setter)
    public BoardEntity(Long id, String title, String content, String writer){
        this.id=id;
        this.title=title;
        this.content=content;
        this.writer=writer;
    }
}

package com.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "likes")
@Getter @Setter
public class Like {

    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long likeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    @OneToMany(mappedBy = "like", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeBook> likeBooks = new ArrayList<>();

    
    //회원하고 연동해주는 creatLike 메서드 생성
    public static Like CreateLike(Member member){
        Like like = new Like();
        like.setMember(member);

        return like;
    }

}

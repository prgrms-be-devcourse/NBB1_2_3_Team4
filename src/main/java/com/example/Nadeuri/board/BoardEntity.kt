package com.example.Nadeuri.board;

import com.example.Nadeuri.comment.CommentEntity;
import com.example.Nadeuri.member.MemberEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "board")
@Entity
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", nullable = false)
    private Long id;

    //멤버 테이블 참조 FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // optional = FK(참조키)에 널 허용하지않음
    @JoinColumn(name = "member_no") // 다른 테이블의 컬럼명
    private MemberEntity member; //멤버 객체로 변경 필요

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @Column(name = "board_content", nullable = false)
    private String boardContent;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true) // 테이블 이름을 맵핑
    private List<CommentEntity> comments;  //한 게시물의 여러 댓글을 보여줌


    @Builder
    public BoardEntity(
            final Long id,
            final MemberEntity member,
            final String boardTitle,
            final String boardContent,
            final Category category,
            final String imageUrl,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt,
            final List<CommentEntity> comments
    ) {
        this.id = id;
        this.member = member;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.comments = comments;
    }

    public static BoardEntity create(
            final MemberEntity member,
            final String boardTitle,
            final String boardContent,
            final Category category,
            final String imageUrl
    ) {
        return BoardEntity.builder()
                .member(member)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .category(category)
                .imageUrl(imageUrl)
                .build();
    }

    public void update(
            final MemberEntity member,
            final String boardTitle,
            final String boardContent,
            final Category category,
            final String imageUrl
    ) {
        this.member = member;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void recordDeletion(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }
}

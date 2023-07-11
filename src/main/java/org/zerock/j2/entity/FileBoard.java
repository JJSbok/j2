package org.zerock.j2.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
public class FileBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;
    private String writer;


    // >> 20개 까지는 한꺼번에 처리 요청 BatchSize
    @BatchSize(size = 20)
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "board")
    @Builder.Default
    private List<FileBoardImage> images = new ArrayList<>();

    public void addImage(FileBoardImage boardImage) {

        boardImage.changeOrd(images.size());

        images.add(boardImage);

    }

    public void cleanImages() {


        images.clear();
    }
}

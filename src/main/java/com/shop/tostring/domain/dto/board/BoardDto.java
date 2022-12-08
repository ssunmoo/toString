package com.shop.tostring.domain.dto.board;

import com.shop.tostring.domain.entity.board.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BoardDto {

    private int bno;		    // 게시글 번호
    private String btitle;	    // 제목
    private String bcontent;	// 내용	[ 썸머노트 이용하여 사진/영상 대용량 추가 ]
    private int bview;			// 조회수 : 기본 값 0
    private int bstar;          // 별점
    private MultipartFile bfile;// 첨부파일 객체 [ 업로드용 ]
    private String bfilename;   // 첨부파일 [ 출력용 ]

    private int bcno;			// 게시판 카테고리 번호 FK
    private int mno;			// 작성자 FK

    // 엔티티로 변환
    public BoardEntity toBoardEntity(){
        return BoardEntity.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bview(this.bview)
                .bstar(this.bstar)
                .build();
    }




}

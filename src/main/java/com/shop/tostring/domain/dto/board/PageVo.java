package com.shop.tostring.domain.dto.board;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PageVo { // 출력 클래스

    private int totalPage; // 전체 페이지
    private int startBtn;  // 시작 버튼
    private int endBtn;    // 끝 버튼
    List<BoardDto> boardDtoList;

}

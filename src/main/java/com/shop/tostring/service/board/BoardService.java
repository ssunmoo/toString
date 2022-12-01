package com.shop.tostring.service.board;

import com.shop.tostring.domain.dto.board.BcategoryDto;
import com.shop.tostring.domain.dto.board.BoardDto;
import com.shop.tostring.domain.entity.board.BcategoryEntity;
import com.shop.tostring.domain.entity.board.BcategoryRepository;
import com.shop.tostring.domain.entity.board.BoardEntity;
import com.shop.tostring.domain.entity.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BcategoryRepository bcategoryRepository;

    @Autowired
    private HttpServletResponse response; // 리스폰 선언

    // 1. 카테고리 등록
    public boolean setBcategory(BcategoryDto bcategoryDto){
        BcategoryEntity entity = bcategoryRepository.save( bcategoryDto.toBcEntity() );
        System.out.println("카테고리등록 ");
        System.out.println(entity);
        if ( entity.getBcno() !=0 ){
            return true;
        }else {
            return false;
        }
    }

    // 2. 카테고리 출력
    public List<BcategoryDto> bcategorylist(){
        List<BcategoryEntity> entityList = bcategoryRepository.findAll();
        List<BcategoryDto> dtoList = new ArrayList<>();

        entityList.forEach( b -> dtoList.add(b.tobcBcDto()));
        return dtoList;
    }


    // [ 첨부파일 업로드 경로 ]
    String path = "C:\\Users\\504\\IdeaProjects\\toString\\src\\main\\resources\\static\\upload\\";

    // 첨부 파일 업로드 메소드
    @Transactional
    public boolean fileupload( BoardDto boardDto , BoardEntity boardEntity ) {
        if ( boardDto.getBfile() != null ) {  // ** 첨부파일 있을때
            
            // 업로드 파일의 중복 방지를 위해 난수 발생 UUID 사용
            String uuid = UUID.randomUUID().toString();
            // 파일 이름 지정
            String filename = uuid + "_" + boardDto.getBfile().getOriginalFilename(); // .getOriginalFilename() 파일이름 가져오는 메소드
            // 파일명 DB 등록
            boardEntity.setBfile(filename);

            try {
                File uploadfile = new File(path + filename);
                boardDto.getBfile().transferTo(uploadfile); // .transferTo() 지정한 경로에 파일을 생성하기
            } catch (IOException e) {
                System.out.println("첨부파일 업로드 실패" + e);
            }
            return true;
        } else {
            return false;
        }
    }

//    // 첨부파일 다운로드
//    public void filedownload( String filename ){
//
//        // UUID 제거
//        String realfilename = "";
//        String [] split = filename.split("_"); // 1. 앞 언더바(_) 기준으로 자르기
//        for( int i = 1; i < split.length; i++ ){     // 2. uuid 제와한 반복문 돌리기 1번째 인덱스부터
//            realfilename += split[i];                // 3. 뒷자리 문자열 추가
//            if ( split.length-1 != i ){              // 4. 마지막 인덱스가 아니면
//                realfilename += "_";                 // 5. 문자열[1]_문자열[2]_문자열[3]
//            }
//        }
//
//        // 등록한 경로 찾기
//        String filepath = path + filename;
//
//        try {
//            response.setHeader(
//                    "Content-Disposition",  // 다운로드 형식
//                    "attachment;filename=" + URLEncoder.encode( realfilename, "UTF-8")); // 다운로드에 표시된 파일명
//
//            File file = new File( filepath ); // 등록된 경로의 파일 객체화
//
//            // 입력 스트림 객체
//            BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( file ) );
//            // 파일의 길이만큼 배열 선언
//            byte[] bytes = new byte[ (int) file.length()];
//            // * 스트림 읽기 : 바이트 배열에 저장
//            inputStream.read( bytes );
//
//            // 출력 스트림 객체
//            BufferedOutputStream outputStream = new BufferedOutputStream( response.getOutputStream() );
//            // * 스트림 내보내기
//            outputStream.write( bytes );
//            // 버퍼 초기화 & 스트림 닫기
//            outputStream.flush(); // 초기화
//            outputStream.close();
//            inputStream.close();
//
//        } catch (Exception e) {
//            System.out.println("첨부파일 다운로드 실패 : " + e);
//        }
//    }

    // 3. 게시글 작성
    @Transactional
    public boolean setWrite(BoardDto boardDto){
        System.out.println( "--서비스 boardDto--" );
        System.out.println( boardDto );

        // 카테고리 정보를 가져와서 null 인지 확인
        Optional<BcategoryEntity> optional = bcategoryRepository.findById( boardDto.getBcno() );

        if ( !optional.isPresent() ){ // 엔티티가 없으면 false
            return false;
        }
        System.out.println("optional :: "+optional);

        BcategoryEntity bcategoryEntity = optional.get(); // 가져온 카테고리번호&이름 넣기
        System.out.println("optional.get() :: "+optional.get());

        // 저장하기 dto->entity
        BoardEntity boardEntity = boardRepository.save( boardDto.toBoardEntity() );
        System.out.println(boardDto.getBcno());
        boardEntity.getBcategoryEntity().setBcno(boardDto.getBcno());
        System.out.println( "--boardEntity--" );
        System.out.println( boardEntity );

        // 게시글번호를 가져왔을때 0이 아니면 실행
        if( boardEntity.getBno() != 0 ){

            fileupload( boardDto, boardEntity );    // 업로드 함수 실행

            boardEntity.setBcategoryEntity( bcategoryEntity );
            bcategoryEntity.getBoardEntityList().add( boardEntity );

            System.out.println("************");
            System.out.println(boardDto);
            System.out.println(boardEntity);
            System.out.println("************");
            // 카테고리 <-> 게시물 연관 관계 대입



            return true;
        }
        return false;
    }

}

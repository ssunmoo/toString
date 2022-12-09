package com.shop.tostring.service.board;

import com.shop.tostring.domain.dto.board.BcategoryDto;
import com.shop.tostring.domain.dto.board.BoardDto;
import com.shop.tostring.domain.dto.board.PageVo;
import com.shop.tostring.domain.entity.board.BcategoryEntity;
import com.shop.tostring.domain.entity.board.BcategoryRepository;
import com.shop.tostring.domain.entity.board.BoardEntity;
import com.shop.tostring.domain.entity.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private BoardRepository boardRepository;

    @Autowired
    private BcategoryRepository bcategoryRepository;

    @Autowired
    private HttpServletResponse response; // 리스폰 선언


    // 1. 카테고리 등록
    public boolean setBcategory(BcategoryDto bcategoryDto){
        BcategoryEntity entity = bcategoryRepository.save( bcategoryDto.toBcEntity() );
        if ( entity.getBcno() != 0 ){
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

    // 첨부파일 다운로드
    public void filedownload( String filename ){

        // UUID 제거
        String realfilename = "";
        String [] split = filename.split("_"); // 1. 앞 언더바(_) 기준으로 자르기
        for( int i = 1; i < split.length; i++ ){     // 2. uuid 제와한 반복문 돌리기 1번째 인덱스부터
            realfilename += split[i];                // 3. 뒷자리 문자열 추가
            if ( split.length-1 != i ){              // 4. 마지막 인덱스가 아니면
                realfilename += "_";                 // 5. 문자열[1]_문자열[2]_문자열[3]
            }
        }

        // 등록한 경로 찾기
        String filepath = path + filename;

        try {
            response.setHeader(
                    "Content-Disposition",  // 다운로드 형식
                    "attachment;filename=" + URLEncoder.encode( realfilename, "UTF-8")); // 다운로드에 표시된 파일명

            File file = new File( filepath ); // 등록된 경로의 파일 객체화

            // 입력 스트림 객체
            BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( file ) );
            // 파일의 길이만큼 배열 선언
            byte[] bytes = new byte[ (int) file.length()];
            // * 스트림 읽기 : 바이트 배열에 저장
            inputStream.read( bytes );

            // 출력 스트림 객체
            BufferedOutputStream outputStream = new BufferedOutputStream( response.getOutputStream() );
            // * 스트림 내보내기
            outputStream.write( bytes );
            // 버퍼 초기화 & 스트림 닫기
            outputStream.flush(); // 초기화
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            System.out.println("첨부파일 다운로드 실패 : " + e);
        }
    }

    // 3. 게시글 작성
    @Transactional
    public boolean setWrite(BoardDto boardDto){
        // 카테고리 정보를 가져와서 null 인지 확인
        Optional<BcategoryEntity> optional = bcategoryRepository.findById( boardDto.getBcno() );
        if ( !optional.isPresent() ){ // 엔티티가 없으면 false
            return false;
        }
        BcategoryEntity bcategoryEntity = optional.get(); // 가져온 카테고리번호&이름 넣기
        // 저장하기 dto->entity
        BoardEntity boardEntity = boardRepository.save( boardDto.toBoardEntity() ); // boardEntity 생성 FK 생성 전
        System.out.println(boardEntity);
//        boardEntity.getBcategoryEntity().setBcno(boardDto.getBcno());

        // 게시글번호를 가져왔을때 0이 아니면 실행
        if( boardEntity.getBno() != 0 ){

            fileupload( boardDto, boardEntity );    // 업로드 함수 실행

            // 카테고리 <-> 게시물 연관 관계 대입
            boardEntity.setBcategoryEntity( bcategoryEntity ); // 넣어주기 @Transactional 필수
            bcategoryEntity.getBoardEntityList().add( boardEntity );

            System.out.println("************");
            System.out.println(boardEntity.getBcategoryEntity());
            // @ToString.Exclude 으로 막아놨기 떄문에 boardEntity안에있는 .getBcategoryEntity() 으로 확인
            System.out.println("************");
            return true;
        }
        return false;
    }

    // 후기 게시판 출력
    @Transactional
    public PageVo reviewList( int bcno, int page, String key, String keyWord ){

        Page<BoardEntity> entityList = null;
        // vo 출력 객체 생성
        PageVo pageVo = new PageVo();
        // 페이지 설정 -> 0부터 시작
        Pageable pageable = PageRequest.of( page-1, 5, Sort.by(Sort.Direction.DESC, "bno") );

        // 검색여부
        if( key.equals("btitle")){
            entityList = boardRepository.findByBtitle( bcno, keyWord, pageable );
        } else if ( key.equals("bcontent") ){
            entityList = boardRepository.findByBcontent( bcno, keyWord, pageable );
        } else {
            entityList = boardRepository.findByBcno( bcno, pageable ); // 전체출력
        }
        List<BoardDto> dtoList = new ArrayList<>();

        // 표시할 페이징 번호 버튼 수
        int btnCount = 5;
        int startBtn = ( page/btnCount ) * btnCount+1; // 시작번호 버튼
        int endBtn = startBtn + btnCount-1; // 마지막 버튼
        
        // 토탈 페이지
        if( endBtn > entityList.getTotalPages() ) { // 마지막 페이지가 크면
            endBtn = entityList.getTotalPages();    // 같게 만들기
        }
            
        // 엔티티에 있는 내용들을 dto로 저장해서 리턴
        entityList.forEach( (r) ->
                dtoList.add( r.toBoardDto() )
        );
        pageVo.setBoardDtoList( dtoList );
        pageVo.setStartBtn( startBtn );
        pageVo.setEndBtn( endBtn );
        pageVo.setTotalPage( entityList.getTotalPages() );

        return pageVo;
    }


    // 후기 게시판 상세보기
    @Transactional
    public BoardDto reviewSelect( int bno ){
        Optional< BoardEntity > optional = boardRepository.findById( bno );
        if( optional.isPresent() ){
            BoardEntity boardEntity = optional.get();
            return boardEntity.toBoardDto();
        }else {
            return null;
        }
    }

    // 후기 게시판 게시글 수정
    public boolean rUpdate( BoardDto boardDto ){
        Optional<BoardEntity> optional = boardRepository.findById(boardDto.getBno());
        if( optional.isPresent() ){
            BoardEntity boardEntity = optional.get();
            
            // 첨부파일 수정 관련
            if( boardDto.getBfile() != null ){ // dto에 파일이 있고
                if( boardEntity.getBfile() != null ){ // 엔티티에 첨부파일이 있으면
                    File file = new File( path + boardEntity.getBfile() ); // 기존 첨부파일을 객체화
                    if( file.exists() ){ // 존재 시 파일 삭제
                        file.delete();
                    }
                    fileupload( boardDto, boardEntity ); 
                }
            }
            boardEntity.setBtitle( boardDto.getBtitle() );
            boardEntity.setBcontent( boardDto.getBcontent() );
            return true;
        }else {
            return false;
        }
    }

    // 후기 게시판 게시글 삭제
    public boolean rDelete( int bno ){
        Optional<BoardEntity> optional = boardRepository.findById( bno );
        if ( optional.isPresent() ){
            BoardEntity boardEntity = optional.get();
            
            // 첨부파일 삭제
            if( boardEntity.getBfile() != null ){
                File file = new File(path + boardEntity.getBfile()); // 등록된 첨부파일을 객체화
                if( file.exists() ){
                    file.delete();
                }
            }
            boardRepository.delete( boardEntity );
            return true;
        }else {
            return false;
        }
    }

//    // 시연영상 게시판
//    public List<BoardDto> testingList(){
//        return false;
//    }














}

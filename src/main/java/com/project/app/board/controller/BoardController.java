package com.project.app.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.board.dto.BoardDto;
import com.project.app.board.service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    // http://localhost:8080/api/boards/2
    @GetMapping("/id/{id}")
    public ResponseEntity<List<BoardDto>> getBoardById(@PathVariable Long id) {
    	List<BoardDto> board = boardService.getBoardById(id);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // http://localhost:8080/api/boards/admin
    // @GetMapping("/{author}") 이렇게 쓰면 위 id 와 같아서 오류가 난다.
    @GetMapping("/author/{author}") //이런식으로 겹치지 않게 해줘야 한다.
    public ResponseEntity<List<BoardDto>> getBoardByAuthor(@PathVariable String Author) {
    	List<BoardDto> board = boardService.getBoardByAuthor(Author);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto){
    	boardService.createBoard(boardDto);
    	return ResponseEntity.status(HttpStatus.CREATED).body(boardDto);
    }
   
//    @PostMapping("/createBoardList")
//    public ResponseEntity<BoardDto> createBoardList(@RequestBody BoardDto boardDto){
//    	boardService.createBoardList(boardDto);
//    	return ResponseEntity.ok(boardDto);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto){
    	boardDto.setId(id);
    	boardService.updateBoard(boardDto);
    	return ResponseEntity.ok(boardDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardById(@PathVariable Long id){
        boardService.deleteBoardById(id);
        return ResponseEntity.ok().build(); // 응답 본문 없이 200 OK 반환
    }
    /* 
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BoardDto>> getBoardsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(boardService.getBoardsByCategory(category));
    }
    */
}
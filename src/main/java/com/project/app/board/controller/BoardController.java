package com.project.app.board.controller;

import java.util.List;

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
    
    // http://localhost:8080/api/boards/1/admin
    @GetMapping("/{id}/{author}")
    public ResponseEntity<List<BoardDto>> getBoardByIdAndAuthor(@PathVariable Long id, @PathVariable String author) {
    	List<BoardDto> board = boardService.getBoardByIdAndAuthor(id, author);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // http://localhost:8080/api/boards/1?author=admin //이럴경우 스프링 시큐리티에 문제가 생긴다.
    /*
    @GetMapping("/{id}")
    public ResponseEntity<List<BoardDto>> getBoardByIdAndAuthor(@PathVariable Long id, @RequestParam String author) {
    	List<BoardDto> board = boardService.getBoardByIdAndAuthor(id, author);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    */
    
    @PostMapping("/createBoardList")
    public ResponseEntity<BoardDto> createBoardList(@RequestBody BoardDto boardDto){
    	boardService.createBoardList(boardDto);
    	return ResponseEntity.ok(boardDto);
    }
    
    @PostMapping("/insertBoardList")
    public ResponseEntity<BoardDto> insertBoardList(@RequestBody BoardDto boardDto){
    	boardService.insertBoardList(boardDto);
    	return ResponseEntity.ok(boardDto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoardList(@PathVariable Long id, @RequestBody BoardDto boardDto){
    	boardDto.setId(id);
    	boardService.updateBoardList(boardDto);
    	return ResponseEntity.ok(boardDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<BoardDto> deleteBoardList(@PathVariable Long id, @RequestBody BoardDto boardDto){
    	boardDto.setId(id);
    	boardService.deleteBoardList(boardDto);
    	return ResponseEntity.ok(null);
    }
    
    /*
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BoardDto>> getBoardsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(boardService.getBoardsByCategory(category));
    }
    */
}
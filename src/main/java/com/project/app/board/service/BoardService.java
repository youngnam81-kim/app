package com.project.app.board.service;

import java.util.List;

import com.project.app.board.dto.BoardDto;

public interface BoardService {
    List<BoardDto> getAllBoards();
    List<BoardDto> getBoardById(Long id);
    List<BoardDto> getBoardByAuthor(String author);
    List<BoardDto> getBoardByIdAndAuthor(Long id, String author);
    void createBoardList(BoardDto boardDto);
    void insertBoardList(BoardDto boardDto);
    BoardDto updateBoardList(BoardDto boardDto);
	void deleteBoardList(BoardDto boardDto);
    
    /*
    List<BoardDto> getBoardsByCategory(String category);
	*/
}
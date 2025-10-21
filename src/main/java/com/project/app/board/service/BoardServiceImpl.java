package com.project.app.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.app.board.dto.BoardDto;
import com.project.app.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<BoardDto> getAllBoards() {
        return boardMapper.selectAllBoards();
    }

	@Override
	public List<BoardDto> getBoardById(Long id) {
		return boardMapper.selectBoardById(id);
	}
	
	@Override
	public List<BoardDto> getBoardByAuthor(String author) {
		return boardMapper.selectBoardByAuthor(author);
	}
	
	@Override
	public List<BoardDto> getBoardByIdAndAuthor(Long id, String Author) {
		return boardMapper.selectBoardByIdAndAuthor(id,Author);
	}

	@Override
	@Transactional
	public void createBoardList(BoardDto boardDto) {
		boardMapper.createBoardList(boardDto);
	}
	
	@Override
	@Transactional
	public void insertBoardList(BoardDto boardDto) {
		boardMapper.insertBoardList(boardDto);
	}
	
	@Override
	@Transactional
	public BoardDto updateBoardList(BoardDto boardDto) {
		boardMapper.updateBoardList(boardDto);
		return boardDto;
	}
	
	@Override
	@Transactional
	public void deleteBoardList(BoardDto boardDto) {
		boardMapper.deleteBoardList(boardDto);
	}
	
/*
    @Override
    public List<BoardDto> getBoardsByCategory(String category) {
        return boardMapper.selectBoardsByCategory(category);
    }
    */
    
/*
BoardService (인터페이스)와 BoardServiceImpl (구현체)로 나누는 것은 객체지향 설계 원칙 중 하나이자 Spring 
프레임워크에서 강력하게 권장하는 방법입니다. 이렇게 나누는 데는 명확한 여러 가지 이유와 장점이 있습니다.

1. 추상화 (Abstraction)
인터페이스(BoardService): 어떤 기능을 제공할 것인가를 정의합니다. 즉, "게시판 서비스는 게시글 목록 조회, 상세 조회, 
추가, 수정, 삭제 등의 기능을 제공해야 한다"는 약속을 하는 것이죠.
구현체(BoardServiceImpl): 정의된 기능을 어떻게 구현할 것인가를 담당합니다. 실제 데이터베이스 연동, 
비즈니스 로직 처리 등 구체적인 세부 사항을 담고 있습니다.

2. 느슨한 결합 (Loose Coupling)
컨트롤러나 다른 서비스 계층에서는 BoardServiceImpl을 직접 참조하는 대신 BoardService 인터페이스를 통해 참조합니다.
// BoardController.java
private final BoardService boardService; 
// ServiceImpl이 아닌 Service 인터페이스를 주입받음
이렇게 하면 컨트롤러는 BoardService가 어떤 메소드를 제공하는지만 알면 되고, 
그 메소드가 내부적으로 어떻게 구현되었는지(JPA를 쓰는지, MyBatis를 쓰는지, 메모리를 쓰는지 등)는 알 필요가 없습니다.
이는 의존성을 낮추어 코드의 유지보수성과 유연성을 높입니다.

3. 유연성 및 확장성 (Flexibility & Extensibility)
만약 나중에 BoardService의 구현 방식을 변경해야 한다고 가정해 보세요 (예: MyBatis에서 JPA로 전환). 이 경우 
BoardServiceImpl만 수정하거나 BoardServiceJPAImpl과 같은 새로운 구현체를 만들어서 교체하기만 하면 됩니다.
BoardService 인터페이스를 사용하는 BoardController와 같은 클라이언트 코드에서는 전혀 수정할 필요가 없습니다. 
이는 새로운 기능 추가나 기존 기능 변경 시에도 클라이언트 코드에 미치는 영향을 최소화합니다.

4. 테스트 용이성 (Testability)
BoardService 인터페이스를 사용하면 단위 테스트(Unit Test)를 작성하기가 매우 용이해집니다. 
실제 BoardServiceImpl 대신 가짜 객체(Mock Object)를 만들어 인터페이스를 구현하도록 해서, 
테스트하고 싶은 특정 코드(예: BoardController)가 서비스 계층에 의존하지 않고도 테스트할 수 있습니다.
이렇게 Mock 객체를 주입하면 실제 DB 연결이나 외부 시스템 의존 없이 특정 로직만을 검증할 수 있습니다.

5. Spring AOP 및 트랜잭션 처리
Spring Framework는 AOP(Aspect-Oriented Programming)를 통해 트랜잭션, 로깅, 보안 등 
횡단 관심사(Cross-cutting Concerns)를 처리할 때 프록시(Proxy) 기반으로 동작합니다.
인터페이스를 사용하면 Spring이 런타임에 이 인터페이스를 구현하는 프록시 객체를 만들어 주입하기 때문에, 
@Transactional 같은 어노테이션이 더 효과적이고 안정적으로 적용됩니다. 
(@Transactional이 정상 작동하려면 인터페이스 기반으로 동작하는 것이 권장됩니다.)
*/
    
}
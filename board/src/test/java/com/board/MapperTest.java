package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class MapperTest {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testInsert() {
		BoardDTO params = new BoardDTO();
		params.setTitle("100번 제목");
		params.setContent("100번 내용");
		params.setWriter("테스터");
		
		int result = boardMapper.insertBoard(params);
		System.out.println("result=============> "+result);
	}
	
	@Test
	public void testSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 10);
		try {
				String boardJson = new ObjectMapper().writeValueAsString(board);
				
				System.out.println("==============================");
				System.out.println(boardJson);
				System.out.println("==============================");
				
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("64번 게시물 수정");
		params.setContent("64번 게시글 내용");
		params.setWriter("64번 테스터");
		params.setIdx((long) 64);
		
		int result = boardMapper.updateBoard(params);
		System.out.println("수정==========>"+result);
	}
	
	@Test
	public void testDelete() {
		int result = boardMapper.deleteBoard((long) 64);
		System.out.println("삭제==========>"+result);
	}
	
	@Test
	public void testSelectAll() {
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		if(boardTotalCount>0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if(!boardList.isEmpty()) {	//리스트가 비었나?
				for(BoardDTO board:boardList) {	//foreach문
					System.out.println("==============================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
					System.out.println("==============================");
				}
			}
		}
	}
}

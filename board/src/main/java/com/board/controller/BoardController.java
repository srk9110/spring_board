package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null) {
				return "redirect:/board/list.do";
			}
			model.addAttribute("board", board);
		}

		return "board/write";
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/board/list.do";
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(Model model) {
		List<BoardDTO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);

		return "board/list";
	}
	
	
	
	@GetMapping("/board/view.do")
	public String openBoardDetail(@RequestParam(value="idx", required=false)
	Long idx, Model model) {
		if(idx==null)
		{
			//올바르지 않은 접근
			return "redirect:/board/list.do";
		}
		
		BoardDTO board = boardService.getBoardDetail(idx);
		if(board==null || "Y".equals(board.getDeleteYn())) {
			//없는 게시글 or 이미 삭제된 게시글
			return "redirect:/board/list.do";
		}
		model.addAttribute("board",board);
		boardService.cntPlus(idx);
		return "board/view";
	}
	
	@PostMapping("/board/delete.do")
	public String deleteBoard(@RequestParam(value="idx", required = false)
	Long idx) {
		if(idx==null) {
			//올바르지 않은 접근
			return "redirect:/board/list.do";
		}
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if(isDeleted==false) {
				//게시글 삭제 실패
			}
		} catch(DataAccessException e) {
			//데이터 베이스 처리과정에 문제 발생
		} catch(Exception e) {
			//시스템 문제 발생
		}
		return "redirect:/board/list.do";
	}
	
	
}

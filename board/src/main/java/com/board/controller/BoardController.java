package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/write.do")
	public String openBoardWrite(Model model) {
		
		String title ="제목";
		String content ="내용";
		String writer ="홍길동";
		
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("writer", writer);
		
		return "board/write";
	}
}

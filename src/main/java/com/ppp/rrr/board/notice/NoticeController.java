package com.ppp.rrr.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.board.BoardDTO;
import com.iu.main.util.Pager;

@Controller
@RequestMapping("/board/*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@ModelAttribute("board")
	public String getBoardName() {
		return "notice";
	}
	
	@PostMapping("setContentsImgDelete")
	public String setContentsImgDelete(String path, HttpSession session, Model model) throws Exception {
		boolean check = noticeService.setContentsImgDelete(path, session);
		model.addAttribute("result", check);
		return "commons/ajaxResult";
	}
	
	@PostMapping("setContentsImg")
	public String setContentsImg(MultipartFile files, HttpSession session, Model model) throws Exception {
		System.out.println("setContetnsImg");
		System.out.println(files.getOriginalFilename());
		String path = noticeService.setContentsImg(files, session);
		model.addAttribute("result", path);
		return "commons/ajaxResult";
	
	}
	
	
	// 각각 메서드들이 실행될 때마다 함께 실행되어서
	// 이름은 board, value는 notice로 넣겠다 뜻
	
	// List
//	@RequestMapping(value = "list", method = RequestMethod.GET)
//	public String getList(Model model) throws Exception {
//		List<NoticeDTO> ar = noticeService.getList();
//		model.addAttribute("list",ar);
//		return "board/list";
//	}
	
	@GetMapping("fileDelete")
	public String setFileDelete(NoticeFileDTO noticeFileDTO, HttpSession session, Model model) throws Exception {
//		System.out.println(noticeFileDTO.getFileNum());
		
		int result = noticeService.setFileDelete(noticeFileDTO, session);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	// Detail
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String getDetail(NoticeDTO noticeDTO, Model model) throws Exception {
		BoardDTO boardDTO = noticeService.getDetail(noticeDTO);
//		ModelAndView mv = new ModelAndView();
		
		if(boardDTO != null) {
			String message = "등록성공";
			model.addAttribute("dto", boardDTO);
			
			return "board/detail";
		} else {
			model.addAttribute("message", "글이 없다");
			model.addAttribute("url", "list");
			
			return "commons/result";
		}
//		return mv;
	}
	
	
//	// Add form 불러오기
//	@RequestMapping(value = "add", method = RequestMethod.GET)
//	public void setAdd() throws Exception {
//		
//	}
//	
//	// Add insert
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public String setAdd(NoticeDTO noticeDTO) throws Exception {
//		int result = noticeService.setAdd(noticeDTO);
//		return "redirect:./list";
//	}
	
	
	// Delete
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String setDelete(BoardDTO boardDTO) throws Exception {
		int result = noticeService.setDelete(boardDTO);
		return "redirect:./list";
	}
	
	// Update form
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String setUpdate(BoardDTO boardDTO, Model model) throws Exception {
		boardDTO = noticeService.getDetail(boardDTO);
		model.addAttribute("dto", boardDTO);
		
		return "board/update";
	}
	
	// Update POST
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String setUpdate(NoticeDTO noticeDTO, MultipartFile[] photos, HttpSession session) throws Exception {
		int result = noticeService.setUpdate(noticeDTO, photos, session);
		
		return("redirect:./detail?num=" + noticeDTO.getNum());
	}
	
	
	/* Paging ---------------------------------------------------- */
	
	
	// List
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String getList(Pager pager, Model model) throws Exception {
		List<BoardDTO> ar = noticeService.getList(pager);
		model.addAttribute("list",ar);
		model.addAttribute("pager", pager);
		return "board/list";
	}
	
	
	// Add form
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String setAdd() throws Exception {
			return "board/add";
	}
	
	// Add insert
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String setAdd(BoardDTO boardDTO, MultipartFile [] photos, HttpSession session, Model model) throws Exception {
		
		int result = noticeService.setAdd(boardDTO, photos, session);
		
		String message = "등록 실패";
		
		if(result>0) {
			message = "등록 성공";
		}
		
		model.addAttribute("message", message);
		model.addAttribute("url", "list");
		
		return "commons/result";
	}
	
	

}

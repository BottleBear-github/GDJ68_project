package com.ppp.rrr.board.qna;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.board.BoardDTO;
import com.iu.main.board.notice.NoticeDTO;
import com.iu.main.util.Pager;

@Controller
@RequestMapping("/qna/*")
public class QnAController {

	@Autowired
	private QnAService qnAService;
	
	@ModelAttribute("board")
	public String getBoardName() {
		return "Q&A";
	}

	@RequestMapping(value="list", method = RequestMethod.GET)
	public String getList(Model model, Pager pager) throws Exception {
		List<BoardDTO> ar = qnAService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		return "board/list";
	}
	
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String setAdd() throws Exception{
		return "board/add";
	}
	
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String setAdd(QnADTO qnADTO, MultipartFile[] photos, HttpSession session) throws Exception {
		
		int result = qnAService.setAdd(qnADTO, photos, session);
		return "redirect:./list";
	}
	
	@RequestMapping(value="detail", method = RequestMethod.GET)
	public ModelAndView getDetail(QnADTO qnADTO, ModelAndView mav) throws Exception {
		BoardDTO boardDTO = qnAService.getDetail(qnADTO);
		mav.addObject("dto",boardDTO);
		mav.setViewName("board/detail");
		return mav;
	}
	
	@RequestMapping(value="reply", method = RequestMethod.GET)
	public ModelAndView reply(Long num, ModelAndView mav) throws Exception {
		//add post 에서 부모 글번호 가져와야되므로 Long num 받아서 어트리뷰트로 전달 
		mav.addObject("num", num);
		mav.setViewName("board/reply");
		return mav;
	}
	
	@RequestMapping(value="reply", method = RequestMethod.POST)
	public String reply(QnADTO qnADTO,MultipartFile[] photos, HttpSession session) throws Exception {
		//전달받은 num을 DS 가 qnADTO.getNum에 값 저장해서 전달 해줌 
		int result = qnAService.setReplyAdd(qnADTO, photos, session);
		return "redirect:./list";
	}
	
	
	@RequestMapping(value = "update")
	public String setUpdate(QnADTO qnADTO, Model model) throws Exception{
		BoardDTO boardDTO  = qnAService.getDetail(qnADTO);
		model.addAttribute("dto", boardDTO);
		return "board/update";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String setUpdate(QnADTO qnADTO, MultipartFile[] photos, HttpSession session) throws Exception {
		int result = qnAService.setUpdate(qnADTO,photos,session);
		return "redirect:./detail?num=" + qnADTO.getNum();
	}
	
	@RequestMapping(value = "delete")
	public String setDelete(QnADTO qnADTO) throws Exception {
		int result = qnAService.setDelete(qnADTO);
		return "redirect:./list";
	}
}

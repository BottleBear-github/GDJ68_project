package com.ppp.rrr.bankBook;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.member.MemberDTO;
import com.iu.main.util.Pager;
import com.ppp.rrr.bankBook.comment.CommentDTO;

@Controller
@RequestMapping("/bankbook/*")
public class BankBookController {
	
	@Autowired
	private BankBookService bankBookService;
//	comment
	@GetMapping("commentList")
	public void getCommentList(CommentDTO commentDTO,Pager pager,Model model) throws Exception{
		pager.setPerPage(2L);
		List<CommentDTO> ar=bankBookService.getCommentList(pager, commentDTO);
		model.addAttribute("commentList", ar);
	}
	@PostMapping("commentAdd")
	public String setCommentAdd(CommentDTO commentDTO,HttpSession session,Model model) throws Exception{
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		commentDTO.setId(memberDTO.getId());
		int result=bankBookService.setCommentAdd(commentDTO);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	
	
//	bankBook
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String getList(Pager pager,Model model)throws Exception{
		System.out.println("list");
		List<BankBookDTO> ar=bankBookService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager",pager);
		return "bankbook/list";
	}
	@RequestMapping(value = "detail")
	public ModelAndView getDetail(BankBookDTO bankBookDTO,ModelAndView mv) throws Exception{
		bankBookDTO=bankBookService.getDetail(bankBookDTO);
		mv.addObject("dto", bankBookDTO);
		System.out.println(bankBookDTO.getBookName());
		System.out.println("detail");
		mv.setViewName("bankbook/detail");
		return mv;
	}
	@RequestMapping(value = "add")
	public void setAdd() throws Exception{
		
	}
	@RequestMapping(value = "add" , method = RequestMethod.POST)
	public String setAdd(BankBookDTO bankBookDTO,MultipartFile [] photos,HttpSession session) throws Exception{
		int result=bankBookService.setAdd(bankBookDTO,photos,session);
		return "redirect:./list";
	}
	@RequestMapping(value = "update")
	public void setUpdate(BankBookDTO bankBookDTO,Model mv) throws Exception{
		bankBookDTO = bankBookService.getDetail(bankBookDTO);
		mv.addAttribute("dto", bankBookDTO);
	}
	@RequestMapping(value = "update" , method = RequestMethod.POST)
	public String setUpdate(BankBookDTO bankBookDTO) throws Exception{
		int result=bankBookService.setUpdate(bankBookDTO);
		//return "redirect:./list";//list로 이동
		return "redirect:./detail?bookNum="+bankBookDTO.getBookNum();
	}
	/*
	 * @RequestMapping(value = "update" , method = RequestMethod.POST) public String
	 * getUpdate(BankBookDTO bankBookDTO) throws Exception{ int
	 * result=bankBookService.setUpdate(bankBookDTO); return "reirect:./list"; }
	 */
	@RequestMapping(value = "delete")
	public String setDelete(Long bookNum) throws Exception{
		int result=bankBookService.setDelete(bookNum);
		return "redirect:./list";//list로 이동
	}
}

package com.ppp.rrr.bankBook;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;
import com.ppp.rrr.bankBook.comment.CommentDTO;

@Service
public class BankBookService {
	
	@Autowired
	private BankBookDAO bankBookDAO;
	
	@Autowired
	private FileManager fileManager;
	
//	comment
	public List<CommentDTO> getCommentList(Pager pager,CommentDTO commentDTO) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		pager.makeRowNum();
		pager.makePageNum(bankBookDAO.getCommentTotal(commentDTO));
		map.put("pager",pager);
		map.put("comment", commentDTO);
		return bankBookDAO.getCommentList(map);
	}
	
	public int setCommentAdd(CommentDTO commentDTO) throws Exception{
		return bankBookDAO.setCommentAdd(commentDTO);
	}
	//bankbook
	public List<BankBookDTO> getList(Pager pager) throws Exception{
		/*
		 * Map<String, Integer> map = new HashMap<String, Integer>(); //page startRow
		 * lastRow //1 1 10 //2 11 20 //3 21 30 int count=10; int
		 * startRow=(page-1)*count+1; int lastRow=page*count;
		 */
		
		pager.makeRowNum();
		Long total = bankBookDAO.getTotal();
		pager.makePageNum(total);
		
		/*
		 * map.put("startRow",startRow); map.put("lastRow", lastRow);
		 */
		return bankBookDAO.getList(pager);
	}
	
	public BankBookDTO getDetail(BankBookDTO bankBookDTO) throws Exception{
		
		
		return bankBookDAO.getDetail(bankBookDTO);
	}
	
	public int setAdd(BankBookDTO bankBookDTO,MultipartFile [] photos, HttpSession session) throws Exception{
		// /resoutces/upload/bankBook
		String path = "/resources/upload/bankBook";
		
		//long num = bankBookDAO.getSeqence();
		
		//bankBookDTO.setBookNum(num);
		int result = bankBookDAO.setAdd(bankBookDTO);
		
		for(MultipartFile multipartFile: photos) {
			
			if(multipartFile.isEmpty()) {
				continue;
			}
			String fileName = fileManager.fileSave(path, session, multipartFile);
			BankFileDTO bankFileDTO = new BankFileDTO();
			bankFileDTO.setOriginalName(multipartFile.getOriginalFilename());
			bankFileDTO.setFileName(fileName);
			bankFileDTO.setBookNum(bankBookDTO.getBookNum());
			result = bankBookDAO.setFileAdd(bankFileDTO);
				
		}
		return result;
	}
	public int setUpdate(BankBookDTO bankBookDTO) throws Exception{
		return bankBookDAO.setUpdate(bankBookDTO);
	}
	
	public int setDelete(Long num) throws Exception{
		return bankBookDAO.setDelete(num);
	}
	
}

package com.ppp.rrr.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.bankBook.BankBookDTO;
import com.iu.main.board.BoardDTO;
import com.iu.main.board.BoardService;
import com.iu.main.file.FileDTO;
import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;

@Service
public class NoticeService implements BoardService {

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private FileManager fileManager;
	
	
	
	public boolean setContentsImgDelete(String path, HttpSession session) throws Exception {
		// path = "/resources/upload/notice/파일명"
		
		FileDTO fileDTO = new FileDTO();

		// fileDTO.setFileName(path);
		// path.substring(path.lastIndexOf("\\")+1);
		// 위처럼 두 줄로 나눠서 쓰거나 아래처럼 한 번에 작성할 수도 있다.
		fileDTO.setFileName(path.substring(path.lastIndexOf("/")+1));
		
		path = "/resources/upload/notice/";		
		return fileManager.fileDelete(fileDTO, path, session);
	}
	

	public String setContentsImg(MultipartFile file, HttpSession session) throws Exception {
		String path = "/resources/upload/notice/";
		String fileName = fileManager.fileSave(path, session, file);
		return path+fileName;
	}
	
	public int setFileDelete(NoticeFileDTO noticeFileDTO, HttpSession session) throws Exception {
		// 폴더 파일 삭제
		noticeFileDTO = noticeDAO.getFileDetail(noticeFileDTO);
		boolean flag = fileManager.fileDelete(noticeFileDTO, "/resources/upload/notice/", session);
		
		// 파일이 삭제하기 db에서 삭제하는 순서로 진행한다면  
		
		if(flag) {
			// db에서 삭제
			return noticeDAO.setFileDelete(noticeFileDTO);			
		} 
		
		return 0; 
		
	}


//	// List
//	public List<NoticeDTO> getList() throws Exception {
//		return noticeDAO.getList();
//	}
	
//	// Detail
//	public NoticeDTO getDetail(NoticeDTO noticeDTO) throws Exception {
//		return noticeDAO.getDetail(noticeDTO);
//	}
	
//	// Add
//	public int setAdd(NoticeDTO noticeDTO) throws Exception {
//		return noticeDAO.setAdd(noticeDTO);
//	}
	
//	// Delete
//	public int setDelete(Long num) throws Exception {
//		return noticeDAO.setDelete(num);
//	}
//	
//	// Update
//	public int setUpdate(NoticeDTO noticeDTO) throws Exception {
//		return noticeDAO.setUpdate(noticeDTO);
//	}
	
	/* ------------------------------------------------------------ */
	/* Paging ----------------------------------------------------- */
	/* ------------------------------------------------------------ */
	
//	// Detail
//	public NoticeDTO getDetail(NoticeDTO noticeDTO) throws Exception{
//		
//		return noticeDAO.getDetail(noticeDTO);
//		//                              DAO로 보내줌
//	}
//		
//	// List
//	public List<NoticeDTO> getList(Pager pager) throws Exception {
//		pager.makeRowNum();
//		Long total = noticeDAO.getTotal(pager);
//		pager.makePageNum(total);
//		
//		return noticeDAO.getList(pager);
//	}
//	
//	// Add
//	public int setAdd(NoticeDTO noticeDTO, MultipartFile [] files, HttpSession session) throws Exception {
//		
//		String path = "/resources/upload/notice/";
//		
//		int result = noticeDAO.setAdd(noticeDTO);
//		
//		for(MultipartFile multipartFile : files) {
//			
//			if(multipartFile.isEmpty()) {
//				continue;
//			}
//			
//			String fileName = fileManager.fileSave(path, session, multipartFile);
//			NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
//			noticeFileDTO.setOriginalName(multipartFile.getOriginalFilename());
//			noticeFileDTO.setFileName(fileName);
//			noticeFileDTO.setNum(noticeDTO.getNum());
//			
//			result = noticeDAO.setFileAdd(noticeFileDTO);
//		}
//		
//		return result;
//	}
	
	
	/* ------------------------------------------------------------ */
	/* implements BoardDAO ---------------------------------------- */
	/* ------------------------------------------------------------ */

	@Override
	public List<BoardDTO> getList(Pager pager) throws Exception {
		pager.makeRowNum();
		Long total = noticeDAO.getTotal(pager);
		pager.makePageNum(total);
		
		return noticeDAO.getList(pager);
	}

	@Override
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getDetail(boardDTO);
	}

	@Override
	public int setAdd(BoardDTO boardDTO, MultipartFile[] files, HttpSession session) throws Exception {

		String path = "/resources/upload/notice/";
		
		int result = noticeDAO.setAdd(boardDTO);
		
		for(MultipartFile file : files) {
			
			if(!file.isEmpty()) {
				String fileName = fileManager.fileSave(path, session, file);
				
				NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
				noticeFileDTO.setNum(boardDTO.getNum());
				noticeFileDTO.setFileName(fileName);
				noticeFileDTO.setOriginalName(file.getOriginalFilename());
				
				result = noticeDAO.setFileAdd(noticeFileDTO);
			}		
		}
		return result;
	}

	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception {
		return noticeDAO.setDelete(boardDTO);
	}

	@Override
	public int setUpdate(BoardDTO boardDTO, MultipartFile [] files, HttpSession session) throws Exception {
		return noticeDAO.setUpdate(boardDTO);
	}

}

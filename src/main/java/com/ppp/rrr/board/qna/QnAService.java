package com.ppp.rrr.board.qna;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.board.BoardDTO;
import com.iu.main.board.BoardService;
import com.iu.main.board.notice.NoticeFileDTO;
import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;

@Service
public class QnAService implements BoardService {

	@Autowired
	private QnADAO qnADAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Override
	public List<BoardDTO> getList(Pager pager) throws Exception {
		
		pager.makeRowNum();
		Long total = qnADAO.getTotal(pager);
		pager.makePageNum(total);
		return qnADAO.getList(pager);
	}

	@Override
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception {
//		int result = qnaDAO.setHitUpdate(boardDTO);
		return qnADAO.getDetail(boardDTO);
	}

	@Override
	public int setAdd(BoardDTO boardDTO, MultipartFile[] photos, HttpSession session) throws Exception {
		String path = "/resources/upload/qna/";
		//1번째방법 쿼리만들고 dao 만들어서 하기
//		long num = bankBookDAO.getSequence();
//		bankBookDTO.setBookNum(num);
		int result = qnADAO.setAdd(boardDTO);
		
		for(MultipartFile multipartFile: photos) {
			if(multipartFile.isEmpty()) {
				continue;
			}
			String fileName = fileManager.fileSave(path, multipartFile,session);
			QnAFileDTO qnAFileDTO = new QnAFileDTO();
			qnAFileDTO.setOriginalName(multipartFile.getOriginalFilename());
			qnAFileDTO.setFileName(fileName);
			//2번째 방법 selectkey 매퍼 에서 등록해서 사용하기
			qnAFileDTO.setQnaNum(boardDTO.getNum());
			result = qnADAO.setFileAdd(qnAFileDTO);
		}
		
		return result ;
	}

	@Override
	public int setUpdate(BoardDTO boardDTO, MultipartFile[] photos, HttpSession session) throws Exception {
		int result = qnADAO.setUpdate(boardDTO);
		return result;
	}

	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception {
		int result = qnADAO.setDelete(boardDTO);
		return result;
		
	}
	
	public int setReplyAdd(QnADTO qnADTO, MultipartFile[] photos, HttpSession session)  throws Exception {
		BoardDTO parentDTO = new BoardDTO();
		//부모로 부터 받은건 qnADTO 그래서 parentDTO를 생성하여 해당 정보값들을 넘겨줌
		
		parentDTO.setNum(qnADTO.getNum());
		
		//넘겨줄때 detail 메소드를 실행해서 정보들을 가져와야되기때문에 boardDTO로 생성
		parentDTO = qnADAO.getDetail(parentDTO);
		//부모 글넘버 설정 끝났고 board에는 ref step 등의 멤버변수가 없기때문에 QnADTO q 선언 후 parentDTO를 형변환 해줌
		QnADTO q = (QnADTO)parentDTO;
		
		qnADTO.setRef(q.getRef());	
		qnADTO.setStep(q.getStep()+1);
		qnADTO.setDepth(q.getDepth()+1);
		int result = qnADAO.setStepUpdate(q);
		result = qnADAO.setReplyAdd(qnADTO);
		return result;
	}
	
	
}

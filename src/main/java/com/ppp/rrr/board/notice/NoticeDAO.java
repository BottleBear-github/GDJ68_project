package com.ppp.rrr.board.notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iu.main.board.BoardDAO;
import com.iu.main.board.BoardDTO;
import com.iu.main.util.Pager;

@Repository
public class NoticeDAO implements BoardDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String NAMESPACE="com.iu.main.board.notice.NoticeDAO.";

	
	
	public NoticeFileDTO getFileDetail(NoticeFileDTO noticeFileDTO) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "getFileDetail", noticeFileDTO);
	}
	
	// Update.jsp에서 첨부파일 삭제
	public int setFileDelete(NoticeFileDTO noticeFileDTO) throws Exception {
		return sqlSession.delete(NAMESPACE + "setFileDelete", noticeFileDTO);
	}
	
	
//	// List
//	public List<NoticeDTO> getList() throws Exception {
//		return sqlSession.selectList(NAMESPACE + "getList");
//	}
	
//	// Detail
//	public NoticeDTO getDetail(NoticeDTO noticeDTO) throws Exception {
//		return sqlSession.selectOne(NAMESPACE + "getDetail", noticeDTO);
//	}
//		
//	// Add
//	public int setAdd(NoticeDTO noticeDTO) throws Exception {
//		return sqlSession.insert(NAMESPACE + "setAdd", noticeDTO);
//	}
//	
//	// Delete
//	public int setDelete(Long num) throws Exception {
//		return sqlSession.delete(NAMESPACE + "setDelete", num);
//	}
//	
//	// Update
//	public int setUpdate(NoticeDTO noticeDTO) throws Exception {
//		return sqlSession.update(NAMESPACE + "setUpdate", noticeDTO);
//	}
//	
//	// Total
//	public Long getTotal(Pager pager) throws Exception {
//		return sqlSession.selectOne(NAMESPACE + "getTotal", pager);
//		
//	}
	
	
	/* ------------------------------------------------------------ */
	/* Paging ---------------------------------------------------- */
	/* ------------------------------------------------------------ */
	
	
//	// List
//	public List<NoticeDTO> getList(Pager pager) throws Exception {
//		return sqlSession.selectList(NAMESPACE + "getList", pager);
//	}
//	
//	// fileAdd
//	public int setFileAdd(NoticeFileDTO noticeFileDTO) throws Exception {
//		return sqlSession.insert(NAMESPACE + "setFileAdd", noticeFileDTO);
//	}
	
	
	/* ------------------------------------------------------------ */
	/* implements BoardDAO ---------------------------------------- */
	/* ------------------------------------------------------------ */

	
	@Override
	public List<BoardDTO> getList(Pager pager) throws Exception {
		return sqlSession.selectList(NAMESPACE + "getList", pager);
	}

	@Override
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + "getDetail", boardDTO);
	}

	@Override
	public int setAdd(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert(NAMESPACE + "setAdd", boardDTO);
	}

	@Override
	public int setUpdate(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update(NAMESPACE + "setUpdate", boardDTO);
	}

	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete(NAMESPACE + "setDelete", boardDTO);
	}

	@Override
	public Long getTotal(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + "getTotal", pager);
	}

	@Override
	public int setHitUpdate(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// fileAdd
	public int setFileAdd(NoticeFileDTO noticeFileDTO) throws Exception {
		return sqlSession.insert(NAMESPACE + "setFileAdd", noticeFileDTO);
	}

}

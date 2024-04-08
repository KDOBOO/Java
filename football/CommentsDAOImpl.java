package football;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBUtil;
import com.util.footballDBConn;

public class CommentsDAOImpl implements CommentsDAO {
	// conn 변수에 DB와 연결된 Connection 객체가 저장
	private Connection conn = footballDBConn.getConnection();

// player
	@Override
	// 선수코멘트 등록
	public int player_insertComments(CommentsDTO dto) throws SQLException {
		int result = 0;
		// SQL 쿼리를 실행하기 위해 사용되는 pstmt 객체
		PreparedStatement pstmt = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = " INSERT INTO player_comments(commentNumber,commentNick,commentCon,playerId,commentDate) "
					+ "VALUES (ct_seq.NEXTVAL,?,?,?,?) ";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, dto.getCommentNick());
			pstmt.setString(2, dto.getCommentCon());
			pstmt.setInt(3, dto.getPlayerId());
			pstmt.setString(4, dto.getCommentDate());

			// 4)실행할 쿼리를 변수에 저장
			result = pstmt.executeUpdate();

			// 전체예외
		} catch (Exception e) {
			throw e;
		} finally {
			// 5)객체 close
			DBUtil.close(pstmt);
		}
		return result;
	}

	@Override
	// 선수코멘트리스트 출력
	public List<CommentsDTO> player_listComments(int playerId) {
		List<CommentsDTO> list = new ArrayList<CommentsDTO>();
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		// rs는 실행된 쿼리의 결과를 받아오는 데 사용
		ResultSet rs = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = "SELECT commentNumber,c.playerId,commentNick,commentCon,commentDate " + "FROM player_comments c "
					+ "JOIN player p ON c.playerId = p.playerId " + "WHERE INSTR(c.playerId, ?) >= 1 ";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setInt(1, playerId);

			// 4)쿼리 실행 후 결과 집합을 가져옴
			rs = pstmt.executeQuery();

			// 5)결과 집합을 반복하면서 DTO 객체를 생성하고, 리스트에 추가
			while (rs.next()) {
				CommentsDTO dto = new CommentsDTO();

				dto.setCommentNumber(rs.getInt("commentNumber"));
				dto.setPlayerId(rs.getInt("playerId"));
				dto.setCommentNick(rs.getString("commentNick"));
				dto.setCommentCon(rs.getString("commentCon"));
				dto.setCommentDate(rs.getString("commentDate"));

				list.add(dto);

			}

			// 전체예외
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5)객체닫기
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}

	@Override
	// 선수이름으로 선수ID 찾기
	public List<CommentsDTO> FindByPlayerName(String playerName) {
		// 코멘트 리스트를 저장할 리스트를 생성
		List<CommentsDTO> list = new ArrayList<CommentsDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = "SELECT playerName,playerId FROM player WHERE playerName = ? ";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, playerName);

			// 4)쿼리 실행 후 결과 집합을 가져옴
			rs = pstmt.executeQuery();

			// 5)결과 집합을 반복하면서 DTO 객체를 생성하고, 리스트에 추가
			while (rs.next()) {
				CommentsDTO dto = new CommentsDTO();

				dto.setPlayerId(rs.getInt("playerId"));
				dto.setPlayerName(rs.getString("playerName"));

				list.add(dto);
			}

			// 전체예외
		} catch (Exception e) {
			e.printStackTrace();
		} // finally로 객체닫기
		finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

// team
	@Override
	// 팀코멘트 등록
	public int team_insertComments(CommentsDTO dto) throws SQLException {
		int result = 0;
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = " INSERT INTO team_comments(commentNumber,commentNick,teamCode,commentCon,commentsDate) "
					+ " VALUES  (ct_seq.NEXTVAL,?,?,?,?)";
			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);
			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, dto.getCommentNick());
			pstmt.setString(2, dto.getTeamCode());
			pstmt.setString(3, dto.getCommentCon());
			pstmt.setString(4, dto.getCommentDate());

			// 4)실행할 쿼리를 변수에 저장
			result = pstmt.executeUpdate();

			// 전체예외
		} catch (Exception e) {
			throw e;
		} finally {
			// 5)객체 close
			DBUtil.close(pstmt);
		}

		return result;
	}

	@Override
	// 팀코멘트리스트 출력
	public List<CommentsDTO> team_listComments(String teamCode) {
		List<CommentsDTO> list = new ArrayList<CommentsDTO>();
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		// rs는 실행된 쿼리의 결과를 받아오는 데 사용
		ResultSet rs = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = "SELECT commentNumber,c.teamCode,commentCon,commentsDate,commentNick " + "FROM team_comments c "
					+ "JOIN team t ON c.teamCode=t.teamCode " + "WHERE c.teamCode = ? ";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, teamCode);

			// 4)실행할 쿼리를 변수에 저장
			rs = pstmt.executeQuery();

			// 5)결과 집합을 반복하면서 DTO 객체를 생성하고, 리스트에 추가
			while (rs.next()) {
				CommentsDTO dto = new CommentsDTO();

				dto.setCommentNumber(rs.getInt("commentNumber"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setCommentNick(rs.getString("commentNick"));
				dto.setCommentCon(rs.getString("commentCon"));
				dto.setCommentDate(rs.getString("commentsDate"));

				list.add(dto);
			}

			// 전테예외
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6)객체닫기
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}

	@Override
	// 팀이름으로 팀코드 찾기
	public List<CommentsDTO> FindByTeamName(String teamName) {
		List<CommentsDTO> list = new ArrayList<CommentsDTO>();
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		// rs는 실행된 쿼리의 결과를 받아오는 데 사용
		ResultSet rs = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = "SELECT teamName,teamCode FROM team WHERE INSTR(teamName,?) > 0";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, teamName);

			// 4)실행할 쿼리를 변수에 저장
			rs = pstmt.executeQuery();

			// 5)결과 집합을 반복하면서 DTO 객체를 생성하고, 리스트에 추가
			while (rs.next()) {
				CommentsDTO dto = new CommentsDTO();

				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

// match
	@Override
	// 경기코멘트 등록
	public int match_insertComments(CommentsDTO dto) throws SQLException {
		int result = 0;
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = " INSERT INTO match_comments(commentNumber,commentNick,matchNumber,commentCon,commentDate) "
					+ "VALUES (ct_seq.NEXTVAL,?,?,?,?) ";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, dto.getCommentNick());
			pstmt.setInt(2, dto.getMatchNumber());
			pstmt.setString(3, dto.getCommentCon());
			pstmt.setString(4, dto.getCommentDate());

			// 4)실행할 쿼리를 변수에 저장
			result = pstmt.executeUpdate();

			// 전체예외
		} catch (Exception e) {
			throw e;
		} finally {
			// 5)객체 close
			DBUtil.close(pstmt);
		}

		return result;
	}

	@Override
	// 경기코멘트리스트 출력
	public List<CommentsDTO> match_listComments(int matchNumber) {
		List<CommentsDTO> list = new ArrayList<CommentsDTO>();
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		// rs는 실행된 쿼리의 결과를 받아오는 데 사용
		ResultSet rs = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = "SELECT commentNumber,m.matchNumber,commentCon,commentDate,commentNick " + "FROM match_comments c "
					+ "JOIN match m ON m.matchNumber = c.matchNumber " + "WHERE m.matchNumber = ? ";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setInt(1, matchNumber);

			// 4)실행할 쿼리를 변수에 저장
			rs = pstmt.executeQuery();

			// 5)결과 집합을 반복하면서 DTO 객체를 생성하고, 리스트에 추가
			while (rs.next()) {
				CommentsDTO dto = new CommentsDTO();

				dto.setCommentNumber(rs.getInt("commentNumber"));
				dto.setMatchNumber(rs.getInt("matchNumber"));
				dto.setCommentNick(rs.getString("commentNick"));
				dto.setCommentCon(rs.getString("commentCon"));
				dto.setCommentDate(rs.getString("commentDate"));

				list.add(dto);

			}

			// 전체예외
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6)객체닫기
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}

	@Override
	// 경기날짜와 구장이름으로 경기번호찾기
	public List<CommentsDTO> FindBymatchNumber(String matchDate, String stadiumName) {
		List<CommentsDTO> list = new ArrayList<CommentsDTO>();
		// pstmt는 쿼리를 실행할 때 사용
		PreparedStatement pstmt = null;
		// rs는 실행된 쿼리의 결과를 받아오는 데 사용
		ResultSet rs = null;
		String sql;

		try {
			// 1)쿼리작성
			sql = "SELECT matchnumber "
		               + " FROM match "
		               + " JOIN stadium ON match.tcode = stadium.teamcode "
		               + " WHERE match.matchdate = ? AND "
		               + " INSTR(REPLACE(stadium.stadiumName,' ',''), REPLACE(?,' ','')) >= 1";

			// 2)쿼리를 인자로 Pre객체 생성
			pstmt = conn.prepareStatement(sql);

			// 3)setter를 이용해 ?에 값 할당
			pstmt.setString(1, matchDate);
			pstmt.setString(2, stadiumName);

			// 4)실행할 쿼리를 변수에 저장
			rs = pstmt.executeQuery();

			// 5)결과 집합을 반복하면서 DTO 객체를 생성하고, 리스트에 추가
			while (rs.next()) {
				CommentsDTO dto = new CommentsDTO();

				dto.setMatchNumber(rs.getInt("matchNumber"));

				list.add(dto);
			}

			// 전체예외
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6)객체닫기
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}

}

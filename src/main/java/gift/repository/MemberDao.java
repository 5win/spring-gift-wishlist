package gift.repository;

import gift.DTO.Member;
import java.sql.Types;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcClient jdbcClient;
    private final RowMapper<Member> memberRowMapper = ((rs, rowNum) ->
        new Member(
            rs.getString("username"),
            rs.getString("password")
        ));

    public MemberDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Integer join(Member member) {
        String sql = """
                INSERT INTO member (username, password)
                VALUES (:username, :password);
            """;
        return jdbcClient.sql(sql)
            .param("username", member.getUsername(), Types.VARCHAR)
            .param("password", member.getPassword(), Types.VARCHAR)
            .update();
    }

    public Optional<Member> findByName(String username) {
        String sql = "SELECT * FROM member WHERE username = :username";
        return jdbcClient.sql(sql)
            .param("username", username)
            .query(memberRowMapper)
            .optional();
    }
}

package gift.controller;

import gift.DTO.Member;
import gift.jwt.JwtUtil;
import gift.repository.MemberDao;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {

    private final MemberDao memberDao;
    private final JwtUtil jwtUtil;

    public MemberController(MemberDao memberDao, JwtUtil jwtUtil) {
        this.memberDao = memberDao;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public ResponseEntity<String> memberJoin(@RequestBody Member member) {
        memberDao.join(member);
        return ResponseEntity.ok("추가 성공");
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> memberLogin(@RequestBody Member member) {
        memberDao.findByName(member.getUsername())
            .orElseThrow(IllegalArgumentException::new);

        String token = jwtUtil.createJwt(member.getUsername(), 1000 * 60 * 5);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return ResponseEntity.ok().header("Authorization", token).body("로그인 성공");
//        return ResponseEntity.ok("로그인 성공");
    }
}

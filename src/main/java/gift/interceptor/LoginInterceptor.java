package gift.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.repository.MemberDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberDao memberDao;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginInterceptor(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

//        if (request.getMethod().equals("GET")) {
//            return true;
//        }
//
//        ServletInputStream inputStream = request.getInputStream();
//        String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//
//        System.out.println("body = " + body);
//
//        Member reqMember = objectMapper.readValue(body, Member.class);
//        System.out.println(
//            "reqMember = " + reqMember.getUsername() + ", " + reqMember.getPassword());
        return true;
    }
}

package gift.controller;

import gift.constants.ErrorMessage;
import gift.constants.SuccessMessage;
import gift.dto.Member;
import gift.dto.Product;
import gift.jwt.JwtUtil;
import gift.repository.MemberDao;
import gift.repository.WishlistDao;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberDao memberDao;
    private final WishlistDao wishlistDao;
    private final JwtUtil jwtUtil;

    public MemberController(MemberDao memberDao, WishlistDao wishlistDao, JwtUtil jwtUtil) {
        this.memberDao = memberDao;
        this.wishlistDao = wishlistDao;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 회원가입 <br> 이미 존재하는 email이면 IllegalArgumentException
     *
     * @return ResponseEntity<String>
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody Member member) {
        memberDao.findByEmail(member.getEmail())
            .ifPresent(user -> {
                throw new IllegalArgumentException(ErrorMessage.EMAIL_ALREADY_EXISTS_MSG);
            });
        memberDao.register(member);
        return ResponseEntity.ok().body(SuccessMessage.REGISTER_MEMBER_SUCCESS_MSG);
    }

    /**
     * 로그인 기능.
     *
     * @return 성공 시, 200 OK 응답과 jwt 토큰을 함께 반환
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        memberDao.findByEmail(member.getEmail())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.MEMBER_NOT_EXISTS_MSG));
        memberDao.findByEmailAndPassword(member)
            .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PASSWORD_MSG));

        String token = jwtUtil.createJwt(member.getEmail(), 100 * 60 * 5);
        return ResponseEntity.ok().header("token", token)
            .body(SuccessMessage.LOGIN_MEMBER_SUCCESS_MSG);
    }

    /**
     * 위시 리스트에 상품을 추가.
     */
    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<String> addWishlist(@PathVariable("productId") Long productId,
        HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistDao.insertProduct(email, productId);

        return ResponseEntity.ok("위시 리스트 추가 성공!");
    }

    /**
     * 위치 리스트에 담겨진 상품들을 조회
     */
    @GetMapping("/wishlist")
    @ResponseBody
    public List<Product> wishlist(Model model, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return wishlistDao.findByEmail(email);
    }

    /**
     * 위시 리스트에서 삭제
     */
    @DeleteMapping("/wishlist/{productId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable("productId") Long productId,
        HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistDao.deleteProduct(email, productId);
        return ResponseEntity.ok("위시 리스트에서 삭제되었습니다.");
    }
}

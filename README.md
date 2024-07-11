# Friendly Backend Server

## KOSTA 272기 최종 프로젝트
**개발 기간:** 2024.05.29 - 2024.06.28  
**팀원:** 5명

## 프로젝트 소개
Friendly Backend Server는 비슷한 취미를 가진 사람들이 소규모 그룹을 만들어 사적 및 온라인 커뮤니티에서 활동할 수 있는 서비스를 제공합니다. 사용자는 소그룹 게시판에서 모임을 만들고, 모임에 지원하고, 승인된 사용자들과 채팅할 수 있습니다. 또한 사진 게시판, 익명 게시판, 자유 게시판 등 다양한 게시판 기능을 제공합니다. 이 서버는 종합 웹 프로젝트를 위한 백엔드를 지원합니다.

### 주요 기능 🎁
- **소셜 회원가입 및 JWT 인증:**
  - 소셜 로그인 시 API를 통해 받은 정보를 사용하여 JWT를 구현합니다.
  - 소셜 로그인 API 인증 토큰을 사용하지 않아 보안 부담을 줄입니다.

  ```java
  @Service
  public class JwtService {
      
      public String createJwt(User user) {
          Map<String, Object> claims = new HashMap<>();
          claims.put("id", user.getId());
          claims.put("email", user.getEmail());
          return Jwts.builder()
                      .setClaims(claims)
                      .setIssuedAt(new Date(System.currentTimeMillis()))
                      .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                      .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                      .compact();
      }
  }

# Friendly Backend Server

> **KOSTA 272기 최종 프로젝트** <br/> **개발 기간: 2024.05.29 ~ 2024.06.28** <br/> 팀원: 5명

---

## 프로젝트 소개

- 비슷한 취미를 가진 사람들이 사적 및 온라인 커뮤니티에서 소규모 그룹을 만들 수 있는 서비스
- 소그룹 게시판에서 모임을 만들고, 사용자들이 모임에 지원하고 승인된 사용자들과 채팅 가능
- 사진 게시판, 익명 게시판, 자유 게시판 등 다양한 게시판 기능 제공
- 웹 프로젝트를 위한 백엔드 서버

👉 [프론트엔드 서버](https://github.com/jaehyeongP/friendy_front)

### 개발 환경

![intellij](https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)

### 백엔드

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=Java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0.RELEASE-green?style=for-the-badge&logo=Spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.3.0.RELEASE-green?style=for-the-badge&logo=Spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-3.3.0.RELEASE-green?style=for-the-badge&logo=Spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-5.0-green?style=for-the-badge&logo=Java&logoColor=white)
![Socket.io](https://img.shields.io/badge/Socket.io-black?style=for-the-badge&logo=socket.io&badgeColor=010101)

### 데이터베이스
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)

### API
[![Kakao Pay API](https://img.shields.io/badge/Kakao%20Pay%20API-FFCD00?style=for-the-badge&logo=kakao&logoColor=black)](https://developers.kakao.com/docs/latest/ko/kakaopay)
[![CoolSMS API](https://img.shields.io/badge/CoolSMS%20API-5B9BD5?style=for-the-badge&logo=coolpad&logoColor=white)](https://www.coolsms.co.kr/)
![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)
![KakaoChatBot](https://img.shields.io/badge/kakao_ChatBot-ffcd00.svg?style=for-the-badge&logo=kakaoChatBot&logoColor=000000)

### 커뮤니케이션

![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white)

---

## 주요 기능 🎁

### 🛒 소셜 회원가입 및 JWT 사용
- 소셜 로그인 시 API를 통해 받은 정보를 사용하여 JWT 구현
- 소셜 로그인 API 인증 토큰을 사용하지 않아 보안 부담 감소

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


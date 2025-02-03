document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // 기본 제출 방지

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ id: username, pw: password })
        });

        if (!response.ok) {
            throw new Error("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
        }

        const data = await response.json();
        console.log("JWT 토큰:", data.token);

        localStorage.setItem("token", data.token);
        window.location.href = "/test";
    } catch (error) {
        console.error("로그인 오류:", error);
        console.log("JWT 토큰:", data.token);
        alert(error.message);
    }
});


// 로그인 실패 시 URL에 `?error=true`가 포함되면 경고창 띄우기
const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has("error")) {
    alert("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
}

document.getElementById("kakaoLogin").addEventListener("click", function() {
    window.location.href = "/oauth2/authorization/kakao"; // 카카오 OAuth 엔드포인트
});

document.getElementById("naverLogin").addEventListener("click", function() {
    window.location.href = "/oauth2/authorization/naver"; // 네이버 OAuth 엔드포인트
});
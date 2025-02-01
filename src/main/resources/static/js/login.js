document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({ username, password })  // URL 인코딩하여 전송
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("로그인 실패");
            }
        })
        .then(data => {
            window.location.href = "/test";
        })
        .catch(error => {
            alert("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
        });
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
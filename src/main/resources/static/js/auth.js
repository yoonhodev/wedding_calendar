const fetchWithAuth = async (endPoint, options = {}) => {
    let token = localStorage.getItem("accessToken");

    // accessToken 없으면 refreshToken으로 갱신 시도
    if (!token) {
        console.log("accessToken 없음. refreshToken 확인 후 갱신 시도");
        const newAccessToken = await refreshAccessToken();

        if (!newAccessToken) {
            console.error("refreshToken도 없음. 로그인 페이지로 이동");
            window.location.href = "/login";
            return;
        }

        console.log("새로운 accessToken 발급 완료");
        localStorage.setItem("accessToken", newAccessToken);
        token = newAccessToken;  // 새로 받은 accessToken을 사용
    }

    const headers = {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
        ...options.headers,
    };

    try {
        let response = await fetch(endPoint, { ...options, headers });

        if (response.status === 401) {
            console.warn("accessToken 만료. refreshToken으로 재발급 시도");

            const newAccessToken = await refreshAccessToken();
            if (newAccessToken) {
                console.log("새로운 accessToken 발급 완료");
                localStorage.setItem("accessToken", newAccessToken);

                // 새 accessToken으로 다시 요청
                headers["Authorization"] = `Bearer ${newAccessToken}`;
                response = await fetch(endPoint, { ...options, headers });
            } else {
                console.error("refreshToken도 만료됨. 로그인 페이지로 이동");
                window.location.href = "/login";
                return;
            }
        }

        return response;
    } catch (error) {
        console.error("API 요청 실패:", error);
    }
};

// refreshToken을 사용해 새 accessToken 요청
const refreshAccessToken = async () => {
    try {
        const response = await fetch("/auth/refresh", {
            method: "POST",
            credentials: "include", // refreshToken은 쿠키에서 가져옴
        });

        if (!response.ok) {
            console.error("refreshToken이 유효하지 않음.");
            return null;
        }
        const data = await response.json();

        return data.token;
    } catch (error) {
        console.error("refreshToken 갱신 실패:", error);
        return null;
    }
};

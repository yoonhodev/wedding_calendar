const fetchWithAuth = async (endPoint, options = {}) => {
    const token = localStorage.getItem("accessToken");

    if(!token) {
        console.log("토큰 없음. 로그인 페이지로 이동");
        window.location.href = "/login";
        return;
    }

    const headers = {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
        ...options.headers,
    };

    try {
        const response = await fetch(endPoint, { ...options, headers});

        if(response.status === 401) {
            const newAccessToken = await refreshAccessToken();
            if(newAccessToken) {
                localStorage.setItem("accessToken", newAccessToken);
                return fetchWithAuth(endPoint, options);
            } else {
                window.location.href = "/auth/login";
            }
        }

        return response;
    } catch (error) {
        console.error("API 요청 실패 :", error);
    }
}

const refreshAccessToken = async () => {
    try {
        const response = await fetch("/auth/refresh", {
            method: "POST",
            credentials: "include" // 쿠키 포함
        });

        if (!response.ok) {
            console.log("Refresh Token 만료");
            return null;
        }

        const data = await response.json();
        console.log('data', data);
        return data.token; // newAccessToken
    } catch (error) {
        console.error("토큰 갱신 오류 :", error);
        return null;
    }
}
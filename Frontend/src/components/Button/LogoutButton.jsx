import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import instance from "../../pages/AuthenticaitionPage/Axiosinstance";
import Cookies from "js-cookie";

const LogoutButton = () => {
  const navigate = useNavigate();
  const [logoutError, setLogoutError] = useState(null);

  const handleLogout = async () => {
    const accessToken = Cookies.get("access-token");
    const refreshToken = Cookies.get("refresh-token");

    // accessToken이나 refreshToken이 없는 경우 로그인 안 한 것으로 간주
    if (!accessToken || !refreshToken) {
      return;
    }

    try {
      const response = await instance.post(
        "members/auth/logout/kakao", // 백엔드 엔드포인트
        { refreshToken }, // 요청 바디에 refreshToken 포함
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // 인증 토큰을 헤더에 추가
            "Content-Type": "application/json", // 필요한 경우 Content-Type 설정
          },
        }
      );

      if (response.status === 200) {
        // 로그아웃이 성공적으로 이루어진 경우 쿠키를 삭제하고 로그인 페이지로 리다이렉트
        Cookies.remove("access-token");
        Cookies.remove("refresh-token");
        navigate("/");
      } else {
        setLogoutError("Failed to log out. Please try again.");
      }
    } catch (error) {
      setLogoutError("Error logging out. Please try again.");
    }
  };

  return (
    <div>
      <button onClick={handleLogout}>Logout</button>
      {logoutError && <p>{logoutError}</p>}
    </div>
  );
};

export default LogoutButton;

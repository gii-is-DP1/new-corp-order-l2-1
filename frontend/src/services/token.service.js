import jwt_decode from "jwt-decode";

class TokenService {
    getLocalRefreshToken() {
        const user = JSON.parse(localStorage.getItem("user"));
        return user?.refreshToken;
    }

    getLocalAccessToken() {
        try {
            const jwt = JSON.parse(localStorage.getItem("jwt"));
            return jwt?.accessToken;
        } catch (e) {
            return null;
        }
    }

    updateLocalAccessToken(token) {
        window.localStorage.setItem("jwt", JSON.stringify({accessToken: token}));
    }

    getRoles() {
        return jwt_decode(this.getLocalAccessToken()).authorities;
    }

    getUser() {
        return JSON.parse(localStorage.getItem("user"));
    }

    setUser(user) {
        window.localStorage.setItem("user", JSON.stringify(user));
    }

    removeUser() {
        window.localStorage.removeItem("user");
        window.localStorage.removeItem("jwt");
    }

}

const tokenService = new TokenService();

export default tokenService;

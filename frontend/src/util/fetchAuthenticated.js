import tokenService from "../services/token.service";

export default async function fetchAuthenticated(url, method) {
    return await fetch(url, {
        method: method,
        headers: {
            "Authorization": `Bearer ${tokenService.getLocalAccessToken()}`,
            "Content-Type": "application/json"
        },
    })
}

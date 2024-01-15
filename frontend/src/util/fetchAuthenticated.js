import tokenService from "../services/token.service";

export default async function fetchAuthenticated(url, method, body = undefined) {
    const response = await fetch(url, {
        method: method,
        headers: {
            "Authorization": `Bearer ${tokenService.getLocalAccessToken()}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
    if (!response.ok) {
        throw new Error(await response.json().then(error => error.message))
    } else {
        return response;
    }
}

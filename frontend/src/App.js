import React from "react";
import {Route, Routes} from "react-router-dom";
import jwt_decode from "jwt-decode";
import {ErrorBoundary} from "react-error-boundary";
import tokenService from "./services/token.service";
import SwaggerDocs from "./public/swagger";
import Login from "./auth/login/index";
import Register from "./auth/register/index"

function ErrorFallback({error, resetErrorBoundary}) {
    return (
        <div role="alert">
            <p>Something went wrong:</p>
            <pre>{error.message}</pre>
            <button onClick={resetErrorBoundary}>Try again</button>
        </div>
    )
}

function App() {
    const jwt = tokenService.getLocalAccessToken();

    let roles = []

    if (jwt) {
        roles = getRolesFromJWT(jwt);
    }

    function getRolesFromJWT(jwt) {
        return jwt_decode(jwt).authorities;
    }

    return (
        <div>
            <ErrorBoundary FallbackComponent={ErrorFallback}>
                <Routes>
                    <Route path="/docs" element={<SwaggerDocs/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                </Routes>
            </ErrorBoundary>
        </div>
    );
}

export default App;

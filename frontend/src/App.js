import React from "react";
import {Route, Routes} from "react-router-dom";
import jwt_decode from "jwt-decode";
import {ErrorBoundary} from "react-error-boundary";
import tokenService from "./services/token.service";
import SwaggerDocs from "./public/swagger";
import Match from "./match/Match"
import {MainPage} from "./MainPage";
import {AdminMatches} from "./admin/AdminMatches";
import {AdminModeration} from "./admin/AdminModeration";

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
        <>
            <ErrorBoundary FallbackComponent={ErrorFallback}>
                <Routes>
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/match/:id" element={<Match/>}/>
                    <Route path="/docs" element={<SwaggerDocs/>}/>
                    <Route path="/admin/matches" element={<AdminMatches/>}/>
                    <Route path="/admin/moderation" element={<AdminModeration/>}/>
                </Routes>
            </ErrorBoundary>
        </>
    );
}

export default App;

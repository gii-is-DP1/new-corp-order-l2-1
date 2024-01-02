import React from "react";
import {Route, Routes} from "react-router-dom";
import jwt_decode from "jwt-decode";
import {ErrorBoundary} from "react-error-boundary";
import tokenService from "./services/token.service";
import SwaggerDocs from "./public/swagger";
import Login from "./auth/login/index";
import Register from "./auth/register/index"
import Match from "./match/Match"
import {MainPage} from "./MainPage";
import {AdminMatches} from "./admin/AdminMatches";
import {AdminModeration} from "./admin/AdminModeration";
import {AdminAchievements} from "./admin/AdminAchievements";
import {MainPage} from "./mainPage/MainPage";
import {ProfilePage} from "./profile/ProfilePage";
import Login from "./auth/login";
import Register from "./auth/register";

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
    const roles = jwt ? tokenService.getRoles() : [];

    return (
        <>
            <ErrorBoundary FallbackComponent={ErrorFallback}>
                <Routes>
                    <Route path="/docs" element={<SwaggerDocs/>}/>

                    {jwt && <>
                        <Route path="/" element={<MainPage/>}/>
                        <Route path="/match/:id" element={<Match/>}/>
                        <Route path="/user/:username/:select?" element={<ProfilePage/>}/>
                    </>}

                    {!jwt && <>
                        {["/", "/login"].map(path => <Route path={path} element={<Login/>}/>)}
                        <Route path="/register" element={<Register/>}/>
                    </>}
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/match/:id" element={<Match/>}/>
                    <Route path="/docs" element={<SwaggerDocs/>}/>
                    <Route path="/admin/matches" element={<AdminMatches/>}/>
                    <Route path="/admin/moderation" element={<AdminModeration/>}/>
                    <Route path="/admin/achievements" element={<AdminAchievements/>}/>
                    <Route path="/user/:username/:select?" element={<ProfilePage/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                </Routes>
            </ErrorBoundary>
        </>
    );
}

export default App;

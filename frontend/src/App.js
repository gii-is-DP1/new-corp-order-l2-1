import React from "react";
import {Route, Routes} from "react-router-dom";
import jwt_decode from "jwt-decode";
import {ErrorBoundary} from "react-error-boundary";
import tokenService from "./services/token.service";
import SwaggerDocs from "./public/swagger";
import Match from "./match/Match"
import {AdminMatches} from "./admin/AdminMatches";
import {AdminModeration} from "./admin/AdminModeration";
import {AdminAchievements} from "./admin/AdminAchievements";
import {MainPage} from "./mainPage/MainPage";
import {ProfilePage} from "./profile/ProfilePage";
import Login from "./auth/login";
import Register from "./auth/register";
import {EditAchievements} from "./admin/EditAchievement";
import {MatchStats} from "./stats/MatchStats";
import {AdminMetrics} from "./admin/AdminMetrics";

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
                        <Route path="/match/:matchCode/stats" element={<MatchStats/>}/>

                        <Route path="/user/:username/:select?" element={<ProfilePage/>}/>

                        <Route path="/admin/metrics" element={<AdminMetrics/>}/>
                        <Route path="/admin/matches" element={<AdminMatches/>}/>
                        <Route path="/admin/moderation" element={<AdminModeration/>}/>
                        <Route path="/admin/achievements" element={<AdminAchievements/>}/>
                        <Route path="/admin/achievements/create" element={<EditAchievements/>}/>
                        <Route path="/admin/achievements/:achievementId" element={<EditAchievements/>}/>
                    </>}

                    {!jwt && <>
                        {["/", "/login"].map(path => <Route path={path} element={<Login/>}/>)}
                        <Route path="/register" element={<Register/>}/>
                    </>}
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/match/:id" element={<Match/>}/>
                    <Route path="/docs" element={<SwaggerDocs/>}/>
                    <Route path="/user/:username/:select?" element={<ProfilePage/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                </Routes>
            </ErrorBoundary>
        </>
    );
}

export default App;

import {Text} from "../components/Text";
import {dangerBackground, successBackground} from "./Colors";
import React from "react";

export function buildErrorComponent(errorMessage) {
    return (
        <Text style={{
            display: "flex",
            flex: "1",
            justifyContent: "center",
            color: dangerBackground
        }}>
            {errorMessage}
        </Text>
    )
}

export function buildSuccessComponent(successMessage) {
    return (
        <Text style={{
            display: "flex",
            justifyContent: "center",
            color: successBackground
        }}>
            {successMessage}
        </Text>
    )
}

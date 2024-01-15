import React from "react";
import {ItemArray} from "./ItemArray";

export class SecretObjectives extends ItemArray {
    constructor(data) {
        super(data, SecretObjectives.displaySecretObjective);
    }

    static displaySecretObjective(item, key) {
        return <img height={"200px"} src={item} alt="secret objective"/>
    }
}

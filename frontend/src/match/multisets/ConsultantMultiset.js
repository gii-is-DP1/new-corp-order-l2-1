import {consultant} from "../MatchEnums";
import {Consultant} from "../Consultant";
import React from "react";
import {ItemMultiset} from "./ItemMultiset";

export class ConsultantMultiset extends ItemMultiset {
    constructor(multiset) {
        super(multiset, consultant);
    }

    getConglomerateComponentOfType(value, quantity) {
        return [...Array(quantity)]
            .map(() => <Consultant consultant={value}/>);
    }
}

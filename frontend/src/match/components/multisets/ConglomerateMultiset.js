import {conglomerate} from "../../data/MatchEnums";
import Conglomerate from "../Conglomerate";
import React from "react";
import {ItemMultiset} from "./ItemMultiset";

export class ConglomerateMultiset extends ItemMultiset {
    constructor(multiset) {
        super(multiset, conglomerate);
    }

    getConglomerateComponentOfType(value, quantity) {
        return [...Array(quantity)]
            .map(() => <Conglomerate conglomerate={value}/>);
    }
}

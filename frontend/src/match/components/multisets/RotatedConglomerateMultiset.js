import {conglomerate} from "../../data/MatchEnums";
import Conglomerate from "../Conglomerate";
import React from "react";
import {ItemMultiset} from "./ItemMultiset";
import Rotatable from "../Rotatable";

export class RotatedConglomerateMultiset extends ItemMultiset {
    constructor(multiset) {
        super(multiset, conglomerate);
    }

    getConglomerateComponentOfType(value, quantity) {
        return [...Array(quantity)]
            .map((i) =>
                <Rotatable key={i} isRotated={true}>
                    <Conglomerate conglomerate={value}/>
                </Rotatable>);
    }
}

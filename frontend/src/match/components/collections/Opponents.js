import Conglomerate from "../Conglomerate";
import React from "react";
import {ItemArray} from "./ItemArray";
import {OpponentHqViewer} from "../../game/viewers/OpponentHqViewer";

export class Opponents extends ItemArray {
    constructor(data) {
        super(data, Opponents.displayOpponent);
    }

    static displayOpponent(item, key) {
        return <OpponentHqViewer opponent={item} key={key}/>
    }
}

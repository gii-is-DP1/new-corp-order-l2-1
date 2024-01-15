import Conglomerate from "../Conglomerate";
import React from "react";
import {ItemArray} from "./ItemArray";

export class Conglomerates extends ItemArray {
    constructor(data) {
        super(data, Conglomerates.displayConglomerate);
    }

    static displayConglomerate(item, key) {
        return <Conglomerate key={key} conglomerate={item}/>
    }
}

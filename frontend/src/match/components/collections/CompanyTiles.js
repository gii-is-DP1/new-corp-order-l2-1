import {CompanyTile} from "../CompanyTile";
import React from "react";
import {ItemArray} from "./ItemArray";

export class CompanyTiles extends ItemArray {
    constructor(data) {
        super(data, CompanyTiles.displayCompanyTile);
    }

    static displayCompanyTile(item, key) {
        return <CompanyTile company={item} key={key}/>
    }
}

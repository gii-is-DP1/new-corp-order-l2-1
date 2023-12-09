import {conglomerate, consultant} from "./MatchEnums";
import Conglomerate from "./Conglomerate";
import React from "react";
import {Consultant} from "./Consultant";

export class ItemsMultiset {
    constructor(multiset, items) {
        this.values = items;
        this.keys = Object.keys(items);
        this.multiset = multiset;
        this.valueArray = this.#getValuesArray();
        this.componentArray = this.#getComponentArray();
    }

    #getValuesArray() {
        return this.keys.map((key) => this.#getConglomerateDataOfType(this.values[key], this.multiset[key] ?? 0)).flat(1);
    }

    #getComponentArray() {
        return this.keys.map((key) => this.getConglomerateComponentOfType(this.values[key], this.multiset[key] ?? 0)).flat(1);
    }

    getConglomerateComponentOfType(value, quantity) {
        throw new Error("Method 'getConglomerateComponentOfType' must be implemented.");
    }

    #getConglomerateDataOfType(value, quantity) {
        return [...Array(quantity)].map(() => value);
    }
}

export class ConglomerateMultiset extends ItemsMultiset {
    constructor(multiset) {
        super(multiset, conglomerate);
    }

    getConglomerateComponentOfType(value, quantity) {
        return [...Array(quantity)]
            .map(() => <Conglomerate conglomerate={value}/>);
    }
}

export class ConsultantMultiset extends ItemsMultiset {
    constructor(multiset) {
        super(multiset, consultant);
    }

    getConglomerateComponentOfType(value, quantity) {
        return [...Array(quantity)]
            .map(() => <Consultant consultant={value}/>);
    }
}

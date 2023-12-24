export class ItemMultiset {
    constructor(multiset, items) {
        this.values = items;
        this.keys = Object.keys(items);
        this.multiset = multiset;
        this.values = this.#getValuesArray();
        this.components = this.#getComponentArray();
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

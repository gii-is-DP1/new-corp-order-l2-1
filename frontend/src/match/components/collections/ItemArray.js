export class ItemArray {
    constructor(data, displayFunction) {
        this.values = data;
        this.displayFunction = displayFunction;
        this.#setComponents();
    }

    #setComponents() {
        this.components = this.values.map(this.displayFunction)
    }
}


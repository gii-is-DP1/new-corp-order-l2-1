export class ItemArray {
    constructor(data, displayFunction) {
        this.data = data;
        this.displayFunction = displayFunction;
        this.#setComponents();
    }

    #setComponents() {
        this.components = this.data.map(this.displayFunction)
    }
}


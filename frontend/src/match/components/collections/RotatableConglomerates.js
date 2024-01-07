import Rotatable from "../Rotatable";
import Conglomerate from "../Conglomerate";
import React from "react";

export class RotatableConglomerates {
    constructor(nonRotatedConglomerates, rotatedConglomerates) {
        this.nonRotatedConglomerates = nonRotatedConglomerates;
        this.rotatedConglomerates = rotatedConglomerates;
        this.#setComponents();
    }

    #setComponents() {
        this.components = [
            ...this.nonRotatedConglomerates.map(this.displayConglomerate(false)),
            ...this.rotatedConglomerates.map(this.displayConglomerate(true))
        ]
    }

    displayConglomerate(isRotated) {
        return (item, key) =>
            (
                <Rotatable key={key} isRotated={isRotated}>
                    <Conglomerate key={key} conglomerate={item}/>
                </Rotatable>
            )
    }
}

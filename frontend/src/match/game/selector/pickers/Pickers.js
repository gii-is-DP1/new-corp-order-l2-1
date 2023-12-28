import Selector from "../Selector";
import {selectAtLeastOne, selectAtLeastOneOrZero, selectAtLeastTwo} from "../CanConfirmFunctions";
import {
    onlySelectOfSameColor,
    selectOrthogonallyAdjacentTiles,
    selectQuantity, selectUntilNRemain
} from "../ChangeSelectableItemsFunctions";
import React from "react";

export const conglomerateContainerStyle = {
    display: "flex",
}

export function pickOneCard(from, onConfirm) {
    return <Selector title={"Pick a card"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={onConfirm}
    />;
}

export function optionallyPickCard(from, onConfirm) {
    return <Selector title={"You may pick a card"}
                     selection={from}
                     canConfirm={selectAtLeastOneOrZero}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={onConfirm}
    />;
}

export function pickManyConglomeratesOfTheSameColor(from, onConfirm) {
    return <Selector title={"Pick a conglomerate"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={onlySelectOfSameColor}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={onConfirm}
    />
}

export function pickCompany(from, onConfirm, n) {
    return <Selector title={"Pick Company"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(n)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={onConfirm}
    />;
}

export function pickOrthogonallyAdjacentCompanyTiles(from, onConfirm) {
    return <Selector title={"Pick orthogonally adjacent Companies"}
                     selection={from}
                     canConfirm={selectAtLeastTwo}
                     changeSelectableItems={selectOrthogonallyAdjacentTiles}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={onConfirm}
    />;
}

export function pickConglomeratesToDiscard(from, onConfirm){
    return <Selector title={"Discard conglomerates in excess"}
                     selection={from}
                     canConfirm={selectAtLeastTwo}
                     changeSelectableItems={selectUntilNRemain(6)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={onConfirm}
    />;
}

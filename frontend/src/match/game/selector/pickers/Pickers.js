import Selector from "../Selector";
import {
    selectAtLeastOne,
    selectAtLeastOneOrTwo,
    selectAtLeastOneOrZero,
    selectAtLeastTwo
} from "../CanConfirmFunctions";
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
                     key={{onConfirm}}
    />;
}

export function pickOneorTwoCards(from, onConfirm) {
    return <Selector title={"Pick one or two cards"}
                     selection={from}
                     canConfirm={selectAtLeastOneOrTwo}
                     changeSelectableItems={selectQuantity(2)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={{onConfirm}}
    />;
}

export function pickTwoCards(from, onConfirm) {
    return <Selector title={"Pick two cards"}
                     selection={from}
                     canConfirm={selectAtLeastTwo}
                     changeSelectableItems={selectQuantity(2)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={{onConfirm}}
    />;
}

export function optionallyPickCard(from, onConfirm, onSkip) {
    return <Selector title={"You may pick a card"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     canSkip={true}
                     onSkip={onSkip}
                     key={{onConfirm}}
    />;
}

export function pickManyConglomeratesOfTheSameColor(from, onConfirm) {
    return <Selector title={"Pick a conglomerate"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={onlySelectOfSameColor}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={{onConfirm}}
    />
}



export function pickCompany(from, onConfirm, n) {
    return <CompanySelector
        title={"Pick Company"}
        selection={from}
        canConfirm={selectAtLeastOne}
        changeSelectableItems={selectQuantity(n)}
        onConfirm={onConfirm}
        key={{onConfirm}}
    />;
}

export function pickOneOrTwoCompanies(from, onConfirm) {
    return <CompanySelector
        title={"Pick one or two Companies"}
        selection={from}
        canConfirm={selectAtLeastOneOrTwo}
        changeSelectableItems={onlySelectOfSameColor}
        onConfirm={onConfirm}
        key={{onConfirm}}
    />;
}

export function pickTwoCompanies(from, onConfirm) {
    return <CompanySelector
        title={"Pick one or two Companies"}
        selection={from}
        canConfirm={selectAtLeastTwo}
        changeSelectableItems={selectQuantity(2)}
        onConfirm={onConfirm}
        key={{onConfirm}}
    />;
}

export function pickOrthogonallyAdjacentCompanyTiles(from, onConfirm) {
    return <CompanySelector
        title={"Pick orthogonally adjacent Companies"}
        selection={from}
        canConfirm={selectAtLeastTwo}
        changeSelectableItems={selectOrthogonallyAdjacentTiles}
        onConfirm={onConfirm}
        key={{onConfirm}}
    />;
}

function CompanySelector({title, selection, canConfirm, changeSelectableItems, onConfirm, key}) {
    return <Selector title={title}
                     selection={selection}
                     canConfirm={canConfirm}
                     changeSelectableItems={changeSelectableItems}
                     onConfirm={onConfirm}
                     itemStyle={{maxHeight: "25vh", maxWidth: "25%", flexShrink: 1}}
                     containerStyle={{
                         overflow: "auto",
                         display: "flex",
                         flexWrap: "wrap",
                         flexShrink: 1,
                         maxWidth: "80vh"
                     }}
                     key={{key}}
    />;
}

export function pickConglomeratesToDiscard(from, onConfirm) {
    return <Selector title={"Discard conglomerates in excess"}
                     selection={from}
                     canConfirm={selectAtLeastTwo}
                     changeSelectableItems={selectUntilNRemain(6)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={{onConfirm}}
    />;
}

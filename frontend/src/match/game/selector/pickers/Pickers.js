import Selector from "../Selector";
import {
    selectAtLeastN,
    selectAtLeastOne,
    selectAtLeastOneOrTwo,
    selectAtLeastOneOrZero,
    selectAtLeastTwo, selectAtLeastTwoOrThree, selectAtLeastTwoTakeover
} from "../CanConfirmFunctions";
import {
    onlySelectOfSameColor,
    selectOrthogonallyAdjacentTiles, selectOrthogonallyAdjacentTilesWithColors,
    selectQuantity, selectUntilNRemain
} from "../ChangeSelectableItemsFunctions";
import React from "react";
import {conglomerate, getConglomerateName} from "../../../data/MatchEnums";
import Conglomerate from "../../../components/Conglomerate";

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

export function pickNCards(title, from, onConfirm, n) {
    return <Selector title={title}
                     selection={from}
                     canConfirm={selectAtLeastN(n)}
                     changeSelectableItems={selectQuantity(n)}
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

export function pickCompanyWithSameColor(from, onConfirm, n, conglomerateType, companies) {
    return <CompanySelector
        title={"Pick Company"}
        selection={from}
        selectableElements={[...Array(from.length).keys()].filter((i) => getConglomerateName(companies[i].type) === conglomerateType)}
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

export function pickTwoOrThreeCompanies(from, onConfirm) {
    return <CompanySelector
        title={"Pick two or three companies"}
        selection={from}
        canConfirm={selectAtLeastTwoOrThree}
        changeSelectableItems={selectQuantity(3)}
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

//selectableElements = {[...Array(from.length).keys()].filter((i) => getConglomerateName(companies[i].type) === conglomerateType)}

export function pickOrthogonallyAdjacentCompanyTilesWithColors(from, onConfirm, conglomerateType, companies) {
    console.log(conglomerateType)
    return <CompanySelector
        title={"Pick orthogonally adjacent Companies"}
        help={
            <>
                <p> You have selected {conglomerateType} </p>
            </>
        }
        selection={from}
        canConfirm={selectAtLeastTwoTakeover(conglomerateType, companies)}
        changeSelectableItems={selectOrthogonallyAdjacentTilesWithColors(companies)}
        onConfirm={onConfirm}
        key={{onConfirm}}
    />;
}

function CompanySelector({
                             title,
                             help = <></>,
                             selection,
                             canConfirm,
                             changeSelectableItems,
                             onConfirm,
                             key,
                             selectableElements = [...Array(selection.length).keys()]
                         }) {
    return <Selector title={title}
                     help={help}
                     selection={selection}
                     canConfirm={canConfirm}
                     changeSelectableItems={changeSelectableItems}
                     selectableElements={selectableElements}
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

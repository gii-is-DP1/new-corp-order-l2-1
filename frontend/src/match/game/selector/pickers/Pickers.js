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

export function pickOneCard(from, onConfirm, selectableElements = [...Array(from.length).keys()]) {
    return <Selector title={"Pick a card"}
                     selection={from}
                     selectableElements={selectableElements}
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

export function optionallyPickCard(from, onConfirm, onSkip, selectableElements = [...Array(from.length).keys()]) {
    return <Selector title={"You may pick a card"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     selectableElements={selectableElements}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     canSkip={true}
                     onSkip={onSkip}
                     key={{onConfirm}}
    />;
}

export function pickManyConglomeratesOfTheSameColor(from, onConfirm, selectableElements = [...Array(from.length).keys()]) {
    return <Selector title={"Pick one or many conglomerates of the same color"}
                     selection={from}
                     selectableElements={selectableElements}
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

export function pickTwoOrThreeCompanies(from, onConfirm, companies) {
    return <CompanySelector
        title={"Broadcast Network Ability"}
        help={
            <>
                <h2>Explanations</h2>
                <p>You can move 2 conglomerates from the source company to one or two others</p>
                <h2>SOURCE COMPANY</h2>
                <p>the source company is the first you select</p>
                <p>it must have at lest two conglomerates</p>
                <h2>OTHER COMPANIES</h2>
                <p>the comapanies agents must be of the same color</p>
            </>
        }
        selection={from}
        canConfirm={selectAtLeastTwoOrThree(companies)}
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

export function pickOrthogonallyAdjacentCompanyTilesWithColors(from, onConfirm, conglomerateType, conglomerateQuantity, companies) {
    return <CompanySelector
        title={"Pick orthogonally adjacent Companies"}
        help={
            <>
                <h2>Pay attention to the color</h2>
                <p> You have selected {conglomerateType}, therefore the first company ability you select must contain
                    agents of type {conglomerateType}! </p>
                <h2>Pay attention to the agents</h2>
                <p> You have selected {conglomerateQuantity} conglomerates, therefore the first company ability you
                    select must contain at least {conglomerateQuantity} agents!</p>
            </>
        }
        selection={from}
        canConfirm={selectAtLeastTwoTakeover(conglomerateType, conglomerateQuantity, companies)}
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

import {getConglomerateName} from "../../data/MatchEnums";

export function selectAtLeastOne(selection, selectedElements) {
    return selectedElements.length >= 1;
}

export function selectAtLeastOneOrZero(selection, selectedElements) {
    return selectedElements == null || selectedElements.length <= 1;
}

export function selectAtLeastTwo(selection, selectedElements) {
    return selectedElements.length >= 2;
}

export function selectAtLeastTwoTakeover(conglomerateType, companies) {
    return (selection, selectedElements) => {
        return selectedElements.length >= 2 && getConglomerateName(companies[selectedElements[0]].type) === conglomerateType;
    }
}

export function selectAtLeastTwoOrThree(selection, selectedElements) {
    return selectedElements.length >= 2 && selectedElements.length <= 3;
}

export function selectAtLeastOneOrTwo(selection, selectedElements) {
    return selectedElements.length >= 1 && selectedElements.length <= 2;
}

export function selectAtLeastN(n) {
    return (selection, selectedElements) => {
        return selectedElements.length === n;
    }
}

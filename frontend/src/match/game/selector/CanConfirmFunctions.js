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

export function selectAtLeastTwoTakeover(conglomerateType, conglomerateQuantity, companies) {
    return (selection, selectedElements) => {
        if(selectedElements.length >= 1)
        {
        }

        return selectedElements.length >= 2 && getConglomerateName(companies[selectedElements[0]].type) === conglomerateType && companies[selectedElements[0]].agents >= conglomerateQuantity;
    }
}

export function selectAtLeastTwoOrThree(companies) {
    return (selection, selectedElements) =>
    {
        return selectedElements.length >= 2 &&
            selectedElements.length <= 3 &&
            companies[selectedElements[0]].agents >= 2 &&
            companies[selectedElements[0]].type === companies[selectedElements[1]].type &&
            (selectedElements.length < 3 || companies[selectedElements[0]].type === companies[selectedElements[2]].type);
    }
}

export function selectAtLeastOneOrTwo(selection, selectedElements) {
    return selectedElements.length >= 1 && selectedElements.length <= 2;
}

export function selectAtLeastN(n) {
    return (selection, selectedElements) => {
        return selectedElements.length === n;
    }
}

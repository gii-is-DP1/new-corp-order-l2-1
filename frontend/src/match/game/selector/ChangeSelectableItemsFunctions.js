import {getConglomerateName} from "../../data/MatchEnums";

export function onlySelectOfSameColor(selection, selectedElements, selectableElements, setSelectableItems) {
    if (selectedElements.length === 1) {
        const type = selection[selectedElements[0]].props.conglomerate;
        setSelectableItems(selectableElements.filter((i) => selection[i].props.conglomerate === type));
    }
    if (selectedElements.length === 0)
        setSelectableItems(selectableElements);
}

export const selectAny = (selection, selectedElements, selectableElements, setSelectableItems) => {
}

export function selectQuantity(n) {
    return (selection, selectedElements, selectableElements, setSelectableItems) => {
        if (selectedElements.length === n)
            setSelectableItems(selectedElements);
        else setSelectableItems(selectableElements);
    }
}

export function selectOrthogonallyAdjacentTiles() {
    return (selection, selectedElements, selectableElements, setSelectableItems) =>
    {
        if (selectedElements.length === 1) {
            const index = selectedElements[0];
            const selectedX = index % 4;
            const selectedY = Math.floor(index / 4);
            selectableElements = selectableElements.filter((i) => {
                const x = i % 4;
                const y = Math.floor(i / 4);
                const difference = Math.abs(selectedX - x) + Math.abs(selectedY - y);
                return difference === 1 || difference === 0;
            });
            setSelectableItems(selectableElements);
        } else if (selectedElements.length === 2)
            setSelectableItems(selectedElements);
        else setSelectableItems(selectableElements);
    }
}
export function selectOrthogonallyAdjacentTilesWithColors(companies) {
    return (selection, selectedElements, selectableElements, setSelectableItems) =>
    {
        if (selectedElements.length === 1) {
            const index = selectedElements[0];
            const selectedX = index % 4;
            const selectedY = Math.floor(index / 4);
            selectableElements = selectableElements.filter((i) => {
                const x = i % 4;
                const y = Math.floor(i / 4);
                const difference = Math.abs(selectedX - x) + Math.abs(selectedY - y);
                return difference === 1 || difference === 0;
            });
            const selectedConglomerateType = getConglomerateName(companies[index].type);
            selectableElements = selectableElements.filter((i) =>
                i === index || getConglomerateName(companies[i].type) !== selectedConglomerateType);

            setSelectableItems(selectableElements);
        } else if (selectedElements.length === 2)
            setSelectableItems(selectedElements);
        else setSelectableItems(selectableElements);
    }
}


export function selectUntilNRemain(n){
    return (selection, selectedElements, selectableElements, setSelectableItems) => {
        if (selectedElements.length === selection.length-n)
            setSelectableItems(selectedElements);
        else setSelectableItems(selectableElements);
    }
}

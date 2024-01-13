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

export function selectOrthogonallyAdjacentTiles(selection, selectedElements, selectableElements, setSelectableItems) { //TODO: make it works on a 4x3 grid
    //setSelectableItems(selectedElements);
    console.log("CHIAMANDO FUNZIONE")
    return;
    if (selectedElements.length === 1) {
        const index = selectedElements[0];
        const selectedX = index % 4;
        const selectedY = index / 4;
        selectableElements = selectableElements.filter((i) => {
            const x = i % 4;
            const y = i / 4;
            const difference = Math.abs(selectedX - x) + Math.abs(selectedY - y);
            return difference === 1 || difference === 0;
        });
        setSelectableItems(selectableElements);
    } else if (selectedElements.length === 2)
        setSelectableItems(selectedElements);
    else setSelectableItems(selectableElements);
}

export function selectUntilNRemain(n){
    return (selection, selectedElements, selectableElements, setSelectableItems) => {
        if (selectedElements.length === selection.length-n)
            setSelectableItems(selectedElements);
        else setSelectableItems(selectableElements);
    }
}
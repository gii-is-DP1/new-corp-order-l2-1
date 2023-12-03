export function onlySelectOfSameColor(selection, selectedElements, selectableElements, setSelectableItems){
    if(selectedElements.length === 1)
    {
        const type = selection[selectedElements[0]].props.conglomerate;
        setSelectableItems(selectableElements.filter((i) => selection[i].props.conglomerate === type));
    }
    if(selectedElements.length === 0)
        setSelectableItems(selectableElements);
}

export const selectAny = (selection, selectedElements, selectableElements, setSelectableItems) => {}

export function selectQuantity(n){
    return  (selection, selectedElements, selectableElements, setSelectableItems) => {
        if(selectedElements.length === n)
            setSelectableItems(selectedElements);
        else setSelectableItems(selectableElements);
    }
}

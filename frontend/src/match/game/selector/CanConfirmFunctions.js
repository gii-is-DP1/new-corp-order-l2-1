export function selectAtLeastOne(selection, selectedElements){
    return selectedElements.length >= 1;
}

export function selectAtLeastOneOrZero(selection, selectedElements){
    return selectedElements == null || selectedElements.length <= 1;
}

export function selectAtLeastTwo(selection, selectedElements){
    return selectedElements.length >= 2;
}

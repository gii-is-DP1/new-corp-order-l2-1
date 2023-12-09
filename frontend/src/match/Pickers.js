import Selector from "./Selector/Selector";
import {selectAtLeastOne} from "./Selector/OnConfirmFunctions";
import {onlySelectOfSameColor, selectQuantity} from "./Selector/ChangeSelectableItemsFunctions";
import React from "react";

const conglomerateContainerStyle = {
    display: "flex",
}

export function pickOneCard(from, onConfirm, key) {
    return <Selector title={"Pick a conglomerate"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={key}
    />;
}

export function pickManyConglomeratesOfTheSameColor(from, onConfirm, key) {
    return <Selector title={"Pick a conglomerate"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={onlySelectOfSameColor}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={key}
    />
}

export function pickCompany(from, onConfirm, key,n) {
    return <Selector title={"Pick Company"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(n)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
                     key={key}
    />;
}

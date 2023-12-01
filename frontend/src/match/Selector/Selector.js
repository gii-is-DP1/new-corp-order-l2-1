import {Children, useState} from "react";
import Button, {buttonContexts, buttonStyles} from "../../components/Button";

export default function Selector({
                                     title,
                                     subtitle,
                                     selection,
                                     selectableElements,
                                     canConfirm,
                                     changeSelectableItems,
                                     onConfirm,
                                     containerStyle,
                                     itemStyle
                                 }) {
    let [selectedElements, setSelectedElements] = useState([]);
    const [selectableItems, setSelectableItems] = useState(selectableElements);
    return <>
        <h1>{title} </h1>
        <h2>{subtitle}</h2>
        <div style={containerStyle}>

            {selection.map((item, i) =>
                <Selectable
                    style={itemStyle}
                    item={item}
                    isSelectable={selectableItems.includes(i)}
                    isSelected={selectedElements.includes(i)}
                    onClick={() => {
                        if (!selectableItems.includes(i))
                            return;

                        if (selectedElements.includes(i))
                            selectedElements = selectedElements.filter((j) => j !== i);
                        else
                            selectedElements = [...selectedElements, i];

                        setSelectedElements(selectedElements);

                        changeSelectableItems(selection, selectedElements, selectableElements, setSelectableItems);
                    }}
                />
            )}
        </div>
        <Button buttonContext={buttonContexts.light} buttonStyle={buttonStyles.primary}
                disabled={!canConfirm(selection, selectedElements)}
                onClick={() => onConfirm(selectedElements)}
        >
            Confirm
        </Button>
    </>
}

export function Selectable({item, isSelectable, isSelected, onClick, style}) {
    const nonSelectableOpacity = 0.6;
    return <div
        onClick={onClick}
        style={{
            borderColor: "black",
            borderStyle: "solid",
            borderWidth: isSelected ? 5 : 0,
            cursor: isSelectable ? "pointer" : "",
            opacity: isSelectable ? 1 : nonSelectableOpacity,
            ...style
        }}>
        {item}
    </div>
}

import {useState} from "react";
import Button, {buttonContexts, buttonStyles} from "../../../components/Button";
import {Selectable} from "./Selectable";

export default function Selector({
                                     title,
                                     subtitle,
                                     selection,
                                     selectableElements = [...Array(selection.length).keys()],
                                     canConfirm,
                                     changeSelectableItems,
                                     onConfirm,
                                     containerStyle,
                                     itemStyle,
                                     canSkip = false,
                                     onSkip = () => {
                                     }
                                 }) {
    let [selectedElements, setSelectedElements] = useState([]);
    const [selectableItems, setSelectableItems] = useState(selectableElements);
    return <>
        <div style={{
            display: "flex",
            flexDirection: "column",
            position: "absolute",
            left: 0,
            right: 0,
            maxWidth: "fit-content",
            margin: "auto"
        }}>
            <h1 style={{textAlign: "center"}}>{title} </h1>
            <h2>{subtitle}</h2>
            <div style={containerStyle}>
                {selection.map((item, i) =>
                    <Selectable
                        key={i}
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
            {canSkip
                ? <Button buttonContext={buttonContexts.light} buttonStyle={buttonStyles.primary}
                          disabled={selectedElements.length > 0}
                          onClick={() => onSkip([])}
                >
                    Confirm
                </Button>
                : <></>
            }
        </div>
    </>
}


export function Selectable({item, isSelectable, isSelected, onClick, style}) {
    const nonSelectableOpacity = 0.6;
    return <div className={""}
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

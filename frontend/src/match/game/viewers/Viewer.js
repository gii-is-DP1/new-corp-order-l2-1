import React, {useState} from "react";
import Button, {buttonContexts, buttonStyles, ButtonType} from "../../../components/Button";

export function Viewer({title, items, containerStyle, itemsStyle, isVisible, setVisible, canQuit = true}) {
    return <> {isVisible
        ? <div style={{
            zIndex: 100,
            position: "fixed",
            backgroundColor: "black",
            left: 0,
            top: 0,
            bottom: 0,
            right: 0,
            width: "100vw",
            height: "100vh"
        }}>
            <div style={{display: "flex", justifyContent: "space-between"}}>
                <h1>{title}</h1>
                {canQuit
                    ? <img src="/svg/cancel-icon.svg" alt="cancel" onClick={() => setVisible(false)}/>
                    : <></>
                }
            </div>

            <div style={containerStyle}>
                {items.map(item =>
                    <Viewable item={item} style={itemsStyle}/>
                )}
            </div>


        </div>
        : <></>
    }
    </>
}

export function ViewerContainer({title, buttonContent, items, containerStyle, itemsStyle}) {
    const [visible, setVisible] = useState(false)
    return <>
        <Button buttonType={ButtonType.primary} onClick={() => setVisible(true)}>
            {buttonContent}
        </Button>
        <Viewer title={title}
                items={items}
                containerStyle={containerStyle}
                itemsStyle={itemsStyle}
                isVisible={visible}
                setVisible={setVisible}
        />
    </>
}

export function Viewable({item, style}) {
    return <div style={style}>
        {item}
    </div>
}

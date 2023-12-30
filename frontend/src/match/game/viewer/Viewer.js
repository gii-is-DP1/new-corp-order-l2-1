import React, {useState} from "react";
import Button, {buttonContexts, buttonStyles} from "../../../components/Button";

function Viewer({title, items, containerStyle, itemsStyle, isVisible, setVisible}) {
    return <> {isVisible
        ? <div style={{position:"absolute", backgroundColor:"black", width:"100%", height:"100vh", left:0, top:0}}>
            <h1>{title}</h1>
            <div style={containerStyle}>
                {items.map(item =>
                    <Viewable item={item} style={itemsStyle}/>
                )}
            </div>

            <img src="/svg/cancel-icon.svg" alt="cancel" onClick={() => setVisible(false)}/>
        </div>
        : <></>
    }
    </>
}

export function ViewerContainer({title,buttonContent, items, containerStyle, itemsStyle}) {
    const [visible, setVisible] = useState(false)
    return <>
        <Button buttonStyle={buttonStyles.primary} buttonContext={buttonContexts.light} onClick={() => setVisible(true)}>
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

function Viewable({item, style}) {
    return <div style={style}>
        {item}
    </div>
}

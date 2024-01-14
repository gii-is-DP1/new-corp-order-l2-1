import {useEffect, useRef, useState} from 'react';
import * as Colors from "../util/Colors";
import SendIcon from '@mui/icons-material/Send';

export default function TextInput({name, placeholder, onClick, type, style, minValue, maxValue, defaultValue}) {
    const [inputValue, setInputValue] = useState(defaultValue?(defaultValue):(""));
    const inputRef = useRef(null);

    const divStyle = {
        backgroundColor: Colors.gray,
        display: "flex",
        flexDirection: "column",
        alignItems: "normal",
        borderRadius: "0.4em",
        border: "none"
    }

    const inputStyle = {
        color: Colors.black,
        placeholderColor: Colors.black,
        backgroundColor: "transparent",
        padding: "15px",
        outline: "none",
        border: "none",
        flex: 1,
        fontFamily: "DIN Next Slab Pro",
    };

    const buttonStyle = {
        backgroundColor: "transparent",
        border: "none",
        padding: "10px"
    }


    const handleEvent = async (e) => {
        if (inputValue !== "" && (e.key === 'Enter' || e.type === 'click') && onClick) {
            e.preventDefault();
            try {
                await onClick(inputValue);
            } catch (error) {
                console.log(error)
            }
        }
    };

    return (
        <div style={{...divStyle, ...style}}>
            <div style={{display: "flex", flexDirection: "row", alignItems: "normal"}}>
                <input type={type}
                       id={name}
                       name={name}
                       placeholder={placeholder}
                       style={inputStyle}
                       value={inputValue}
                       onChange={e => setInputValue(e.target.value)}
                       ref={inputRef}
                       onKeyDown={handleEvent}
                       min={minValue}
                       max={maxValue}
                />
                {onClick &&
                    <button onClick={handleEvent} style={buttonStyle}>
                        <SendIcon/>
                    </button>}
            </div>
        </div>
    )
}

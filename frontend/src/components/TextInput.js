import {useState} from 'react';
import * as Colors from "../util/Colors";

export default function TextInput({name, placeholder, onClick}) {

    const [inputValue, setInputValue] = useState('');


    const divStyle = {
        backgroundColor: Colors.gray,
        display: "flex",
        alignItems: "center",
        height: "50px",
        width: "300px",
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
        flex: 1
    };

    const buttonStyle = {
        backgroundColor: "transparent",
        border: "none",
        height: "50px",
        width: "50px"
    }


    return (
         <div style={divStyle}>
            <input name={name} placeholder={placeholder} style={inputStyle} value={inputValue}
                   onChange={e => setInputValue(e.target.value)}/>
            {onClick && <button onClick={onClick} style={buttonStyle}>></button>}
         </div>
)
}

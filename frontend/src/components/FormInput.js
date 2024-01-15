import {useState} from "react";
import * as Colors from "../util/Colors";
import SendIcon from "@mui/icons-material/Send";
import TextInput from "./TextInput";

export default function FormInput({name, placeholder, type, textInputStyle, labelStyle, minValue, maxValue, defaultValue, label}) {

    const textStyle = {
        margin: 0,
        textTransform: "uppercase",
        fontFamily: "DIN Next Slab Pro",
    }

    return (
        <div>
            <label style={{...textStyle, ...labelStyle}} htmlFor={name}>
                {label || name}
            </label>
            <TextInput name={name}
                       placeholder={placeholder}
                       type={type}
                       style={textInputStyle}
                       minValue={minValue}
                       maxValue={maxValue}
                       defaultValue={defaultValue}
            />
        </div>

    )
}

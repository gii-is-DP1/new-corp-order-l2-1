import {useState} from "react";
import * as Colors from "../util/Colors";
import SendIcon from "@mui/icons-material/Send";
import TextInput from "./TextInput";

export default function FormInput({name, placeholder, type}) {

    const textStyle = {
        margin: 0,
        textTransform: "uppercase",
        fontFamily: "DIN Next Slab Pro",

    }

    return (
        <div>
            <label style={textStyle} htmlFor={name}>
                {name}
            </label>
            <TextInput name={name} placeholder={placeholder} type={type}/>
        </div>

    )
}

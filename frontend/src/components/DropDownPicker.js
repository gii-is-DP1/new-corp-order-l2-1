import React, { useState } from 'react';
import {black, white, gray} from "../util/Colors";
import * as Colors from "../util/Colors";

function DropdownPicker({ options, style, onChange, defaultValue }) {

    const defaultStyle = {
        color: black,
        placeholderColor: black,
        backgroundColor: gray,
        padding: "15px",
        outline: "none",
        border: "none",
        flex: 1,
        fontFamily: "DIN Next Slab Pro",
        borderRadius: "0.4em",
    }

    return (
        <div>
            <select style={{...defaultStyle, ...style}} defaultValue={defaultValue}
                onChange={e => onChange(e.target.value)}
            >
                <option value="">Select an option</option>
                {
                    options.map((option, index) => (
                        <option key={index} value={option.value}>{option.label}</option>
                    ))
                }
            </select>
        </div>
    );
}

export default DropdownPicker;

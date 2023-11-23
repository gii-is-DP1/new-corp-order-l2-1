import React from 'react';
export default function TextInput({name, placeholder}){

    const style = {
        backgroundColor: "#9b9a9a",
        color:"#252323",
        placeholderColor:"#252323",
        borderRadius:"0.4em",
        borderWidth:"0px",
        paddingTop:"1%",
        paddingBottom:"1%",
        paddingLeft:"2%",
        paddingRight:"5%"
    };

    return(
        <input name={name} placeholder={placeholder} style={style}/>
    )
}

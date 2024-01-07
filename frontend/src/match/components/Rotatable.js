import React from "react";

export default function Rotatable({children, isRotated}) {
    return <div style={{transform: isRotated ? "rotate(90deg)" : ""}}>
        {children}
    </div>
}

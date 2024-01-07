import {useNavigate} from "react-router-dom";

export default function GoBackButton() {
    const style = {
        paddingLeft: "5%",
    }
    let navigate = useNavigate();
    return <div
        style={style}
        onClick={() => navigate(-1)}>
        <GoBackButtonSvg/>
    </div>
}

const GoBackButtonSvg = () => <img  width="48" height="48" className="bi bi-box-arrow-left" src="/svg/back-button.svg" alt="back"/>;

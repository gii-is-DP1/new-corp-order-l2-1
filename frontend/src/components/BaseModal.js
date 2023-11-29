import Modal from "react-bootstrap/Modal";
import {black, white} from "../util/Colors";
import Button, {buttonContexts, buttonStyles} from "./Button";

export default function BaseModal({title, state, body, onContinue = () => {}}) {
    const [show, setShow] = state;
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const background = {
        backgroundColor:black,
    }

    return (
        <>
            <Modal
                show={show}
                onHide={handleClose}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header style={{backgroundColor:black, display: "block"}}>
                    <Modal.Title>
                        <span>{title}</span>
                        <img style={{float:"right", cursor:"pointer"} } alt="X" onClick={handleClose} src = "/svg/cancel-icon.svg" />
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body style={{backgroundColor:white, color: black}}>
                    {body}
                </Modal.Body>
                <Modal.Footer style={{backgroundColor:white,display:"flex",justifyContent:"space-between"}}>
                    <Button buttonStyle={buttonStyles.primary} buttonContext={buttonContexts.light} onClick={() => {handleClose(); onContinue()}}>Ok</Button>
                    <Button buttonStyle={buttonStyles.secondary} buttonContext={buttonContexts.light}  onClick={handleClose}>Cancel</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

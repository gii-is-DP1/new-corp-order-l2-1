import Modal from "react-bootstrap/Modal";
import {black, white} from "../util/Colors";
import Button, {ButtonType} from "./Button";

export default function BaseModal({title, state, body, onContinue = () => {}, canCancel = true}) {
    const [show, setShow] = state;
    const closeModal = () => setShow(false);
    const CancelButton = () => canCancel
        ? <Button buttonType={ButtonType.secondaryLight} onClick={closeModal}>Cancel</Button>
        : <></>;

    const XButton = () => canCancel
        ? <img style={{float:"right", cursor:"pointer"}} alt="X" onClick={closeModal} src = "/svg/cancel-icon.svg" />
        : <></>


    return (
        <>
            <Modal
                show={show}
                onHide={closeModal}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header style={{backgroundColor:black, display: "block"}}>
                    <Modal.Title>
                        <span style={{color:white}}>{title}</span>
                        <XButton/>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body style={{backgroundColor:white, color: black}}>
                    {body}
                </Modal.Body>
                <Modal.Footer style={{backgroundColor:white,display:"flex",justifyContent:"space-between"}}>
                    <Button buttonType={ButtonType.primary} onClick={() => {
                        closeModal();
                        onContinue()
                    }}>
                       Ok
                    </Button>
                    <CancelButton/>
                </Modal.Footer>
            </Modal>
        </>
    );
}

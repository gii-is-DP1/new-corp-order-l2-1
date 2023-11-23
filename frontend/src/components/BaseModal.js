import BaseButton, {black, buttonContexts, buttonStyles, white} from "./Button";
import Modal from "react-bootstrap/Modal";
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
                    <Modal.Title><span>{title}</span> <svg style={{float:"right"}}  onClick={handleClose} width="41" height="41" viewBox="0 0 41 41" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M20.4041 2.53674C19.4793 2.53674 18.5545 2.8692 17.7962 3.56911L2.9999 17.5674C1.53877 18.9322 1.53877 21.1369 2.9999 22.5017L17.7962 36.5C19.2388 37.8823 21.5693 37.8823 23.0119 36.5L37.8082 22.5017C39.2693 21.1369 39.2693 18.9322 37.8082 17.5674L23.0119 3.56911C22.2536 2.8692 21.3288 2.53674 20.4041 2.53674Z" fill="#F8F8F8"/>
                        <path d="M28.2787 15.08H26.4427L22.3867 20.768L26.2267 26.12H28.0387V29H21.5107V26.12H22.3987L20.5027 23.432L18.6067 26.12H19.4947V29H12.9667V26.12H14.7787L18.6187 20.768L14.5507 15.08H12.7267V12.2H19.2547V15.08H18.3667L20.5027 18.104L22.6387 15.08H21.7507V12.2H28.2787V15.08Z" fill="#2C2C2C"/>
                    </svg>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body style={{backgroundColor:white, color: black}}>
                    {body}
                </Modal.Body>
                <Modal.Footer style={{backgroundColor:white,display:"flex",justifyContent:"space-between"}}>
                    <BaseButton buttonStyle={buttonStyles.primary} buttonContext={buttonContexts.light} onClick={() => {handleClose(); onContinue()}}>Ok</BaseButton>
                    <BaseButton buttonStyle={buttonStyles.secondary} buttonContext={buttonContexts.light}  onClick={handleClose}>Cancel</BaseButton>
                </Modal.Footer>
            </Modal>
        </>
    );
}

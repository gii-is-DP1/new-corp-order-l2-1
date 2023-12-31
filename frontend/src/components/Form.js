import Button, {ButtonType as buttonStyles} from "./Button";

export default function Form(handleSubmit, formInputs, buttonText){

    return (
    <form onSubmit={handleSubmit}>
        {formInputs}
        <Button buttonColor={buttonStyles.primary} type="submit" buttonText={buttonText} ></Button>
    </form>
    )
}

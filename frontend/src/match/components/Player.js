import ProfilePicture from "../../components/ProfilePicture";
import BaseButton, {buttonContexts, buttonStyles} from "../../components/Button";
import css from "./components.module.css"

export function Player({data, isAdmin, onKick}) {

    const usernameParagraph = data.username != null
        ? <p>{data.username}</p>
        : <></>;

    return (
        <div className={css.player}>
            <ProfilePicture url={data.propic} isTransparent={data.username == null}/>
            {usernameParagraph}
            {isAdmin && data.username != null
                ?
                <BaseButton buttonStyle={buttonStyles.primary} buttonContext={buttonContexts.light}
                            onClick={() => onKick()}>Kick</BaseButton>
                : <></>
            }
        </div>
    );
}

import ProfilePicture from "../../components/ProfilePicture";
import BaseButton, {ButtonType} from "../../components/Button";
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
                <BaseButton buttonType={ButtonType.primary} onClick={() => onKick()}>Kick</BaseButton>
                : <></>
            }
        </div>
    );
}

import ProfilePicture from "../components/ProfilePicture";
import BaseButton, {buttonContexts, buttonStyles} from "../components/Button";

export function Player({data, isAdmin, onKick}) {
    data = data ?? {};
    const style = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    }

    let usernameParagraph;
    if (data.username != null)
        usernameParagraph = <p>{data.username}</p>;

    return (
        <div style={style}>
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

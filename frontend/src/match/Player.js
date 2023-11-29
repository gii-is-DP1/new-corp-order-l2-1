import ProfilePicture from "../components/ProfilePicture";
import BaseButton, {buttonContexts, buttonStyles} from "../components/Button";

export function Player({player, isAdmin, onKick}) {
    player = player ?? {};
    const style = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
    }

    let usernameParagraph;
    if (player.username != null)
        usernameParagraph = <p>{player.username}</p>;

    return (
        <div style={style}>
            <ProfilePicture url={player.propic} isTransparent={player.username == null}/>
            {usernameParagraph}
            {isAdmin && player.username != null
                ?
                <BaseButton buttonStyle={buttonStyles.primary} buttonContext={buttonContexts.light}
                            onClick={() => onKick()}>Kick</BaseButton>
                : <></>
            }
        </div>
    );
}

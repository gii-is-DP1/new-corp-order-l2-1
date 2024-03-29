import AppNavbar from "../AppNavbar";
import {black, orange, white} from "../util/Colors";
import Button, {ButtonType} from "../components/Button";
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import FormInput from "../components/FormInput";
import DropDownPicker from "../components/DropDownPicker";
import {Text} from "../components/Text";
import {Title} from "../components/Title";
import fetchAuthenticated from "../util/fetchAuthenticated";

export function EditAchievements() {
    const navigate = useNavigate()
    const [achievementData, setAchievementData] = useState(null)
    const [isLoaded, setIsLoaded] = useState(false);
    const [selectedValue, setSelectedValue] = useState("");
    const [message, setMessage] = useState();
    const [title, setTitle] = useState('');
    const [buttonText, setButtonText] = useState('');
    const {achievementId} = useParams();

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    const labelStyle = {
        color: white,
        fontSize: "20px",
        textTransform: "uppercase"
    }

    const buttonStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        margin: "10px"
    }

    const textStyle = {
        textTransform: "uppercase",
        fontFamily: "DIN Next Slab Pro",
        color: white,
        fontSize: "20px"
    }

    const titleStyle = {
        fontSize: "40px",
        color: white,
        marginBottom: '20px'
    }

    const statsOptions = [
        {value: 'GAMES_LOST', label: 'GAMES LOST'},
        {value: 'GAMES_TIED', label: 'GAMES TIED'},
        {value: 'GAMES_WON', label: 'GAMES WON'},
        {value: 'TIMES_PLAYED', label: 'TIMES PLAYED'},
        {value: 'TIMES_PLOTTED', label: 'TIMES PLOTTED'},
        {value: 'TIMES_INFILTRATED', label: 'TIMES INFILTRATED'},
        {value: 'TIMES_TAKEN_OVER', label: 'TIMES TAKEN OVER'},
        {value: 'CONSULTANT_STATS_USED', label: 'CONSULTANT STATS USED'},
        {value: 'ABILITIES_USED', label: 'ABILITIES USED'},
        {value: 'FINAL_SCORE', label: 'FINAL SCORE'},
    ];

    const fetchAchievementData = async () => {
        try {
            const data = await fetchAuthenticated(`/api/v1/achievements/${achievementId}`, "GET")
                .then(async response => await response.json());
            setAchievementData(data);
            setIsLoaded(true);
            setSelectedValue(data.stat)
        } catch (error) {
            console.log(error);
        }
    }
    const handleDropDownChange = (value) => {
        setSelectedValue(value);
    }

    useEffect(() => {
        if (achievementId === undefined) {
            setAchievementData({
                "id": null,
                "name": "",
                "description": "",
                "imageUrl": "",
                "stat": "",
                "threshold": null
            });
            setIsLoaded(true);
            setTitle('Create achievement')
            setButtonText('Create achievement')
        } else {
            fetchAchievementData()
            setTitle('Edit achievement: ')
            setButtonText('Edit achievement')
        }
    }, [achievementId]);

    async function handleSubmit(body) {
        setMessage(null);
        if (!selectedValue || selectedValue.trim() === '') {
            setMessage('Por favor, selecciona un valor para el estadístico.');
            return;
        }
        if (body.name.trim() === '') {
            setMessage('Por favor, introduce un nombre para el logro.');
            return;
        }
        if (achievementId === undefined) {
            await fetchAuthenticated("/api/v1/achievements", "POST", body)
                .then(response => {
                    if (response.status === 201) return response.json();
                    else return Promise.reject("Invalid create achievement attempt");
                })
                .then(() => {
                    window.location.href = "/admin/achievements";
                })
                .catch(error => {
                    const errorMessage = error.message || "An error occurred";
                    setMessage(errorMessage);
                });
        } else {
            await fetchAuthenticated(`/api/v1/achievements/${achievementId}`, "PUT", body)
                .then(response => {
                    if (response.status === 200) return response.json();
                    else return Promise.reject("Invalid edit achievement attempt");
                })
                .then(() => {
                    window.location.href = "/admin/achievements";
                })
                .catch(error => {
                    const errorMessage = error.message || "An error occurred";
                    setMessage(errorMessage);
                });
        }
    }

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <div style={{display: "flex", flexDirection: "row", alignItems: "center"}}>
                    <Title style={titleStyle}>
                        {title}
                    </Title>
                    <Title style={{fontSize: "40px", color: orange, marginBottom: '20px'}}>
                        {achievementData?.name}
                    </Title>
                </div>
                {isLoaded ? (
                        <form style={{display: 'flex', flexDirection: 'column', width: "600px", gap: "8px"}}
                              onSubmit={(e) => {
                                  e.preventDefault();
                                  handleSubmit({
                                      name: e.target.name.value,
                                      description: e.target.description.value,
                                      imageUrl: e.target.image_url.value,
                                      stat: selectedValue,
                                      threshold: e.target.threshold.value
                                  });
                              }}>
                            <FormInput labelStyle={labelStyle} name={"name"} defaultValue={achievementData.name}
                                       placeholder={"Type the achievement's name"}></FormInput>
                            <FormInput labelStyle={labelStyle} name={"description"}
                                       defaultValue={achievementData.description}
                                       placeholder={"Type the achievement's description"}></FormInput>
                            <FormInput labelStyle={labelStyle} name={"image_url"} defaultValue={achievementData.imageUrl}
                                       placeholder={"Put the url of the image the achievement will have"}></FormInput>
                            <label style={textStyle} htmlFor={'stat'}>
                                stat
                            </label>
                            <DropDownPicker style={{width: "600px"}} options={statsOptions} onChange={handleDropDownChange}
                                            defaultValue={achievementData.stat}/>
                            <FormInput labelStyle={labelStyle} name={"threshold"} type={'number'} minValue={0}
                                       maxValue={9999} defaultValue={achievementData.threshold}
                                       placeholder={"Put the threshold of the stat necessary to gain the achievement"}></FormInput>
                            <div style={buttonStyle}>
                                {message && <Text style={{textTransform: "none", color: "red"}}>{message}</Text>}
                                <Button buttonType={ButtonType.primary} type="submit">{buttonText}</Button>
                            </div>
                        </form>
                    ) :
                    (
                        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                            <Title style={titleStyle}>
                                Loading...
                            </Title>
                        </div>
                    )}
            </section>
        </div>
    )
}


import React, {useEffect, useState} from "react";
import List from "../../components/List";
import {AchievementExpandedView} from "./AchievementExpandedView";

export function AchievementsTab({achievementsCompleatedData, allAchievementsData, isMe, rowListStyle, username}) {
    const [selectedAchievement, setSelectedAchievement] = useState(null)
    const [notCompletedAchievementsData, setNotCompleatedAchievementsData] = useState([]);

    useEffect(() => {
        setNotCompleatedAchievementsData(allAchievementsData?.filter(achievement => {
            return !achievementsCompleatedData?.some(compleatedAchievement => {
                return compleatedAchievement.id === achievement.id
            })
        }))
    }, []);

    let compleatedAchievementsItems = achievementsCompleatedData?.map(achievement => {
        return <AchievementExpandedView achievement={achievement} setSelectedAchievement={setSelectedAchievement} selectedAchievement={selectedAchievement} earned={true}/>
    });


    let notCompleatedAchievementsItems = notCompletedAchievementsData?.map(achievement => {
        return <AchievementExpandedView achievement={achievement} setSelectedAchievement={setSelectedAchievement} selectedAchievement={selectedAchievement} earned={false}/>
    });

    return (
        <div style={{width: "100%", display: "flex", flexDirection: "column"}}>
            <List style={rowListStyle}>
                {compleatedAchievementsItems}
                {isMe && notCompleatedAchievementsItems}
            </List>

        </div>
    )
}

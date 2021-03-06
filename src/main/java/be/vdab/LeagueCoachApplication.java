package be.vdab;

import be.vdab.config.JsonPathConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Map;

import static be.vdab.commands.UserInput.enterMatchIndex;
import static be.vdab.commands.UserInput.enterSummonerName;
import static be.vdab.commands.GetMatchData.getMatchData;
import static be.vdab.commands.GetMatchData.getParticipantId;
import static be.vdab.repository.MatchListRepository.getMatchListBySummoner;
import static be.vdab.repository.MatchRepository.getMatchByID;
import static be.vdab.repository.SummonerRepository.getSummonerInfo;

@SpringBootApplication
public class LeagueCoachApplication {
    public static void main(String[] args) throws IOException {
        //Setting JsonPath provider to Gson, required for compatibility
        JsonPathConfig.initializeConfig();

        //User enters the player name and the specific match they want data from
        String accountName = enterSummonerName();
        int matchId = enterMatchIndex();

        //Grabbing data from APIs and writing to generated JSON files in dynamically created directories
        getSummonerInfo(accountName);
        getMatchListBySummoner(accountName);
        getMatchByID(accountName, matchId);

        int participantId = getParticipantId(accountName, matchId);

        Map<String, Number> playerStats = getMatchData(accountName, matchId, participantId - 1);

        Number summonerKills = playerStats.get("Kills");
        System.out.println("This summoner achieved " + summonerKills + " kills this game.");
    }




}

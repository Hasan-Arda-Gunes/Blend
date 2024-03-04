public class Songs {
    int songID;
    String name;
    int playCount;
    int hearthScore;
    int roadScore;
    int blissScore;
    int playListID;
    Songs(int songID, String name, int playCount, int hearthScore, int roadScore,int blissScore){
        this.songID = songID;
        this.name = name;
        this.playCount = playCount;
        this.hearthScore = hearthScore;
        this.roadScore = roadScore;
        this.blissScore = blissScore;
    }
    Songs(){
        songID = 0;
        name = null;
        playCount = 0;
        hearthScore = 0;
        roadScore = 0;
        blissScore = 0;
    }

}

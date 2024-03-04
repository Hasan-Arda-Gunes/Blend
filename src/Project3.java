import java.io.*;
import java.util.ArrayList;

public class Project3 {
    public static void main(String[] args) throws Exception {
        File input = new File(args[0]);
        BufferedReader scan = new BufferedReader(new FileReader(input));
        FileWriter out = new FileWriter(args[2]);
        out.write("");
        scan.readLine();
        // creating data structures that will store the songs
        MaxHeapHearth heapHearth = new MaxHeapHearth();
        MaxHeapRoad heapRoad = new MaxHeapRoad();
        MaxHeapBliss heapBliss = new MaxHeapBliss();
        HashTable allSongs = new HashTable();
        while (scan.ready()){
            // scanning the songs
            String[] line = scan.readLine().split(" ");
            int ID = Integer.parseInt(line[0]);
            String name = line[1];
            int playCount = Integer.parseInt(line[2]);
            int hearth = Integer.parseInt(line[3]);
            int road = Integer.parseInt(line[4]);
            int bliss = Integer.parseInt(line[5]);
            Songs song = new Songs(ID,name,playCount,hearth,road,bliss);

            allSongs.insert(song);
        }
        File input2 = new File(args[1]);
        scan = new BufferedReader(new FileReader(input2));;
        // reading the limits
        String[] limits = scan.readLine().split(" ");
        int playlistLimit = Integer.parseInt(limits[0]);
        int hearthLimit = Integer.parseInt(limits[1]);
        int roadLimit = Integer.parseInt(limits[2]);
        int blissLimit = Integer.parseInt(limits[3]);
        MinHeapHearth EpicHearth = new MinHeapHearth();
        MinHeapRoad EpicRoad = new MinHeapRoad();
        MinHeapBliss EpicBliss = new MinHeapBliss();
        HashTable EpicBlendHearth = new HashTable();
        HashTable EpicBlendRoad = new HashTable();
        HashTable EpicBlendBliss = new HashTable();
        int num = Integer.parseInt(scan.readLine());
        MinHeapHearth[] allEpicHearth = new MinHeapHearth[num];
        MinHeapRoad[] allEpicRoad = new MinHeapRoad[num];
        MinHeapBliss[] allEpicBliss = new MinHeapBliss[num];
        for (int i = 0; i<num;i++){
            allEpicHearth[i] = new MinHeapHearth();
            allEpicRoad[i] = new MinHeapRoad();
            allEpicBliss[i] = new MinHeapBliss();
            String[] playlist = scan.readLine().split(" ");
            int playListID = Integer.parseInt(playlist[0]);
            int playNum = Integer.parseInt(playlist[1]);
            String[] songs = scan.readLine().split(" ");
            if (playNum != 0){
                for (String ID:songs){
                    //storing the songs
                    int id = Integer.parseInt(ID);
                    Songs song = allSongs.find(id);
                    song.playListID = playListID;
                    heapHearth.insert(song);
                    heapRoad.insert(song);
                    heapBliss.insert(song);

                }
            }

        }
        int[] countHearth = new int[num];
        int[] countRoad = new int[num];
        int[] countBliss = new int[num];
        ArrayList<Songs> tempRemoved = new ArrayList<>();
        HashTable removedSongs = new HashTable();


        while (EpicHearth.size < hearthLimit){
            if (heapHearth.size == 0)
                break;
            // store the ones with the highest scores if they didn't reach the limit
            Songs x = heapHearth.deleteMax();
            tempRemoved.add(x);
            if (countHearth[x.playListID-1] != playlistLimit){
                countHearth[x.playListID-1] += 1;
                EpicHearth.insert(x);
                EpicBlendHearth.insert(x);
                allEpicHearth[x.playListID-1].insert(x);
            }

        }
        // reinsert the ones you deleted
        while(!tempRemoved.isEmpty()){
            heapHearth.insert(tempRemoved.remove(0));
        }
        while (EpicRoad.size < roadLimit){
            if (heapRoad.size == 0)
                break;
            // store the ones with the highest scores if they didn't reach the limit
            Songs x = heapRoad.deleteMax();
            tempRemoved.add(x);
            if (countRoad[x.playListID-1] != playlistLimit){
                countRoad[x.playListID-1] += 1;
                allEpicRoad[x.playListID-1].insert(x);
                EpicRoad.insert(x);
                EpicBlendRoad.insert(x);
            }
        }
        // reinsert the ones you deleted
        while(!tempRemoved.isEmpty()){
            heapRoad.insert(tempRemoved.remove(0));
        }

        while (EpicBliss.size < blissLimit){
            if (heapBliss.size == 0)
                break;
            // store the ones with the highest scores if they didn't reach the limit
            Songs x = heapBliss.deleteMax();
            tempRemoved.add(x);
            if (countBliss[x.playListID-1] != playlistLimit){
                countBliss[x.playListID-1] += 1;
                allEpicBliss[x.playListID-1].insert(x);
                EpicBliss.insert(x);
                EpicBlendBliss.insert(x);
            }

        }
        // reinsert the ones you deleted
        while(!tempRemoved.isEmpty()){
            heapBliss.insert(tempRemoved.remove(0));
        }

        scan.readLine();
        while (scan.ready()){
            String[] line = scan.readLine().split(" ");
            if (line[0].equals("ADD")){
                // add the songs into the heaps
                int songID = Integer.parseInt(line[1]);
                int playListID = Integer.parseInt(line[2]);
                Songs song = allSongs.find(songID);
                song.playListID = playListID;
                if (removedSongs.contains(song))
                    removedSongs.remove(song);
                heapHearth.insert(song);
                heapRoad.insert(song);
                heapBliss.insert(song);
                allEpicRoad[playListID-1].insert(song);
                allEpicHearth[playListID-1].insert(song);
                allEpicBliss[playListID-1].insert(song);
                Songs minHearth = null;
                int addedHearth = 0;
                int addedRoad = 0;
                int addedBliss = 0;
                int removedHearth = 0;
                int removedRoad = 0;
                int removedBliss = 0;


                if (EpicBlendHearth.size < hearthLimit){
                    int index = playListID-1;
                    // if playlist didn't reach the limit and epicBlend is not full insert the song
                    if (countHearth[index] < playlistLimit){
                        addedHearth = songID;
                        allEpicHearth[index].insert(song);
                        EpicBlendHearth.insert(song);
                        EpicHearth.insert(song);
                        countHearth[index] += 1;
                    }
                    else {
                        while (true){
                            // if playlist reached the limit delete the min of that playlist and compare them
                            // if it is a better fit compared to deleted one then insert if not reinsert the deleted one
                            if (allEpicHearth[index].size == 0)
                                break;
                            Songs x = allEpicHearth[index].deleteMin();
                            if (EpicBlendHearth.contains(x)){
                                if (song.hearthScore > x.hearthScore){
                                    EpicBlendHearth.insert(song);
                                    EpicHearth.insert(song);
                                    allEpicHearth[index].insert(song);
                                    EpicBlendHearth.remove(x);
                                    addedHearth = song.songID;
                                    removedHearth = x.songID;
                                    break;
                                }
                                else if (song.hearthScore == x.hearthScore){
                                    if (song.name.compareTo(x.name) < 0){
                                        allEpicHearth[index].insert(song);
                                        EpicBlendHearth.insert(song);
                                        EpicHearth.insert(song);
                                        EpicBlendHearth.remove(x);
                                        addedHearth = song.songID;
                                        removedHearth = x.songID;
                                        break;
                                    }
                                    tempRemoved.add(x);
                                    break;
                                }

                                else{
                                    tempRemoved.add(x);
                                    break;
                                }


                            }
                        }
                        while(!tempRemoved.isEmpty()){
                            Songs x = tempRemoved.remove(0);
                            if (EpicBlendHearth.contains(x))
                                allEpicHearth[playListID-1].insert(x);
                        }
                    }

                }
                else{
                    // if EpicBlend reached the limit then delete the min of epic blend and compare them
                    while (true){
                        if (EpicHearth.size == 0)
                            break;
                        Songs x = EpicHearth.deleteMin();
                        if (EpicBlendHearth.contains(x)){
                            minHearth = x;

                            if (minHearth.hearthScore > song.hearthScore){
                                tempRemoved.add(x);
                                break;
                            }

                            if (song.hearthScore > minHearth.hearthScore){
                                int newIndex = playListID-1;
                                int oldIndex = minHearth.playListID-1;

                                if (oldIndex == newIndex){
                                    EpicHearth.insert(song);
                                    allEpicHearth[newIndex].insert(song);
                                    EpicBlendHearth.insert(song);
                                    EpicBlendHearth.remove(minHearth);
                                    addedHearth = song.songID;
                                    removedHearth = minHearth.songID;
                                    break;
                                }
                                else if (countHearth[newIndex] < playlistLimit){
                                    countHearth[oldIndex] -= 1;
                                    countHearth[newIndex] += 1;
                                    allEpicHearth[newIndex].insert(song);
                                    EpicHearth.insert(song);
                                    EpicBlendHearth.remove(minHearth);
                                    EpicBlendHearth.insert(song);
                                    addedHearth = song.songID;
                                    removedHearth = minHearth.songID;
                                    break;
                                }
                                else{
                                    // if playlist reached the limit the compare it with min of the playlist
                                    Songs y;
                                    boolean reached = false;
                                    while (true){
                                        y = allEpicHearth[newIndex].deleteMin();
                                        if (EpicBlendHearth.contains(y)){
                                            if (song.hearthScore > y.hearthScore){
                                                allEpicHearth[newIndex].insert(song);
                                                EpicHearth.insert(song);
                                                EpicBlendHearth.remove(y);
                                                EpicBlendHearth.insert(song);
                                                addedHearth = song.songID;
                                                removedHearth = y.songID;
                                                reached = true;
                                                break;
                                            }
                                            else if (song.hearthScore == y.hearthScore && song.name.compareTo(y.name) <0){
                                                allEpicHearth[newIndex].insert(song);
                                                EpicHearth.insert(song);
                                                EpicBlendHearth.remove(y);
                                                EpicBlendHearth.insert(song);
                                                addedHearth = song.songID;
                                                removedHearth = y.songID;
                                                reached = true;
                                                break;
                                            }
                                            else if (song.hearthScore < y.hearthScore){
                                                tempRemoved.add(x);
                                                allEpicHearth[newIndex].insert(y);
                                                break;
                                            }

                                            tempRemoved.add(x);
                                            allEpicHearth[newIndex].insert(y);
                                            break;
                                        }
                                    }
                                    if (!reached)
                                        continue;
                                }

                            }
                            else{
                                // if scores are equal compare their names
                                if (song.name.compareTo(minHearth.name) < 0){
                                    int newIndex = playListID-1;
                                    int oldIndex = minHearth.playListID-1;

                                    if (oldIndex == newIndex){
                                        EpicHearth.insert(song);
                                        allEpicHearth[newIndex].insert(song);
                                        EpicBlendHearth.insert(song);
                                        EpicBlendHearth.remove(minHearth);
                                        addedHearth = song.songID;
                                        removedHearth = minHearth.songID;
                                        break;
                                    }
                                    else if (countHearth[newIndex] < playlistLimit){
                                        countHearth[oldIndex] -= 1;
                                        countHearth[newIndex] += 1;
                                        allEpicHearth[newIndex].insert(song);
                                        EpicHearth.insert(song);
                                        EpicBlendHearth.remove(minHearth);
                                        EpicBlendHearth.insert(song);
                                        addedHearth = song.songID;
                                        removedHearth = minHearth.songID;
                                        break;
                                    }
                                    else{
                                        // if playlist reached the limit the compare it with min of the playlist
                                        Songs y;
                                        boolean reached = false;
                                        while (true){
                                            y = allEpicHearth[newIndex].deleteMin();
                                            if (EpicBlendHearth.contains(y)){
                                                if (song.hearthScore > y.hearthScore){
                                                    allEpicHearth[newIndex].insert(song);
                                                    EpicHearth.insert(song);
                                                    EpicBlendHearth.remove(y);
                                                    EpicBlendHearth.insert(song);
                                                    addedHearth = song.songID;
                                                    removedHearth = y.songID;
                                                    reached = true;
                                                    break;
                                                }
                                                else if (song.hearthScore == y.hearthScore && song.name.compareTo(y.name) <0){
                                                    allEpicHearth[newIndex].insert(song);
                                                    EpicHearth.insert(song);
                                                    EpicBlendHearth.remove(y);
                                                    EpicBlendHearth.insert(song);
                                                    addedHearth = song.songID;
                                                    removedHearth = y.songID;
                                                    reached = true;
                                                    break;
                                                }
                                                else if (song.hearthScore < y.hearthScore){
                                                    tempRemoved.add(x);
                                                    allEpicHearth[newIndex].insert(y);
                                                    break;
                                                }

                                                tempRemoved.add(x);
                                                allEpicHearth[newIndex].insert(y);
                                                break;
                                            }
                                        }
                                        if (!reached)
                                            continue;
                                    }
                                }
                                tempRemoved.add(x);

                            }
                            break;
                        }
                    }
                    while(!tempRemoved.isEmpty()){
                        Songs x = tempRemoved.remove(0);
                        if (EpicBlendHearth.contains(x))
                            EpicHearth.insert(x);
                    }

                }
                /////////////////////////////////
                if (EpicBlendRoad.size < roadLimit){
                    int index = playListID-1;
                    // if playlist didn't reach the limit and epicBlend is not full insert the song
                    if (countRoad[index] < playlistLimit){
                        addedRoad = songID;
                        allEpicRoad[index].insert(song);
                        EpicBlendRoad.insert(song);
                        EpicRoad.insert(song);
                        countRoad[index] += 1;
                    }
                    else {
                        while (true){
                            // if playlist reached the limit delete the min of that playlist and compare them
                            // if it is a better fit compared to deleted one then insert if not reinsert the deleted one
                            if (allEpicRoad[index].size == 0)
                                break;
                            Songs x = allEpicRoad[index].deleteMin();
                            if (EpicBlendRoad.contains(x)){
                                if (song.roadScore > x.roadScore){
                                    EpicBlendRoad.insert(song);
                                    EpicRoad.insert(song);
                                    allEpicRoad[index].insert(song);
                                    EpicBlendRoad.remove(x);
                                    addedRoad = song.songID;
                                    removedRoad = x.songID;
                                    break;
                                }
                                else if (song.roadScore == x.roadScore){
                                    if (song.name.compareTo(x.name) < 0){
                                        allEpicRoad[index].insert(song);
                                        EpicBlendRoad.insert(song);
                                        EpicRoad.insert(song);
                                        EpicBlendRoad.remove(x);
                                        addedRoad = song.songID;
                                        removedRoad = x.songID;
                                        break;
                                    }
                                    tempRemoved.add(x);
                                }
                                else
                                    tempRemoved.add(x);

                            }
                        }
                        while(!tempRemoved.isEmpty()){
                            Songs x = tempRemoved.remove(0);
                            if (EpicBlendRoad.contains(x))
                                allEpicRoad[playListID-1].insert(x);
                        }
                    }

                }
                else{
                    // if EpicBlend reached the limit then delete the min of epic blend and compare them
                    while (true){
                        if (EpicRoad.size == 0)
                            break;
                        Songs x = EpicRoad.deleteMin();
                        Songs minRoad;
                        if (EpicBlendRoad.contains(x)){
                            minRoad = x;

                            if (minRoad.roadScore > song.roadScore){
                                tempRemoved.add(x);
                                break;
                            }
                            if (song.roadScore > minRoad.roadScore){
                                int newIndex = playListID-1;
                                int oldIndex = minRoad.playListID-1;

                                if (oldIndex == newIndex){
                                    EpicRoad.insert(song);
                                    allEpicRoad[newIndex].insert(song);
                                    EpicBlendRoad.insert(song);
                                    EpicBlendRoad.remove(minRoad);
                                    addedRoad = song.songID;
                                    removedRoad = minRoad.songID;
                                    break;
                                }
                                else if (countRoad[newIndex] < playlistLimit){
                                    countRoad[oldIndex] -= 1;
                                    countRoad[newIndex] += 1;
                                    allEpicRoad[newIndex].insert(song);
                                    EpicRoad.insert(song);
                                    EpicBlendRoad.remove(minRoad);
                                    EpicBlendRoad.insert(song);
                                    addedRoad = song.songID;
                                    removedRoad = minRoad.songID;
                                    break;
                                }
                                else{
                                    // if playlist reached the limit the compare it with min of the playlist
                                    Songs y;
                                    boolean reached = false;
                                    while (true){
                                        y = allEpicRoad[newIndex].deleteMin();
                                        if (EpicBlendRoad.contains(y)){
                                            if (song.roadScore > y.roadScore){
                                                allEpicRoad[newIndex].insert(song);
                                                EpicRoad.insert(song);
                                                EpicBlendRoad.remove(y);
                                                EpicBlendRoad.insert(song);
                                                addedRoad = song.songID;
                                                removedRoad = y.songID;
                                                reached = true;
                                                break;
                                            }
                                            else if (song.roadScore == y.roadScore && song.name.compareTo(y.name) <0){
                                                allEpicRoad[newIndex].insert(song);
                                                EpicRoad.insert(song);
                                                EpicBlendRoad.remove(y);
                                                EpicBlendRoad.insert(song);
                                                addedRoad = song.songID;
                                                removedRoad = y.songID;
                                                reached = true;
                                                break;
                                            }
                                            else if (song.roadScore < y.roadScore){
                                                tempRemoved.add(x);
                                                allEpicRoad[newIndex].insert(y);
                                                break;
                                            }

                                            tempRemoved.add(x);
                                            allEpicRoad[newIndex].insert(y);
                                            break;
                                        }
                                    }
                                    if (!reached)
                                        continue;
                                }

                            }
                            else {
                                // if scores are equal compare their names
                                if (song.name.compareTo(minRoad.name) < 0){
                                    int newIndex = playListID-1;
                                    int oldIndex = minRoad.playListID-1;

                                    if (oldIndex == newIndex){
                                        EpicRoad.insert(song);
                                        allEpicRoad[newIndex].insert(song);
                                        EpicBlendRoad.insert(song);
                                        EpicBlendRoad.remove(minRoad);
                                        addedRoad = song.songID;
                                        removedRoad = minRoad.songID;
                                        break;
                                    }
                                    else if (countRoad[newIndex] < playlistLimit){
                                        countRoad[oldIndex] -= 1;
                                        countRoad[newIndex] += 1;
                                        allEpicRoad[newIndex].insert(song);
                                        EpicRoad.insert(song);
                                        EpicBlendRoad.remove(minRoad);
                                        EpicBlendRoad.insert(song);
                                        addedRoad = song.songID;
                                        removedRoad = minRoad.songID;
                                        break;
                                    }
                                    else{
                                        // if playlist reached the limit the compare it with min of the playlist
                                        Songs y;
                                        boolean reached = false;
                                        while (true){
                                            y = allEpicRoad[newIndex].deleteMin();
                                            if (EpicBlendRoad.contains(y)){
                                                if (song.roadScore > y.roadScore){
                                                    allEpicRoad[newIndex].insert(song);
                                                    EpicRoad.insert(song);
                                                    EpicBlendRoad.remove(y);
                                                    EpicBlendRoad.insert(song);
                                                    addedRoad = song.songID;
                                                    removedRoad = y.songID;
                                                    reached = true;
                                                    break;
                                                }
                                                else if (song.roadScore == y.roadScore && song.name.compareTo(y.name) <0){
                                                    allEpicRoad[newIndex].insert(song);
                                                    EpicRoad.insert(song);
                                                    EpicBlendRoad.remove(y);
                                                    EpicBlendRoad.insert(song);
                                                    addedRoad = song.songID;
                                                    removedRoad = y.songID;
                                                    reached = true;
                                                    break;
                                                }
                                                else if (song.roadScore < y.roadScore){
                                                    tempRemoved.add(x);
                                                    allEpicRoad[newIndex].insert(y);
                                                    break;
                                                }
                                                tempRemoved.add(x);
                                                allEpicRoad[newIndex].insert(y);
                                                break;
                                            }
                                        }
                                        if (!reached)
                                            continue;
                                    }
                                }
                                tempRemoved.add(x);

                            }
                            break;
                        }
                    }
                    while(!tempRemoved.isEmpty()){
                        Songs x = tempRemoved.remove(0);
                        if (EpicBlendRoad.contains(x))
                            EpicRoad.insert(x);
                    }

                }
                /////////////////////////////////////

                if (EpicBlendBliss.size < blissLimit){
                    int index = playListID-1;
                    // if playlist didn't reach the limit and epicBlend is not full insert the song
                    if (countBliss[index] < playlistLimit){
                        addedBliss = songID;
                        allEpicBliss[index].insert(song);
                        EpicBlendBliss.insert(song);
                        EpicBliss.insert(song);
                        countBliss[index] += 1;
                    }
                    else {
                        // if playlist reached the limit delete the min of that playlist and compare them
                        // if it is a better fit compared to deleted one then insert if not reinsert the deleted one
                        while (true){
                            if (allEpicBliss[index].size == 0)
                                break;
                            Songs x = allEpicBliss[index].deleteMin();
                            if (EpicBlendBliss.contains(x)){
                                if (song.blissScore > x.blissScore){
                                    EpicBlendBliss.insert(song);
                                    EpicBliss.insert(song);
                                    allEpicBliss[index].insert(song);
                                    EpicBlendBliss.remove(x);
                                    addedBliss= song.songID;
                                    removedBliss = x.songID;
                                    break;
                                }
                                if (song.blissScore == x.blissScore){
                                    if (song.name.compareTo(x.name) < 0){
                                        allEpicBliss[index].insert(song);
                                        EpicBlendBliss.insert(song);
                                        EpicBliss.insert(song);
                                        EpicBlendBliss.remove(x);
                                        addedBliss = song.songID;
                                        removedBliss = x.songID;
                                        break;
                                    }
                                    tempRemoved.add(x);
                                }
                                else
                                    tempRemoved.add(x);

                            }
                        }
                        while(!tempRemoved.isEmpty()){
                            Songs x = tempRemoved.remove(0);
                            if (EpicBlendBliss.contains(x))
                                allEpicBliss[playListID-1].insert(x);
                        }
                    }

                }
                else{
                    // if EpicBlend reached the limit then delete the min of epic blend and compare them
                    while (true){
                        if (EpicBliss.size == 0)
                            break;
                        Songs x = EpicHearth.deleteMin();
                        Songs minBliss;
                        if (EpicBlendBliss.contains(x)){
                            minBliss = x;

                            if (minBliss.blissScore > song.blissScore){
                                tempRemoved.add(x);
                                break;
                            }

                            if (song.blissScore > minBliss.blissScore){
                                int newIndex = playListID-1;
                                int oldIndex = minBliss.playListID-1;

                                if (oldIndex == newIndex){
                                    EpicBliss.insert(song);
                                    allEpicBliss[newIndex].insert(song);
                                    EpicBlendBliss.insert(song);
                                    EpicBlendBliss.remove(minBliss);
                                    addedBliss = song.songID;
                                    removedBliss = minBliss.songID;
                                    break;
                                }
                                else if (countBliss[newIndex] < playlistLimit){
                                    countBliss[oldIndex] -= 1;
                                    countBliss[newIndex] += 1;
                                    allEpicBliss[newIndex].insert(song);
                                    EpicBliss.insert(song);
                                    EpicBlendBliss.remove(minBliss);
                                    EpicBlendBliss.insert(song);
                                    addedBliss = song.songID;
                                    removedBliss = minBliss.songID;
                                    break;
                                }
                                else{
                                    // if playlist reached the limit then compare it with min of the playlist
                                    Songs y;
                                    boolean reached = false;
                                    while (true){
                                        y = allEpicBliss[newIndex].deleteMin();
                                        if (EpicBlendBliss.contains(y)){
                                            if (song.blissScore > y.blissScore){
                                                allEpicBliss[newIndex].insert(song);
                                                EpicBliss.insert(song);
                                                EpicBlendBliss.remove(y);
                                                EpicBlendBliss.insert(song);
                                                addedBliss = song.songID;
                                                removedBliss = y.songID;
                                                reached = true;
                                                break;
                                            }
                                            else if (song.blissScore == y.blissScore && song.name.compareTo(y.name) <0){
                                                allEpicBliss[newIndex].insert(song);
                                                EpicBliss.insert(song);
                                                EpicBlendBliss.remove(y);
                                                EpicBlendBliss.insert(song);
                                                addedBliss = song.songID;
                                                removedBliss = y.songID;
                                                reached = true;
                                                break;
                                            }
                                            else if (song.blissScore < y.blissScore){
                                                tempRemoved.add(x);
                                                allEpicBliss[newIndex].insert(y);
                                                break;
                                            }
                                            tempRemoved.add(x);
                                            allEpicBliss[newIndex].insert(y);
                                            break;
                                        }
                                    }
                                    if (!reached)
                                        continue;
                                }

                            }
                            else {
                                // if scores are equal compare their names
                                if (song.name.compareTo(minBliss.name) < 0){
                                    int newIndex = playListID-1;
                                    int oldIndex = minBliss.playListID-1;

                                    if (oldIndex == newIndex){
                                        EpicBliss.insert(song);
                                        allEpicBliss[newIndex].insert(song);
                                        EpicBlendBliss.insert(song);
                                        EpicBlendBliss.remove(minBliss);
                                        addedBliss = song.songID;
                                        removedBliss = minBliss.songID;
                                        break;
                                    }
                                    else if (countBliss[newIndex] < playlistLimit){
                                        countBliss[oldIndex] -= 1;
                                        countBliss[newIndex] += 1;
                                        allEpicBliss[newIndex].insert(song);
                                        EpicBliss.insert(song);
                                        EpicBlendBliss.remove(minBliss);
                                        EpicBlendBliss.insert(song);
                                        addedBliss = song.songID;
                                        removedBliss = minBliss.songID;
                                        break;
                                    }
                                    else{
                                        //if playlist reached the limit then compare it with min of the playlist
                                        Songs y;
                                        boolean reached = false;
                                        while (true){
                                            y = allEpicBliss[newIndex].deleteMin();
                                            if (EpicBlendBliss.contains(y)){
                                                if (song.blissScore > y.blissScore){
                                                    allEpicBliss[newIndex].insert(song);
                                                    EpicBliss.insert(song);
                                                    EpicBlendBliss.remove(y);
                                                    EpicBlendBliss.insert(song);
                                                    addedBliss = song.songID;
                                                    removedBliss = y.songID;
                                                    reached = true;
                                                    break;
                                                }
                                                else if (song.blissScore == y.blissScore && song.name.compareTo(y.name) <0){
                                                    allEpicBliss[newIndex].insert(song);
                                                    EpicBliss.insert(song);
                                                    EpicBlendBliss.remove(y);
                                                    EpicBlendBliss.insert(song);
                                                    addedBliss = song.songID;
                                                    removedBliss = y.songID;
                                                    reached = true;
                                                    break;
                                                }
                                                else if (song.blissScore < y.blissScore){
                                                    tempRemoved.add(x);
                                                    allEpicBliss[newIndex].insert(y);
                                                    break;
                                                }
                                                tempRemoved.add(x);
                                                allEpicBliss[newIndex].insert(y);
                                                break;
                                            }
                                        }
                                        if (!reached)
                                            continue;
                                    }

                                }
                                tempRemoved.add(x);

                            }
                            break;
                        }
                    }
                    while(!tempRemoved.isEmpty()){
                        Songs x = tempRemoved.remove(0);
                        if (EpicBlendBliss.contains(x))
                            EpicBliss.insert(x);
                    }

                }

                out.append(addedHearth + " " + addedRoad + " " + addedBliss + "\n");
                out.append(removedHearth + " "+ removedRoad + " " + removedBliss + "\n");
            }
            if (line[0].equals("REM")){
                int songID = Integer.parseInt(line[1]);
                Songs song = allSongs.find(songID);
                removedSongs.insert(song);
                int addedHearth = 0;
                int addedRoad = 0;
                int addedBliss = 0;
                int removedHearth = 0;
                int removedRoad = 0;
                int removedBliss = 0;
                if (EpicBlendHearth.contains(song)){
                    // if it is in epicBlend then we need to update the blend
                    EpicBlendHearth.remove(song);
                    removedHearth = songID;
                    int index = song.playListID-1;

                    countHearth[index] -= 1;
                    while (true){
                        if (heapHearth.size == 0)
                            break;
                        Songs newSong = heapHearth.deleteMax();
                        if (!removedSongs.contains(newSong)){
                            tempRemoved.add(newSong);
                            if (!EpicBlendHearth.contains(newSong)){
                                // find a song to replace the one in epicBlend
                                // looking for one that didn't reach the playListLimit
                                int newIndex = newSong.playListID-1;

                                if (countHearth[newIndex] == playlistLimit)
                                    continue;
                                countHearth[newIndex] += 1;
                                EpicBlendHearth.insert(newSong);
                                EpicHearth.insert(newSong);
                                addedHearth = newSong.songID;
                                break;
                            }
                        }
                    }
                    while(!tempRemoved.isEmpty()){
                        heapHearth.insert(tempRemoved.remove(0));
                    }
                }

                if (EpicBlendRoad.contains(song)){
                    // if it is in epicBlend then we need to update the blend
                    EpicBlendRoad.remove(song);
                    removedRoad = songID;
                    int index = song.playListID-1;

                    countRoad[index] -= 1;
                    while (true){
                        if (heapRoad.size == 0)
                            break;
                        Songs newSong = heapRoad.deleteMax();
                        if (!removedSongs.contains(newSong)){
                            tempRemoved.add(newSong);
                            if (!EpicBlendRoad.contains(newSong)){
                                // find a song to replace the one in epicBlend
                                int newIndex = newSong.playListID-1;

                                if (countRoad[newIndex] == playlistLimit)
                                    continue;
                                // looking for one that didn't reach the playListLimit
                                countRoad[newIndex] += 1;
                                EpicBlendRoad.insert(newSong);
                                EpicRoad.insert(newSong);
                                addedRoad = newSong.songID;
                                break;
                            }

                        }
                    }
                    while(!tempRemoved.isEmpty()){
                        heapRoad.insert(tempRemoved.remove(0));
                    }
                }
                /////////
                if (EpicBlendBliss.contains(song)){
                    // if it is in epicBlend then we need to update the blend
                    EpicBlendBliss.remove(song);
                    removedBliss = songID;
                    int index = song.playListID-1;

                    countBliss[index] -= 1;
                    while (true){
                        if (heapBliss.size == 0)
                            break;
                        Songs newSong = heapBliss.deleteMax();
                        if (!removedSongs.contains(newSong)){
                            tempRemoved.add(newSong);
                            if (!EpicBlendBliss.contains(newSong)){
                                // find a song to replace the one in epicBlend
                                int newIndex = newSong.playListID-1 ;

                                if (countBliss[newIndex] == playlistLimit)
                                    continue;
                                // looking for one that didn't reach the playListLimit
                                countBliss[newIndex] += 1;
                                EpicBlendBliss.insert(newSong);
                                EpicBliss.insert(newSong);
                                addedBliss = newSong.songID;
                                break;
                            }

                        }
                    }
                    while(!tempRemoved.isEmpty()){
                        heapBliss.insert(tempRemoved.remove(0));
                    }
                }
                out.append(addedHearth + " " + addedRoad + " " + addedBliss + "\n");
                out.append(removedHearth + " " + removedRoad + " " + removedBliss + "\n");
            }
            if (line[0].equals("ASK")){
                // create a max heap to store songs
                MaxPlayCount solution = new MaxPlayCount();
                // insert the heap arrays into the solution heap
                for (Songs x: EpicHearth.array){
                    if (x == null)
                        continue;
                    if (EpicBlendHearth.contains(x))
                        solution.insert(x);
                }
                for (Songs x: EpicRoad.array){
                    if (x == null)
                        continue;
                    if (EpicBlendRoad.contains(x) && !EpicBlendHearth.contains(x))
                        solution.insert(x);
                }
                for (Songs x: EpicBliss.array){
                    if (x==null)
                        continue;
                    if (EpicBlendBliss.contains(x) && (!EpicBlendRoad.contains(x) && !EpicBlendHearth.contains(x)))
                        solution.insert(x);
                }
                HashTable solutionTable = new HashTable();
                ArrayList<Songs> list = new ArrayList<>();
                while (solution.size != 0){
                    Songs song = solution.deleteMax();
                    // so that there are no duplicates
                    if (!solutionTable.contains(song)){
                        solutionTable.insert(song);
                        list.add(song);
                    }
                }
                for (int i = 0; i< solutionTable.size-1;i++){
                    out.append(list.get(i).songID + " ");
                }

                out.append(list.get(solutionTable.size-1).songID +"\n");

            }
        }
        out.close();

    }
}
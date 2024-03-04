import java.util.LinkedList;

public class HashTable {
    public LinkedList<Songs>[] list;
    public int size;

    HashTable() {
        this(101);
    }

    HashTable(int tableSize) {
        list = new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            list[i] = new LinkedList<>();
        }
        size = 0;
    }

    private int hash(int x) {
        int hashIndex = x;
        hashIndex = hashIndex % list.length;
        if (hashIndex < 0)
            hashIndex += list.length;
        return hashIndex;
    }


    public void insert(Songs song) {
        LinkedList<Songs> insertedList = list[hash(song.songID)];
        insertedList.add(song);
        size++;
        if (size > list.length)
            rehash();
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }
        if (n == 1 || n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;

    }

    private static int nextPrime(int x) {
        if (x % 2 == 0) {
            x++;
        }
        while (!isPrime(x)) {
            x += 2;
        }
        return x;
    }

    private void rehash() {
        LinkedList<Songs>[] oldLinkedLists = list;
        list = new LinkedList[nextPrime(2 * list.length)];
        for (int i = 0; i < list.length; i++)
            list[i] = new LinkedList<>();
        size = 0;
        for (LinkedList<Songs> linkedList : oldLinkedLists) {
            for (Songs x : linkedList)
                insert(x);
        }
    }

    public boolean contains(Songs x) {
        LinkedList<Songs> listToCheck = list[hash(x.songID)];
        return listToCheck.contains(x);

    }

    public void remove(Songs x) {
        LinkedList<Songs> removeFrom = list[hash(x.songID)];
        removeFrom.remove(x);
        size--;
    }

    public int getSize() {
        return size;
    }
    public Songs find(int ID){
        LinkedList<Songs> listToFind = list[hash(ID)];
        for (Songs songs:listToFind){
            if (songs.songID == ID)
                return songs;
        }
        return null;
    }


}

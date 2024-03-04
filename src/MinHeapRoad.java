public class MinHeapRoad {
    public Songs[] array;
    public int size;
    MinHeapRoad() {
        size = 0;
        array = new Songs[10000];
    }
    public void insert(Songs x) {
        // insert with scores being priority and names being second priorities
        if( size == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );
        int hole = ++size;
        for(array[0] = x; x.roadScore <= (array[hole/2].roadScore); hole/=2){
            if (x.roadScore == array[hole/2].roadScore){
                if (x.name.compareTo(array[hole/2].name) > 0){
                    array[hole] = array[hole/2];
                }
                else
                    break;
            }
            else
                array[hole] = array[hole/2];
        }
        array[hole] = x;


    }
    public void enlargeArray(int size){
        Songs[] x = new Songs[size];
        for (int i = 0; i< array.length;i++){
            x[i] = array[i];
        }
        array = x;
    }
    public Songs deleteMin(){
        Songs min = array[1];
        array[1] = array[size--];
        percolateDown(1);
        return min;
    }
    private void percolateDown(int x){
        Songs temp = array[x];
        int child;
        for (;x*2<=size;x = child){
            child = x*2;
            if(child != size && (array[child+1].roadScore<(array[child].roadScore)))
                child++;
            else if (child != size && (array[child+1].roadScore == (array[child].roadScore) && array[child+1].name.compareTo(array[child].name) > 0))
                child++;
            if( array[child].roadScore < temp.roadScore || (array[child].roadScore == temp.roadScore && array[child].name.compareTo(temp.name) > 0))
                array[x] = array[child];
            else
                break;
        }
        array[x] = temp;
    }
    public Songs peek(){return array[1];}
}

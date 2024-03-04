public class MaxHeapHearth {
    public Songs[] array;
    public int size;
    MaxHeapHearth() {
        size = 0;
        array = new Songs[10000];
    }
    public void insert(Songs x) {
        // insert with scores being priority and names being second priorities
        if( size == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );
        int hole = ++size;
        for(array[0] = x; x.hearthScore >= (array[hole/2].hearthScore); hole/=2){
            if (x.hearthScore == array[hole/2].hearthScore){
                if (x.name.compareTo(array[hole/2].name) < 0){
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
    public Songs deleteMax(){
        Songs max = array[1];
        array[1] = array[size--];
        percolateDown(1);
        return max;
    }
    private void percolateDown(int x){
        Songs temp = array[x];
        int child;
        for (;x*2<=size;x = child){
            child = x*2;
            if(child != size && (array[child+1].hearthScore>(array[child].hearthScore)))
                child++;
            else if (child != size && (array[child+1].hearthScore == (array[child].hearthScore) && array[child+1].name.compareTo(array[child].name) < 0))
                child++;
            if( array[child].hearthScore > temp.hearthScore || (array[child].hearthScore == temp.hearthScore && array[child].name.compareTo(temp.name) < 0))
                array[x] = array[child];
            else
                break;
        }
        array[x] = temp;
    }
    public Songs peek(){return array[1];}
}

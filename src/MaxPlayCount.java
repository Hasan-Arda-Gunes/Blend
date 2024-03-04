public class MaxPlayCount {
    private Songs[] array;
    public int size;
    MaxPlayCount() {
        size = 0;
        array = new Songs[10000];
    }
    public void insert(Songs x) {
        // insert with scores being priority and names being second priorities
        if( size == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );
        int hole = ++size;
        for(array[0] = x; x.playCount >= (array[hole/2].playCount); hole/=2){
            if (x.playCount == array[hole/2].playCount){
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
            if(child != size && (array[child+1].playCount>(array[child].playCount)))
                child++;
            else if (child != size && (array[child+1].playCount == (array[child].playCount) && array[child+1].name.compareTo(array[child].name) > 0))
                child++;
            if( array[child].playCount > temp.playCount || (array[child].playCount == temp.playCount && array[child].name.compareTo(temp.name) > 0))
                array[x] = array[child];
            else
                break;
        }
        array[x] = temp;
    }
    public Songs peek(){return array[1];}
}

import java.util.Arrays;

public class HashTable {

	private HashEntry[] table;
	private int tableSize;
	private double loadFactor = 0.75;
	private int entries;
	
	//Constructs HashTable of initial size 1000
	public HashTable(){
		tableSize = 1000;
		table = new HashEntry[1000];
	}
	
	//Constructs HashTable of given input size
	public HashTable(int size){
		tableSize = size;
		table = new HashEntry[size];
	}
	
	/*
	 * Insert value into HashTable
	 * Rehashes if table is overloaded 
	 */
	public void put(String key, int value){
		if(probe(key) == -1) return;
		table[probe(key)] = new HashEntry(key, value);
		entries++;
		if(entries >= Math.floor(tableSize * loadFactor)){
			rehash();
		}
		
	}
	/*
	 * Attempts to insert value into given hashCode
	 * 
	 */
	public void put(String key, int value, int hashCode){
		if(table[hashCode] == null){
			table[hashCode] = new HashEntry(key, value);
		}
		else{
			table[probe(key, hashCode)] = new HashEntry(key, value);
		}
		entries++;
		if(entries >= Math.floor(tableSize * loadFactor)){
			rehash();
		}
	}
	
	public void update(String key, int value){
		if(search(key) == -1){
			table[probe(key)] = new HashEntry(key,value);
		}
		else{
		table[search(key)].setValue(value);
		}
	}
	
	/*
	 * Returns value of given key
	 */
	public int get(String key){
		if (search(key) == -1){
			return -1;
		}
		int value = table[search(key)].getValue();
		return value;
	}
	
	public int get(String key, int hashCode){
		return table[search(key, hashCode)].getValue();
	}
	
	private int probe(String key){
		int i = Math.abs(key.hashCode()) % tableSize;
		int iterations = 0;
		while(table[i] != null){
			i++; iterations++;
			if(i == tableSize) i = 0;
			if(iterations > tableSize) return -1;
		}
		return i;
	}
	private int probe(String key, int hashCode){
		int i = hashCode;
		int iterations = 0;
		while(table[i] != null){
			i++; iterations++;
			if(i == tableSize) i = 0;
			if(iterations > tableSize) return -1;
		}
		return i;
	}
	public int search(String key){
		int i = Math.abs(key.hashCode()) % tableSize;
		int iterations = 0;
		while(table[i] == null || (table[i].getKey().compareTo(key) != 0)){
			i++;
			iterations++;
			if(i == tableSize) i = 0;
			if(iterations > tableSize) return -1;
		}
		return i;
	}
	public int search(String key, int hashCode){
		int i = hashCode;
		int iterations = 0;
		while(table[i] == null || (table[i].getKey().compareTo(key) != 0)){
			i++;
			iterations++;
			if(i == tableSize) i = 0;
			if(iterations > tableSize) return -1;
		}
		return i;
	}
	
	public void rehash(){	
		int oldSize = tableSize;
		HashEntry[] oldTable = table;
		tableSize = 2 * oldSize;
		table = new HashEntry[tableSize];
		for(int i = 0; i < oldSize; i++){
			if (oldTable[i] != null){
				table[i] = oldTable[i];
			}
		}
	}
	
	
	public static void main(String[] args){
	
	}
}

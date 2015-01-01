
public class HashEntry {
	private String key;
	private int value;
	private boolean removed;
	
	public HashEntry(String key, int value){
		this.key = key;
		this.value = value;
		removed = false;
	}
	
	public String getKey(){
		return key;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
}

package application;

public class ObjectList {
	
	private String id;
	private String content;
	
	public ObjectList(String id, String content) {
		setId(id);
		setContent(content);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return getContent();
	}
}

package test;

public enum ChanelTypeForTest {
	
	NAV_CHANNEL("导航栏目", "dhlm"), TOPIC_LIST("文章列表栏目", "wzlm"), TOPIC_CONTENT(
			"文章内容栏目", "wznrlm"), TOPIC_IMG("图片列表栏目", "tplblm");

	private String name;
	private String value;

	private ChanelTypeForTest(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

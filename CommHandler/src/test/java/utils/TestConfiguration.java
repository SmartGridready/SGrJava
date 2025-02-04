package utils;

import java.util.Collection;
import java.util.Properties;

public class TestConfiguration {

	public static class NameValuePair {
		private String name;
		private String value;

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
	
	public static class Description {
		private String file;
		private Collection<NameValuePair> parameters;

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}

		public Collection<NameValuePair> getParameters() {
			return parameters;
		}

		public void setParameters(Collection<NameValuePair> parameters) {
			this.parameters = parameters;
		}

		public Properties getParametersAsProperties() {
			final Properties props = new Properties();
			if (parameters != null) {
				parameters.forEach(p -> {
					props.put(p.getName(), p.getValue());
				});
			}
			return props;
		}
	}

	private String descriptionFolder;
	
	private Collection<Description> descriptions;

	public String getDescriptionFolder() {
		return descriptionFolder;
	}

	public void setDescriptionFolder(String descriptionFolder) {
		this.descriptionFolder = descriptionFolder;
	}

	public Collection<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Collection<Description> descriptions) {
		this.descriptions = descriptions;
	}	
}

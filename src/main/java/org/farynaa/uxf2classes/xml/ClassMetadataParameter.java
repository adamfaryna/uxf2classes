package org.farynaa.uxf2classes.xml;

public class ClassMetadataParameter {
	private String paramType;
	private String paramName;

	public ClassMetadataParameter(String paramType, String paramName) {
		this.paramType = paramType;
		this.paramName = paramName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	@Override
	public boolean equals(Object other) {		
		if (other == this) {
			return true;
		}
		
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		
		ClassMetadataParameter classMetadataParameterOther = (ClassMetadataParameter) other;
		return getParamType().equals(classMetadataParameterOther.getParamType()) &&
				getParamName().equals(classMetadataParameterOther.getParamName());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getParamType() == null) ? 0 : getParamType().hashCode());
		result = prime * result + ((getParamName() == null) ? 0 : getParamName().hashCode());
		return result;
	}
}
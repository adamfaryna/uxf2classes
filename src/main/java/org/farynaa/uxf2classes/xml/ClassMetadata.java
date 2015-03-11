package org.farynaa.uxf2classes.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devil
 *
 */
public class ClassMetadata {

	private String className;
	private List<ClassMetadataParameter> properties = new ArrayList<ClassMetadataParameter>();
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<ClassMetadataParameter> getProperties() {
		return properties;
	}
	
	@Override
	public boolean equals(Object other) {		
		if (other == this) {
			return true;
		}
		
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		
		ClassMetadata classMetadataOther = (ClassMetadata) other;
		return getClassName().equals(classMetadataOther.getClassName()) &&
				getProperties().containsAll(classMetadataOther.getProperties());		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
		result = prime * result + ((getProperties() == null) ? 0 : getProperties().hashCode());
		return result;
	}
}

package module3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeMagnitudeClass {
	private List<Class> classes;
	
	private class Class {
		public String class_name;
		public Float magnitude_from;
		public Float magnitude_to;
		public Color color;
		
		public Class (String class_name, Float magnitude_from, Float magnitude_to, Color color) {
			this.class_name = class_name;
			this.magnitude_from = magnitude_from;
			this.magnitude_to = magnitude_to;
			this.color = color;
		}
	}
	
	public EarthquakeMagnitudeClass() {
		classes = new ArrayList<Class>();
		classes.add(new Class("Minor", 3f, 4f, new Color(255, 255, 0)));
		classes.add(new Class("Light", 4f, 5f, new Color(255, 204, 0)));
		classes.add(new Class("Moderate", 5f, 6f, new Color(255, 153, 0)));
		classes.add(new Class("Strong", 6f, 7f, new Color(255, 102, 0)));
		classes.add(new Class("Major", 7f, 8f, new Color(255, 50, 0)));
		classes.add(new Class("Great", 8f, null, new Color(255, 0, 0)));
	}
	
		private Class findClass(float magnitude) {
		for (Class class_ : classes) {
			if ((class_.magnitude_from == null || class_.magnitude_from <= magnitude) && (class_.magnitude_to == null || class_.magnitude_to > magnitude)) {
				return class_;
			}
		}
		
		return null;
	}
	
	public int getColor(float magnitude) {
		Class class_ = findClass(magnitude);
		
		if (class_ != null) {
			return class_.color.getRGB();
		}
		
		return new Color(155, 155, 155).getRGB();
	}
}
	
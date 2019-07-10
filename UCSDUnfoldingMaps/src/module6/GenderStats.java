package module6;

import processing.core.PApplet;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;

public class GenderStats extends PApplet {
	private int start_year, end_year, current_year;
	private UnfoldingMap map;
	private List<Feature> countries;
	private List<Marker> country_markers;
	private HashMap<String, HashMap<Integer, Float>> fertility_rate_map;
	
	public void setup() {
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 0, 0, 800, 600, new Microsoft.AerialProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// Load fertility rates from GenderStats.csv
		fertility_rate_map = ParseFeed.loadGenderStatsFromCSV(this,"GenderStats.csv", "SP.DYN.TFRT.IN");
		setYears();
		
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		country_markers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(country_markers);
	}
	
	public void draw() {
		map.draw();
		
		shadeCountries(this.current_year);
		
		textAlign(LEFT, BOTTOM);
		fill(255, 255, 255);
		textSize(12);
		text("Fertility rate in year: " + this.current_year, 25, 300);
		noStroke();
		for (int i = 0; i <= 255; i += 3) {
			fill(i, 100, 255 - i);
			rect(25, 305 + i/3, 20, 1);
		}
		
		textAlign(LEFT, TOP);
		fill(255, 255, 255);
		text(10, 55, 305);
		textAlign(LEFT, BOTTOM);
		text(0, 55, 305 + 255/3);
		
		setCurrentYear();
		
		delay(100);
	}
	
	// Helper method to color each country based on fertility rate
	private void shadeCountries(Integer year) {
		for (Marker marker : country_markers) {
			// Find data for country of the current marker
			String country_code = marker.getId();
			
			if (fertility_rate_map.containsKey(country_code)) {
				Float fertility_rate = fertility_rate_map.get(country_code).get(year);
				
				if (fertility_rate != null) {
					// Encode value as brightness (values range: 0-10)
					int color_level = (int) map(fertility_rate, 0, 10, 0, 255);
					marker.setColor(color(255 - color_level, 100, color_level));
				}
			}
		}
	}
	
	private void setCurrentYear() {
		this.current_year = this.start_year + (this.current_year - this.start_year + 1) % (this.end_year - this.start_year + 1);
	}
	
	private void setYears() {
		int min_year = -1;
		int max_year = -1;
		
		for (String country_code : fertility_rate_map.keySet()) {
			for (int year : fertility_rate_map.get(country_code).keySet()) {
				if (min_year > year || min_year == -1) {
					min_year = year;
				}
				
				if (max_year < year || max_year == -1) {
					max_year = year;
				}
			}
		}
		
		this.start_year = min_year;
		this.current_year = min_year;
		this.end_year = max_year;
	}

}
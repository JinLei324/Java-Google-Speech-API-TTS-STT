/*
Class to computer temperature conversions
*/
package Temp_convert;
public class Convert {

	private String from;
	private String to;
	private double value;

	// receive inputs for conversions
	public Convert(String from, String to, double value) {
		this.from = from;
		this.to = to;
		this.value = value;
	}
	
	// do conversions
	public double convert() {
		// celsius conversions
		if (from.equals("celsius")) {
			if (to.equals("fahrenheit"))
				return CtoF(value);
			if (to.equals("kelvin"))
				return CtoK(value);
			if (to.equals("rankin"))
				return CtoR(value);
			if(to.equals("celsius"))
				return value;
		}

		// fahrenheit conversions
		else if (from.equals("fahrenheit")) {
			if (to.equals("celsius"))
				return FtoC(value);
			if (to.equals("kelvin"))
				return FtoK(value);
			if (to.equals("rankin"))
				return FtoR(value);
		}

		// kelvin conversions
		else if (from.equals("kelvin")) {
			if (to.equals("celsius"))
				return KtoC(value);
			if (to.equals("fahrenheit"))
				return KtoF(value);
			if (to.equals("rankin"))
				return KtoR(value);
		}

		// rankin conversions
		else if (from.equals("rankin")) {
			if (to.equals("celsius"))
				return RtoC(value);
			if (to.equals("fahrenheit"))
				return RtoF(value);
			if (to.equals("kelvin"))
				return RtoK(value);
		}

		// same base
		return value;
	}

	// celsius to farenheight
	public double CtoF(double c) {
		return c * 9 / 5.0 + 32;
	}

	// celsius to kelvin
	public double CtoK(double c) {
		return c + 273.15;
	}

	// celsius to rankin
	public double CtoR(double c) {
		return (c + 273.15) * 9 / 5.0;
	}

	// farenheight to celsius
	public double FtoC(double f) {
		return (f - 32) * 5 / 9.0;
	}

	// farenheight to kelvin
	public double FtoK(double f) {
		return (f + 459.67) * 5 / 9.0;
	}

	// fahrenheit to rankin
	public double FtoR(double f) {
		return f + 459.67;
	}

	// kelvin to celcius
	public double KtoC(double k) {
		return k - 273.15;
	}

	// kelvin to faherheit
	public double KtoF(double k) {
		return k * 9 / 5.0 - 459.67;
	}

	// kelvin to rankin
	public double KtoR(double k) {
		return k * 9 / 5.0;
	}

	// rankin to celsius
	public double RtoC(double r) {
		return (r - 491.67) * 5 / 9.0;
	}

	// rankin to farenheight
	public double RtoF(double r) {
		return r - 459.67;
	}

	// rankin to kelvin
	public double RtoK(double r) {
		return r * 5 / 9.0;
	}
}

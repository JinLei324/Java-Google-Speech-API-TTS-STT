/*
 * thread used to calculate temperature conversions
 * thread received Conversion object used to do conversion
 * thread writes result to a textbox 
 */

package Temp_convert;
import javax.swing.JTextField;

/*
Class implementd Runnable to create threads
*/
class Threading implements Runnable {
	// class attributes
	private String name;
	private Convert convert;
	private double result;
	private Thread thread;
	private JTextField textBox;

	// constructor to set values
	Threading(String name, Convert convert, JTextField textBox) {
		this.name = name;
		this.convert = convert;
		this.textBox = textBox;

	}

	// method to run the thread
	@Override
	public void run() {

		try {

			// do conversion
			result = convert.convert();

			Thread.sleep(100);// make thread sleep for a while
		} catch (InterruptedException ex) {
			System.err.println(ex.getMessage());
		}

		// write results to text box
		textBox.setText(String.format("%.1f", result));
	}

	// return result
	public double getResult() {
		return result;
	}

	// method to start the thread
	public void start() {
		if (thread == null) {
			thread = new Thread(this, name);
			thread.start(); // run the thread
		}
	}

}
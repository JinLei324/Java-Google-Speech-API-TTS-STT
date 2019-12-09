/**
 * Program that uses threads to compute temperature conversions
 * celsius, fahrenhet, kelvin and rankin scales
 */

package Temp_convert;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.io.IOException;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;

public class Main {
	SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	final Microphone mic = new Microphone(FLACFileWriter.FLAC);	
	SpeakState speakState = new SpeakState();
	// main method to start the application
	public static void main(String[] args) {
		Main mainWindow = new Main();
		mainWindow.setUp();// call method to set up the frame
	}
	public Main(){
		duplex.setLanguage("en");
	}
	// method to set the frame and its components
	public void setUp() {
		// create frame and set its components
		JFrame frame = new JFrame("Temperature Convertor");
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();// create panel
		panel.setLayout(new GridLayout(10, 2)); // use grid layout

		// add label to panel for choice of conversion
		JLabel tempChoice1 = new JLabel("Select input ", JLabel.RIGHT);
		panel.add(tempChoice1);
		JLabel tempChoice2 = new JLabel("temperature scale:", JLabel.LEFT);
		panel.add(tempChoice2);

		// select from temperature radio buttons

		
		JRadioButton radioCelsius = new JRadioButton("Celsius");
		radioCelsius.setActionCommand("celsius");
		panel.add(radioCelsius);
		JRadioButton radioFahrenheit = new JRadioButton("Fahrenheit");
		radioFahrenheit.setActionCommand("fahrenheit");
		panel.add(radioFahrenheit);
		JRadioButton radioKelvin = new JRadioButton("Kelvin");
		radioKelvin.setActionCommand("kelvin");
		panel.add(radioKelvin);
		JRadioButton radioRankin = new JRadioButton("Rankin");
		radioRankin.setActionCommand("rankin");
		panel.add(radioRankin);

		// add radio buttons to button group
		ButtonGroup groupBtns = new ButtonGroup();
		groupBtns.add(radioCelsius);
		groupBtns.add(radioFahrenheit);
		groupBtns.add(radioKelvin);
		groupBtns.add(radioRankin);

		// add label to panel for temperature number
		JLabel label = new JLabel("Enter Temperature: ");
		panel.add(label);

		// add input text field to panel
		JTextField textField = new JTextField(10);
		panel.add(textField);

		
		JLabel tempChoice3 = new JLabel("Select output ", JLabel.RIGHT);
		panel.add(tempChoice3);
		JLabel tempChoice4 = new JLabel("temperature scale", JLabel.LEFT);
		panel.add(tempChoice4);
		
		// make output checkbox selection and conversions textbox
		JRadioButton chkCelsius = new JRadioButton("Celsius");
		chkCelsius.setActionCommand("celsius");
		panel.add(chkCelsius);
		
		JRadioButton chkFahrenheit = new JRadioButton("Fahrenheit");
		chkFahrenheit.setActionCommand("fahrenheit");
		panel.add(chkFahrenheit);
		
		JRadioButton chkKelvin = new JRadioButton("Kelvin");
		chkKelvin.setActionCommand("kelvin");
		panel.add(chkKelvin);
		
		JRadioButton chkRankin = new JRadioButton("Rankin");
		chkRankin.setActionCommand("rankin");
		panel.add(chkRankin);
		

		ButtonGroup convertBtns = new ButtonGroup();
		convertBtns.add(chkCelsius);
		convertBtns.add(chkFahrenheit);
		convertBtns.add(chkKelvin);
		convertBtns.add(chkRankin);


		JLabel labelResult = new JLabel("Convert Result:");
		panel.add(labelResult);
		JTextField textFieldResult = new JTextField(10);
		panel.add(textFieldResult);

		JButton record = new JButton("Start");
		JButton stop = new JButton("Exit");
		stop.setEnabled(false);

		panel.add(record);
		panel.add(stop);
		// add panel to frame
		frame.add(panel);
		// display the frame to user
		frame.setVisible(true);

		// action listener for convert button to start threads
		
	
		GSpeechResponseListener scaleRecog = new GSpeechResponseListener() {
			String old_text = "";
			String new_text = "";
			int inputState = 0;
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = this.new_text;
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = ( this.new_text );
					this.old_text = this.old_text.replace(")", "").replace("( ", "");					
					this.new_text=this.old_text;
					inputState = 0;
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + "(" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println(output);
				output = output.replaceAll("\\s+","");
				if(inputState == 0){
					groupBtns.clearSelection();
					if(output.compareTo("1")==0 || output.compareTo("one")==0){
						//System.out.println("Celsius Seletecd");
						radioCelsius.setSelected(true);
						//speakState.setState(1) ;
						inputState = 1;
						
					}
					if(output.compareTo("2")==0 || output.compareTo("cool")==0 || output.compareTo("who")==0){
						System.out.println("radioFahrenheit Seletecd");					
						radioFahrenheit.setSelected(true);
						//speakState.setState(1) ;
						inputState = 1;
						
						
					}
					if(output.compareTo("3")==0 || output.compareTo("three")==0 || output.compareTo("tree")==0){
						System.out.println("Kelvin Seletecd");					
						radioKelvin.setSelected(true);
						//speakState.setState(1) ;
						inputState = 1;
						
						
					}
					if(output.compareTo("4")==0 || output.compareTo("soul")==0 || output.compareTo("full")==0){
						System.out.println("radioRankin Seletecd");					
						radioRankin.setSelected(true);
						//speakState.setState(1) ;	
						inputState = 1;				
					}

					if(inputState == 1){
						inputState = 0;
						duplex.stopSpeechRecognition();	
						return;
					}
				}
				
			}
		};
		
		GSpeechResponseListener tempRecog = new GSpeechResponseListener() {
			String old_text = "";
			String new_text = "";
			//int inputState = 0;
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = this.new_text;
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = ( this.new_text );
					this.old_text = this.old_text.replace(")", "").replace("( ", "");					
					this.new_text=this.old_text;
					//inputState = 0;
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + "(" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println(output);
				output = output.replaceAll("\\s+","");
				textField.setText(output);
				try {
					// get input temperature value					 
					Double.parseDouble(textField.getText());
					duplex.stopSpeechRecognition();
					return;
				}catch (NumberFormatException ex) {
					
				}			
			}
		};



		GSpeechResponseListener convertRecog = new GSpeechResponseListener() {
			String old_text = "";
			String new_text = "";
			int inputState = 0;
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = this.new_text;
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = ( this.new_text );
					this.old_text = this.old_text.replace(")", "").replace("( ", "");					
					this.new_text=this.old_text;
					inputState = 0;
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + "(" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println(output);
				output = output.replaceAll("\\s+","");
				if(inputState == 0){
					convertBtns.clearSelection();
					if(output.compareTo("1")==0 || output.compareTo("one")==0){
						//System.out.println("Celsius Seletecd");
						chkCelsius.setSelected(true);
						//speakState.setState(1) ;
						inputState = 1;
						
					}
					if(output.compareTo("2")==0 || output.compareTo("who")==0 || output.compareTo("cool")==0){
											
						chkFahrenheit.setSelected(true);
						//speakState.setState(1) ;
						inputState = 1;
						
						
					}
					if(output.compareTo("3")==0 || output.compareTo("three")==0 || output.compareTo("tree")==0){
											
						chkKelvin.setSelected(true);
						//speakState.setState(1) ;
						inputState = 1;
						
						
					}
					if(output.compareTo("4")==0 || output.compareTo("soul")==0 || output.compareTo("full")==0){
									
						chkRankin.setSelected(true);
						//speakState.setState(1) ;	
						inputState = 1;				
					}

					if(inputState == 1){
						inputState = 0;
						duplex.stopSpeechRecognition();
						return;
					}
				}
				
			}
		};


		record.addActionListener((ActionEvent e) ->{
			record.setEnabled(false);			
			stop.setEnabled(true);
			Thread thread = new Thread(() -> {
				speak("hello please input scale. 1 for cilcios , 2 for fehrenhiet , 3 for kelvin, 4 for Rankin");			
				duplex.addResponseListener(scaleRecog);
				
				try {
					duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
						
					duplex.removeResponseListener(scaleRecog);
					speak("Please Enter Temperature you want to convert it");


					duplex.addResponseListener(tempRecog);
					duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
					
					duplex.removeResponseListener(tempRecog);

					speak("hello please output scale. 1 for cilcios , 2 for fehrenhiet , 3 for kelvin, 4 for Rankin");
					duplex.addResponseListener(convertRecog);
					duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
					
					duplex.removeResponseListener(convertRecog);


					try {
						// get input temperature value
						
						double temp = Double.parseDouble(textField.getText());
						
					
						// no selection
						if (groupBtns.getSelection() == null || convertBtns.getSelection()==null) {
							return;
						}
						// get from selection
						ButtonModel btnModel = groupBtns.getSelection();
						String from = btnModel.getActionCommand();			
						
						String to = convertBtns.getSelection().getActionCommand();				
						
						System.out.println("Convert Result");
						Convert ct = new Convert(from,to,temp);
						double convertValue = ct.convert();

						DecimalFormat formatter = new DecimalFormat("#.###");
						formatter.setRoundingMode( RoundingMode.DOWN );
						String s = formatter.format(convertValue);

						textFieldResult.setText(s);	
						System.out.println(textFieldResult.getText());				
						speak("The temperature you want to convert to it is "+textFieldResult.getText()+to);		
						record.setEnabled(true);	
						mic.close();
		
					}catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid Input!");
					}



				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});

			//We don't want the application to terminate before this Thread terminates
			thread.setDaemon(false);		
			//Start the Thread
			thread.start();		
			//record.setEnabled(true);

			
		});
		stop.addActionListener((ActionEvent e) ->{
			System.exit(0);
		});
	
	}


	public void speak(String text) {
		System.out.println(text);
		//speakState.setState(1);
		//Create a new Thread because JLayer is running on the current Thread and will make the application to lag
		Thread thread = new Thread(() -> {
			try {
				
				//Create a JLayer instance
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();		
				//speakState.setState(0);	
				//System.out.println("Successfully got back synthesizer data");		

			} catch (IOException | JavaLayerException e) {				
				e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)				
			}
		});
		
		//We don't want the application to terminate before this Thread terminates
		thread.setDaemon(false);		
		//Start the Thread
		thread.start();		
	}

	
}

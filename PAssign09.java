/**
 * File: csci1302/ch16/MileageCalculator.java
 * Package: ch16
 * @author Christopher Williams
 * Edited: Ibrahim Jarra
 * Created on: Apr 12, 2017
 * Last Modified: Nov 16, 2021
 * Description:
 * Github Repo: https://github.com/ibrahimjarra/MileageCalculator
 */
package ch16;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PAssign09 extends Application {
	// default values/strings
    private double txtWidth = 125.0;
    private String defaultCalc = String.format("%.2f", 0.00);
    private String defaultEntry = String.format("%.2f", 0.00);
    private String defaultMileage = "Miles";
    private String defaultCapacity = "Gallons";
    private String defaultResult = "MPG";
    private String altMileage = "Kilometers";
    private String altCapacity = "Liters";
    private String altResult = "L/100KM";
    
    // create UI components split by type
    private Button btnCalc = new Button("Calculate");
    private Button btnReset = new Button("Reset");
    
    private Label lblDistance = new Label(defaultMileage);
    private Label lblCapacity = new Label(defaultCapacity);
    private Label lblResult = new Label(defaultResult);
    private Label lblEffType = new Label("Efficiency Type");
    
    private TextField tfDistance = new TextField(defaultEntry);
    private TextField tfCapacity = new TextField(defaultEntry);
    private TextField tfResult = new TextField(defaultCalc);
    
    private RadioButton rbMPG = new RadioButton(defaultResult);
    private RadioButton rbKPL = new RadioButton(altResult);
    private ToggleGroup tgConv = new ToggleGroup();
    
    private GridPane mainPane = new GridPane();

    private ObservableList <String> list = FXCollections.observableArrayList(defaultResult, altResult);
    private ComboBox<String> comboBox = new ComboBox<>(list);



    public void start(Stage primaryStage) {   	
    	// set toggle group for RadioButton -- Updated to Combo box
    	rbMPG.setToggleGroup(tgConv);
    	rbKPL.setToggleGroup(tgConv);


    	
        // set preferences for UI components
        tfDistance.setMaxWidth(txtWidth);
        tfCapacity.setMaxWidth(txtWidth);
        tfResult.setMaxWidth(txtWidth);
        tfResult.setEditable(false);
        rbMPG.setSelected(true);
        
        // create a main grid pane to hold items
        mainPane.setPadding(new Insets(10.0));
        mainPane.setHgap(txtWidth/2.0);
        mainPane.setVgap(txtWidth/12.0);
        
        // add items to mainPane
        mainPane.add(lblEffType, 0, 0);
        mainPane.add(comboBox,  0, 1);
        mainPane.add(lblDistance, 0, 2);
        mainPane.add(tfDistance, 1, 2);
        mainPane.add(lblCapacity, 0, 3);
        mainPane.add(tfCapacity, 1, 3);
        mainPane.add(lblResult, 0, 4);
        mainPane.add(tfResult, 1, 4);
        mainPane.add(btnReset, 0, 5);
        mainPane.add(btnCalc, 1, 5);
        
        // register action handlers
        btnCalc.setOnAction(e -> calcMileage());
        tfDistance.setOnAction(e -> calcMileage());
        tfCapacity.setOnAction(e -> calcMileage());
        tfResult.setOnAction(e -> calcMileage());
        comboBox.setOnAction(e-> {
            changeLabels();
            convertion();


        });
        btnReset.setOnAction(e -> resetForm());



        
        // create a scene and place it in the stage
        Scene scene = new Scene(mainPane); 
        
        // set and show stage
        primaryStage.setTitle("Mileage Calculator"); 
        primaryStage.setScene(scene); 
        primaryStage.show();      
        
        // stick default focus in first field for usability
        tfDistance.requestFocus();
    }
    
    /**
     * Convert existing figures and recalculate
     * This needs to be separate to avoid converting when
     * the conversion is not necessary
     */
    private void changeLabels() {
    	// distinguish between L/100KM and MPG
    	if (comboBox.getValue().equals(altResult) && lblCapacity.getText().equals(defaultCapacity)) {
        	// update labels
        	lblCapacity.setText(altCapacity);
        	lblDistance.setText(altMileage);
        	lblResult.setText(altResult);       	
         } else {
        	// update labels
        	lblCapacity.setText(defaultCapacity);
        	lblDistance.setText(defaultMileage);
        	lblResult.setText(defaultResult);
        }
    }
    
    /**
     * Calculate expenses based on entered figures
     */
    private void calcMileage() {       
    	// set default values
        double distance = 0.0, capacity = 0.0;
        
        // make sure to get numeric values only
        if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty()
        		&& tfDistance.getText() != null && !tfDistance.getText().isEmpty()) {
        	distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
        }

        // check for type of calculation
        double result = 0.0;
        if (rbKPL.isSelected()) {
        	// liters / 100KM
        	result = (distance != 0) ? capacity/(distance/100.0) : 0;
        } else {
        	// MPG
        	result = (capacity != 0) ? distance/capacity : 0;       	
        }
    
	    // update calculation fields with currency formatting
        tfResult.setText(String.format("%.2f", result));
    }

    private void convertion() {
        double defDistance = Double.parseDouble(tfDistance.getText());
        double defCapacity =Double.parseDouble(tfCapacity.getText());
        double defResult = Double.parseDouble(tfResult.getText());
        double distance = 0.0;
        double capacity = 0.0;
        double result = 0.0;

        if(comboBox.getValue() == defaultResult){
            tfDistance.setText(String.format("%.2f",defDistance/1.609));
            tfCapacity.setText(String.format("%.2f",defCapacity/3.78541));
            tfResult.setText(String.format("%.2f",235.215/defResult));

        }else if (comboBox.getValue() == altResult){
            distance = Double.parseDouble(tfDistance.getText());
            capacity = Double.parseDouble(tfCapacity.getText());
            result = Double.parseDouble(tfResult.getText());

            tfDistance.setText(String.format("%.2f", distance*1.609 ) );
            tfCapacity.setText(String.format("%.2f", capacity*3.78541 ) );
            tfResult.setText(String.format("%.2f", 235.215/result));
        }




    }
    
    /**
     * Reset all values in the application
     */
    private void resetForm() {
        // reset all form fields
    	rbMPG.setSelected(true);
        tfDistance.setText(defaultEntry);
        tfCapacity.setText(defaultEntry);
        tfResult.setText(defaultCalc);
        lblCapacity.setText(defaultCapacity);
    	lblDistance.setText(defaultMileage);
    	lblResult.setText(defaultResult);
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}

}


package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

import Model.Complexity;
import View.Mainwindow;

public class ComplexityController implements ActionListener{
	Complexity model;
	Mainwindow MainView;
	
	public ComplexityController(Mainwindow mainview){
		MainView = mainview;
		model = new Complexity();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	   
	   float result = model.calcComplexity(MainView.getPassword());
	   if( result < 10){
		   MainView.meterwords.setText("VeryWeak");
		   MainView.getView().setBackground(Color.red);
		   
	   }
	   else if( result  >=10 && result < 20){
		   MainView.meterwords.setText("Weak");
		   MainView.getView().setBackground(Color.red);
		   MainView.meterwords.setForeground(Color.black);
	   }
	   
	   else if (result >= 20 && result < 30){
		   MainView.meterwords.setText("Sufficient");
		   MainView.getView().setBackground(Color.yellow);
		   MainView.meterwords.setForeground(Color.black);
	   }
	   else if( result >= 30  && result < 40){
		   MainView.meterwords.setText("Good");
		   MainView.getView().setBackground(Color.blue);
		   MainView.meterwords.setForeground(Color.white);
		
	   }
	   else if (result >= 40){
		   MainView.meterwords.setText("Strong");
		   MainView.getView().setBackground(Color.green);
		   MainView.meterwords.setForeground(Color.black);
	   }
	   System.out.println(result);
	   MainView.meter.setValue((int) result);
	}
		//given an input be able to 
}

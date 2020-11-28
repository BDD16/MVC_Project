package View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.HulkController;
import Model.ViewHolder;

public class WindowContainer extends JFrame{
	private JFrame Window;
	
	public ViewHolder AllViews;
	
	public WindowContainer(){
		Init();
	}
	
	public void Init(){
		this.setWindow(new JFrame("Hulk Chat App"));
		this.getWindow().setSize(450,647);//done by the golden ratio
		this.getWindow().setOpacity((float) 1.0);
		this.getWindow().setLocationRelativeTo(null);
		((JFrame) this.getWindow()).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setName("WindowContainer");
	}

	public java.awt.Window getWindow() {
		// TODO Auto-generated method stub
		return Window;
	}

	public void setWindow(JFrame jFrame) {
		// TODO Auto-generated method stub
		Window = jFrame;
		
	}
	
	public void setWindow(JPanel jPanel){
		Window.add(jPanel);
	    Window.setVisible(true);
	}
}

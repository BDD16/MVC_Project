package Model;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public abstract class ViewSwitcher {
	//populate views here
	protected DefaultListModel<JFrame> ViewArray;
	
	abstract public JFrame getView(String Name);
	abstract public void setView(String Name);
	abstract public void addToViewArray(JFrame viewToAdd);
	abstract public void removeFromViewArray(JFrame viewToRemove);
	public DefaultListModel<JFrame> getViewArray() {
		return ViewArray;
	}
	public void setViewArray(DefaultListModel<JFrame> viewArray) {
		if(ViewArray == null){
			ViewArray = new DefaultListModel<JFrame>();
		}
		ViewArray = viewArray;
	}
	
	public JFrame getFromViewArray(String Name){
		if(ViewArray == null){
			ViewArray = new DefaultListModel<JFrame>();
		}
		for(int i = 0; i < ViewArray.getSize(); i += 1){
			if (ViewArray.getElementAt(i).getName() == Name){
				return ViewArray.getElementAt(i);
			}
		}
		//Didn't find anything
		return null;
	}

}

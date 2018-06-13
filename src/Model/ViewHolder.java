package Model;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public class ViewHolder extends ViewSwitcher {
	


	@Override
	public JFrame getView(String Name) {
	    return this.getFromViewArray(Name);
		
	}

	@Override
	public void setView(String Name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToViewArray(JFrame viewToAdd) {
		// TODO Auto-generated method stub
		if(ViewArray == null){
			ViewArray = new DefaultListModel<JFrame>();
		}
		this.getViewArray().addElement(viewToAdd);
		
	}

	@Override
	public void removeFromViewArray(JFrame viewToRemove) {
		// TODO Auto-generated method stub
		this.getViewArray().removeElement(viewToRemove);
		
	}

}

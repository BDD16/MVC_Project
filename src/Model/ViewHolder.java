package Model;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        
        public void addToViewPanelArray(JPanel viewToAdd){
            if(ViewArrayJPanel == null){
                ViewArrayJPanel = new DefaultListModel<JPanel>();
                this.getViewArrayJPanel().addElement(viewToAdd);
            }
        }

    @Override
    public JPanel getViewPanel(String panel) {
        if(ViewArrayJPanel == null){
			ViewArrayJPanel = new DefaultListModel<JPanel>();
		}
		for(int i = 0; i < ViewArrayJPanel.getSize(); i += 1){
			if (ViewArrayJPanel.getElementAt(i).getName() == panel){
				return ViewArrayJPanel.getElementAt(i);
			}
		}
		//Didn't find anything
		return null;
    }

}

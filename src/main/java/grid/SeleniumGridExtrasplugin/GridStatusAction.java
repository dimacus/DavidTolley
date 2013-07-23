package grid.SeleniumGridExtrasplugin;

import hudson.Extension;
import hudson.model.RootAction;

@Extension
public class GridStatusAction implements RootAction{

	public String getIconFileName() {
	 return "setting.png";
	}

	public String getDisplayName() {
		return "Grid status";
	}

	public String getUrlName() {
		return "/gridStatus";
	}

}

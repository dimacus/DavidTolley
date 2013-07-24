package grid.SeleniumGridExtrasplugin;

import hudson.Extension;
import hudson.model.RootAction;

@Extension
public class GridStatusAction implements RootAction{

	public String getIconFileName() {
	 return "up.png";
	}

	public String getDisplayName() {
		return "Selenium Grid Extras";
	}

	public String getUrlName() {
		return "/gridExtras";
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util;

/**
 *
 * @author ATX
 */

public class VARIABLES {
	/* Items */
	public static final long LifeTimeAfterDisconnected = 2592000000L;
	public static final long TimeBeforeDeleteAfterLifeTime = 2678400L;
	
	/* Communication */
	public final static String SERVICE_TAG = "toService";
	
	/* SharingManager */
	public final static int ReplicationsAccount = 5;
	public final static int CheckTimeAccount = 3000;
	public static final long MaxTimeSearch = 3000L;
	
	/* Network */
	public final static String NetworkFolderName = ".peerFolder";
	public final static String NetworkPeerName = "peer name";
	
	/* Store */
	public final static String ConfigFilePathWindows = "./SXPConfig.xml";
	public final static String ConfigFilePathLinux = "./SXPConfig.xml";
	public final static String ConfigFilePathMacOs = "";
	public final static String ConfigFilePathSolaris = "";
	public static final String ManagerFileName = "SXPManager.xml";
	public static final String ManagerFilePath = "./"+ManagerFileName;
	public static final String BootstrapFileName = "bootstrap.xml";
	public static final String BootstrapFilePath = BootstrapFileName;//was "./"
	
	/* AVProtocol */
	public static final int AVProtocolN = 10;
	public static final int AVProtocolK = 5;
	
	/* Email Invitation */
	public static final String EmailIntro = "Hello,%0A"
			+ "This is an automatically generated email.%0A"
			+ "What is the HulkNet application? %0A"
			+ "It is a platform to provide objects to the exchange. %0A"
			+ "Yes, unlike the usual commercial platform, you are not %0A"
			+ "going to buy or sell but rather trade items.%0A"
			+ "it's based on the latest %0A"
			+ "technology to improve security and reliability. %0A"
			+ "____________________________________________________________%0A%0A";
	public static final String EmailFoot = "%0A"
			+ "____________________________________________________________%0A%0A" 
			+ "To start using the application, simply download it by going on%0A"
			+ "       %0A%0A"
			+ "Once the application is downloaded and installed, launch the %0A"
			+ "application and click the \"Bootstrap Settings\". You simply %0A"
			+ "copy the contents of the frame above and paste it into the %0A"
			+ "text box provided for this purpose. Restart the application. %0A"
			+ "And now, you are ready to use the application.";
}

package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import resources.AppConfiguration;
import resources.FileStream;

public class ConfigureGameDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Group group1;
	private Label label1;
	private Combo comboAspectRatio;
	private Label label3;
	private Combo comboFiltering;
	private Button cancelButton;
	private Button okButton;
	private Label label2;
	private Combo comboWindowSize;
	private String[] aspects = new String[]{"4:3", "5:4", "8:7", "16:9", "16:10"};
	private String[][] resolutions = new String[][]{
			{"320x240", "480x360", "640x480", "800x600", "1024x768", "1152x864", "1280x960", "1600x1200"},
			{"450x360", "600x480", "800x640", "960x768", "1080x864", "1280x1024", "1600x1280"},
			{"320x280", "480x420", "512x448", "800x700", "1024x896", "1152x1008", "1280x1120", "1600x1400"},
			{"320x180", "480x270", "720x405", "800x450", "1024x576", "1366x768", "1280x720", "1600x900", "1920x1080"},
			{"320x200", "480x300", "768x480", "800x500", "1024x640", "1152x720", "1280x800", "1600x1000", "1920x1200"}
	};
	private String[] filters = new String[]{"Nearest (default)", "Interpolation", "Smooth edges", "Scanlines 25%", "Scanlines 50%"};

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	
	public ConfigureGameDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			AppConfiguration ac = FileStream.loadConfig();
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(383, 230);
			dialogShell.setText("Configure Game");
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonLData = new FormData();
				okButtonLData.width = 93;
				okButtonLData.height = 25;
				okButtonLData.bottom =  new FormAttachment(1000, 1000, -12);
				okButtonLData.right =  new FormAttachment(1000, 1000, -12);
				okButton.setLayoutData(okButtonLData);
				okButton.setText("OK");
				okButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						okButtonWidgetSelected(evt);
					}
				});
			}
			{
				group1 = new Group(dialogShell, SWT.NONE);
				group1.setText("Main configurations");
				FormData group1LData = new FormData();
				group1LData.left =  new FormAttachment(0, 1000, 12);
				group1LData.top =  new FormAttachment(0, 1000, 12);
				group1LData.width = 337;
				group1LData.height = 111;
				group1LData.right =  new FormAttachment(1000, 1000, -12);
				group1.setLayoutData(group1LData);
				group1.setLayout(null);
				{
					label1 = new Label(group1, SWT.NONE);
					label1.setText("Aspect ratio:");
					label1.setBounds(12, 31, 84, 23);
				}
				{
					comboAspectRatio = new Combo(group1, SWT.NONE);
					comboAspectRatio.setBounds(96, 31, 235, 23);
					comboAspectRatio.setItems(aspects);
					comboAspectRatio.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							comboAspectRatioWidgetSelected(evt);
						}
					});
					//comboAspectRatio.setOrientation(SWT.HORIZONTAL);
					for(int i = 0; i < aspects.length; i++){
						if(ac.aspect.equals(aspects[i])){
							comboAspectRatio.select(i);
							break;
						}
					}
				}
				{
					comboWindowSize = new Combo(group1, SWT.NONE);
					comboWindowSize.setBounds(96, 60, 235, 23);
					comboWindowSize.setItems(resolutions[comboAspectRatio.getSelectionIndex()]);
					for(int i = 0; i < resolutions[comboAspectRatio.getSelectionIndex()].length; i++){
						if(ac.resolution.equals(resolutions[comboAspectRatio.getSelectionIndex()][i])){
							comboWindowSize.select(i);
							break;
						}
					}
				}
				{
					label2 = new Label(group1, SWT.NONE);
					label2.setText("Window size:");
					label2.setBounds(12, 60, 84, 23);
				}
				{
					comboFiltering = new Combo(group1, SWT.NONE);
					comboFiltering.setBounds(96, 89, 235, 23);
					comboFiltering.setItems(filters);
					comboFiltering.select(ac.filtering);
				}
				{
					label3 = new Label(group1, SWT.NONE);
					label3.setText("Filtering:");
					label3.setBounds(12, 89, 84, 23);
				}
			}
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				cancelButton.setText("Cancel");
				FormData button1LData = new FormData();
				button1LData.width = 93;
				button1LData.height = 25;
				button1LData.bottom =  new FormAttachment(1000, 1000, -12);
				button1LData.right =  new FormAttachment(1000, 1000, -117);
				cancelButton.setLayoutData(button1LData);
				cancelButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						cancelButtonWidgetSelected(evt);
					}
				});
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void comboAspectRatioWidgetSelected(SelectionEvent evt) {
		comboWindowSize.setItems(resolutions[comboAspectRatio.getSelectionIndex()]);
		comboWindowSize.select(0);
	}
	
	private void cancelButtonWidgetSelected(SelectionEvent evt) {
		dialogShell.dispose();
	}
	
	private void okButtonWidgetSelected(SelectionEvent evt) {
		String resolution = comboWindowSize.getItem(comboWindowSize.getSelectionIndex());
		String aspect = comboAspectRatio.getItem(comboAspectRatio.getSelectionIndex());
		int filter = comboFiltering.getSelectionIndex();
		FileStream.saveConfig(resolution, aspect, filter );
		dialogShell.dispose();
	}

}

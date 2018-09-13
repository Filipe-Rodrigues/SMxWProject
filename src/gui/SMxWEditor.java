package gui;

import gfx.SceneDrawer;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.holongate.j2d.IPaintable;
import org.holongate.j2d.TimedCanvas;
import resources.FileStream;
import resources.SWTResourceManager;

public class SMxWEditor extends org.eclipse.swt.widgets.Composite {

	private static Display display;
	private static Shell shell;
	private TimedCanvas editorCanvas;
	private ScrolledComposite sc;
	private Menu menu1;
	private MenuItem configureAppMenuItem;
	private MenuItem aboutMenuItem;
	private MenuItem contentsMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private Menu optionsMenu;
	private MenuItem optionsMenuItem;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private Menu fileMenu;
	private MenuItem fileMenuItem;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public SMxWEditor(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(685, 527);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				FormData scLData = new FormData();
				scLData.top =  new FormAttachment(0, 1000, 12);
				scLData.width = 657;
				scLData.height = 451;
				scLData.left =  new FormAttachment(0, 1000, 12);
				scLData.right =  new FormAttachment(1000, 1000, -12);
				sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.BORDER);
				sc.setLayoutData(scLData);
				sc.setLayout(null);
				sc.setOrientation(SWT.HORIZONTAL);
				{
					editorCanvas = new TimedCanvas(sc, SWT.NONE, new IPaintable() {
						
						@Override
						public void redraw(Control arg0, GC arg1) {
						}
						
						@Override
						public void paint(Control arg0, Graphics2D arg1) {
							arg1.fillRect(16, 16, 16, 16);
						}
						
						@Override
						public Rectangle2D getBounds(Control arg0) {
							return null;
						}
					});
					sc.setContent(editorCanvas);
					editorCanvas.setBounds(2, 2, 8192, 448);
				}
			}
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							openFileMenuItem.setText("Open");
						}
						{
							newFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							newFileMenuItem.setText("New");
							newFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									newFileMenuItemWidgetSelected(evt);
								}
							});
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							saveFileMenuItem.setText("Save");
						}
						{
							closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							closeFileMenuItem.setText("Start game");
							closeFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									closeFileMenuItemWidgetSelected(evt);
								}
							});
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
							exitMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									exitMenuItemWidgetSelected(evt);
								}
							});
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					optionsMenuItem = new MenuItem(menu1, SWT.CASCADE);
					optionsMenuItem.setText("Options");
					{
						optionsMenu = new Menu(optionsMenuItem);
						optionsMenuItem.setMenu(optionsMenu);
						{
							configureAppMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
							configureAppMenuItem.setText("Configure app");
							configureAppMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									configureAppMenuItemWidgetSelected(evt);
								}
							});
						}
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							contentsMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							contentsMenuItem.setText("Contents");
						}
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("About");
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		display = Display.getDefault();
		shell = new Shell(display);
		SMxWEditor inst = new SMxWEditor(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
	}
	
	private void configureAppMenuItemWidgetSelected(SelectionEvent evt) {
		File file = new File("res/config/application.cfg");
		if(!file.exists()){
			FileStream.saveConfig("800x700", "8:7", 0);
		}
		ConfigureGameDialog cgDialog = new ConfigureGameDialog(getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		cgDialog.open();
	}
	
	private void closeFileMenuItemWidgetSelected(SelectionEvent evt) {
		SceneDrawer exemplo = new SceneDrawer();
		exemplo.inicia();
	}
	
	private void exitMenuItemWidgetSelected(SelectionEvent evt) {
		display.close();
	}
	
	private void newFileMenuItemWidgetSelected(SelectionEvent evt) {
		System.out.println("newFileMenuItem.widgetSelected, event="+evt);
		//TODO add your code for newFileMenuItem.widgetSelected
	}

}

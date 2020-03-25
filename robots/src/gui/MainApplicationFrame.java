package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import log.Logger;



/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private Info info = null;
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
    	if (new File("info.json").exists())
    	{
    		Gson gson = new GsonBuilder().create();
    		try {
				info = gson.fromJson(new FileReader("info.json"), Info.class);
			} catch (JsonSyntaxException e) {
				System.out.print("Can't read json(syntax)");
				e.printStackTrace();
			} catch (JsonIOException e) {
				System.out.print("Can't read json(fileException)");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.print("Can't read json(file not found)");
				e.printStackTrace();
			}
    	}
	        int inset = 50; 
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setBounds(inset, inset,
	            screenSize.width  - inset*2,
	            screenSize.height - inset*2);
	        setContentPane(desktopPane);
	        
	        LogWindow logWindow = createLogWindow();
	        addWindow(logWindow);
	
	        GameWindow gameWindow = new GameWindow();
	        if (info != null)
	        {
	        	gameWindow.setLocation(info.Internal2Frame.Location);
	        	gameWindow.setSize(info.Internal2Frame.Width, info.Internal2Frame.Height);
	        }
	        else
	        	gameWindow.setSize(400,  400);
	        addWindow(gameWindow);
	
	        setJMenuBar(generateMenuBar());
	        setDefaultCloseOperation(EXIT_ON_CLOSE);		
    }
    
    protected LogWindow createLogWindow()
    {
    	LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
    	if (info != null)
    	{
    		logWindow.setLocation(info.Internal1Frame.Location);
            logWindow.setSize(info.Internal1Frame.Width, info.Internal1Frame.Height);
            if(info.Internal1Frame.IsMin)
                    try {
                    	//Вроде бы этот метод сворачивает окно, но на деле ничего не проиходит
						logWindow.setIcon(true);
					} catch (PropertyVetoException e) {
						e.printStackTrace();
					}
    	}
    	else {
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
    	}
    	logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
        		"Управление режимом отображения приложения");
        lookAndFeelMenu.add(getSystemLookAndFeel());
        lookAndFeelMenu.add(getCrossplatformLookAndFeel());
        
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
        		"Тестовые команды"); 
        testMenu.add(getLogMessageItem());
         
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(getCloseMenuItem());
        return menuBar;
    }
    
    private JMenuItem getCloseMenuItem() {
    	JMenuItem closeMenu = new JMenuItem("Закрыть", KeyEvent.VK_C);
        closeMenu.addActionListener(new ActionListener() {
            @Override
        	public void actionPerformed(ActionEvent e) {
                   int result = JOptionPane.showOptionDialog(
                		   MainApplicationFrame.this, 
                		   "Закрыть приложение?", 
                		   "Подтверждение", 
                		   JOptionPane.YES_NO_OPTION, 
                		   JOptionPane.INFORMATION_MESSAGE, 
                		   null, 
                		   new Object[]{"Да", "Нет"}, 
                		   "Да");//то, что выделяется системой                   
                if (result == JOptionPane.YES_OPTION)
                	saveAndExit();
                }}
 	
        );
        return closeMenu;
}

	private JMenuItem getLogMessageItem() {
    	JMenuItem logMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        logMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        return logMessageItem;
}

	private JMenuItem getCrossplatformLookAndFeel() {
    	JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        return crossplatformLookAndFeel;
}

	private JMenuItem getSystemLookAndFeel() {
    	JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        return systemLookAndFeel;
}

	private void saveAndExit()
    {
    	JInternalFrame[] panes = desktopPane.getAllFrames();
    	//Упорядочивание окон в desktopPane
    	if (!(desktopPane.getAllFrames()[0] instanceof LogWindow))
    	{
    		panes[0] = desktopPane.getAllFrames()[1];
    		panes[1] = desktopPane.getAllFrames()[0];
    	} 
    	FrameInfo frame1 = new FrameInfo(panes[0].getWidth(), panes[0].getHeight(), panes[0].getLocation(), panes[0].isMaximum(), panes[0].isIcon());
    	FrameInfo frame2 = new FrameInfo(panes[1].getWidth(), panes[1].getHeight(), panes[1].getLocation(), panes[1].isMaximum(), panes[1].isIcon());
    	Info info = new Info(frame1, frame2);
    	Gson gson = new GsonBuilder().create();
    	FileWriter outStream;
    	try {
			outStream = new FileWriter("info.json", false);
			outStream.write(gson.toJson(info));
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			System.out.print("JSON file wasn't write");
			e.printStackTrace();
		}
    	System.exit(0);
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}

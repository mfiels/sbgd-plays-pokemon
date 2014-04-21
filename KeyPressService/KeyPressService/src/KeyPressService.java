import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class KeyPressService {

	private static final int PORT_NUMBER = 9090;
	private static final Map<String, Integer> KEY_MAP = new HashMap<>();
	static {
		KEY_MAP.put("up", 		KeyEvent.VK_UP);
		KEY_MAP.put("down", 	KeyEvent.VK_DOWN);
		KEY_MAP.put("left", 	KeyEvent.VK_LEFT);
		KEY_MAP.put("right", 	KeyEvent.VK_RIGHT);
		KEY_MAP.put("a", 		KeyEvent.VK_Z);
		KEY_MAP.put("b", 		KeyEvent.VK_X);
		KEY_MAP.put("l", 		KeyEvent.VK_A);
		KEY_MAP.put("r", 		KeyEvent.VK_S);
		KEY_MAP.put("start", 	KeyEvent.VK_ENTER);
		KEY_MAP.put("select", 	KeyEvent.VK_BACK_SPACE);
	}
	
	private Robot robot;
	private ServerSocket listenSocket;
	
	public KeyPressService() 
			throws Exception {
		this.robot = new Robot();
		this.listenSocket = new ServerSocket(PORT_NUMBER);
		
		listenLoop();
	}
	
	private void listenLoop() 
			throws Exception {
		while (true) {
			L("Waiting for a connection...");
			Socket connectionSocket = listenSocket.accept();
			L("Connection made");
			Scanner in = new Scanner(connectionSocket.getInputStream());
			while (in.hasNext()) {
				String line = in.nextLine();
				L("Got message: " + line);
				doKeyPress(line);
			}
			in.close();
			connectionSocket.close();
			L("Connection closed");
		}
	}
	
	private void doKeyPress(String line) {
		line = line.toLowerCase();
		if (KEY_MAP.containsKey(line)) {
			int keyCode = KEY_MAP.get(line);
			robot.keyPress(keyCode);
			robot.delay(100);
			robot.keyRelease(keyCode);
		} else {
			L("Bad message: " + line);
		}
	}
	
	private static void L(String msg) {
		System.out.println(msg);
	}
	
	public static void main(String[] args) 
			throws Exception {
		new KeyPressService();
	}
	
}

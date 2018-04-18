import java.util.Scanner;

public class Paint {
	public static final String CMD_CANVAS = "C";
	public static final String CMD_LINE = "L";
	public static final String CMD_RECT = "R";
	public static final String CMD_BUCKET_FILL = "B";
	public static final String CMD_QUIT = "Q";
	public static final String CMD_HELP = "H";
	public static final String NEW_LINE = "\n";
	public static final String USAGE_HEADER = "Usage:";
	
	Canvas canvas;
	
	private void run() {
		displaySameLine("enter command: ");
		Scanner scanner = new Scanner(System.in);
		String cmd = scanner.nextLine();
		while(!CMD_QUIT.equalsIgnoreCase(cmd)) {
			
			draw(cmd);
			displaySameLine("enter command: ");
			cmd = scanner.nextLine();
			
		}
		
		display("Session ended.");
		
		scanner.close();
		
	}
	
	private void draw(String command) {
		String[] cmd = command.split(" ");
		switch(cmd[0]) {
			case CMD_CANVAS : { //command for canvas creation
				try {					
					int w = Integer.parseInt(cmd[1]);
					int h = Integer.parseInt(cmd[2]);
					
					canvas = new CanvasImpl(w, h);
					canvas.display();
					
				} catch (Exception e) {
					displayError(CMD_CANVAS);
					display(NEW_LINE + USAGE_HEADER);
					displayCommandUsage(CMD_CANVAS);
				}
				break;
			}
			
			case CMD_LINE : { //command for creating a line within the canvas
				try {
					
					if(!canvasExists()) {
						break;
					}
					
					int x1 = Integer.parseInt(cmd[1]);
					int y1 = Integer.parseInt(cmd[2]);
					int x2 = Integer.parseInt(cmd[3]);
					int y2 = Integer.parseInt(cmd[4]);
					
					if(!canvas.isWithinDrawableBounds(x1, y1) || !canvas.isWithinDrawableBounds(x2, y2)) {
						display("Line must be drawn within the canvas");
						throw new Exception();
					}
					
					canvas.createLine(x1, y1, x2, y2);
					canvas.display();
				} catch (Exception e) {
					displayError(CMD_LINE);
					display(NEW_LINE + USAGE_HEADER);
					displayCommandUsage(CMD_LINE);
				}
				
				break;
			}
			
			case CMD_RECT : { //command for creating a rectangle within the canvas
				try {
					
					if(!canvasExists()) {
						break;
					}
					
					int x1 = Integer.parseInt(cmd[1]);
					int y1 = Integer.parseInt(cmd[2]);
					int x2 = Integer.parseInt(cmd[3]);
					int y2 = Integer.parseInt(cmd[4]);
					
					if(!canvas.isWithinDrawableBounds(x1, y1) || !canvas.isWithinDrawableBounds(x2, y2)) {
						display("Rectangle must be drawn within the canvas");
						throw new Exception();
					}
					
					canvas.createRectangle(x1, y1, x2, y2);
					canvas.display();
				} catch (Exception e) {
					displayError(CMD_RECT);
					display(NEW_LINE + USAGE_HEADER);
					displayCommandUsage(CMD_RECT);
				}
				
				break;
			}
			
			case CMD_BUCKET_FILL : { //command for filling in adjacent pixels with the specified color
				try {
					
					if(!canvasExists()) {
						break;
					}
					
					int x = Integer.parseInt(cmd[1]);
					int y = Integer.parseInt(cmd[2]);
					char color = cmd[3].charAt(0);
					
					if(!canvas.isWithinDrawableBounds(x, y)) {
						display("Target point must be drawn within the canvas");
						throw new Exception();
					}
					
					canvas.bucketFill(x, y, color);
					canvas.display();
				} catch (Exception e) {
					displayError(CMD_BUCKET_FILL);
					display(NEW_LINE + USAGE_HEADER);
					displayCommandUsage(CMD_BUCKET_FILL);
				}
				
				break;
			}
			
			default : { //shows the available commands and their corresponding description and usage
				displayUsage();
			}
		}
	}
	
	private boolean canvasExists() {
		if(canvas == null) {
			display(MessagesUtil.getMessage(MessagesUtil.MSG_PREFIX_CMD + CMD_CANVAS + MessagesUtil.MSG_SUFFIX_EMPTY));
			display(NEW_LINE + USAGE_HEADER);
			displayCommandUsage(CMD_CANVAS);
		}
		
		return canvas != null;
	}
	
	private void displayUsage() {
		display("Command\t\tDescription");
		displayCommandUsage(CMD_CANVAS);
		displayCommandUsage(CMD_LINE);
		displayCommandUsage(CMD_RECT);
		displayCommandUsage(CMD_BUCKET_FILL);
		displayCommandUsage(CMD_QUIT);
		display(NEW_LINE);
	}
	
	private void displayCommandUsage(String command) {
		display(MessagesUtil.getCommand(command) + MessagesUtil.getCommandDescription(command));
		display(MessagesUtil.getCommandExample(command));
	}
	
	public static void displayError(String command) {
		display(MessagesUtil.getCommandError(command));
	}
	
	public static void display(Object obj) {
		Logger.display(obj);
	}
	
	public static void displaySameLine(Object obj) {
		Logger.displaySameLine(obj);
	}
	
	public void test(){
		display("enter command: ");
		Scanner scanner = new Scanner(System.in);
		String cmd = scanner.nextLine();
		display(cmd);
	}
	
	public static void main (String[] args) {
		Paint paint = new Paint();
		paint.run();
	}
}

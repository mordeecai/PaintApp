
public interface Canvas {
	
	public boolean isBorder(int x, int y);
	
	public boolean isTopBorder(int x, int y);
	
	public boolean isBottomBorder(int x, int y);
	
	public boolean isLeftBorder(int x, int y);
	
	public boolean isRightBorder(int x, int y);
	
	public boolean isYWithinBorder(int y);
	
	public boolean isXWithinBorder(int x);
	
	public boolean isWithinDrawableBounds(int x, int y);
	
	public void createBorder();
	
	public void colorPixel(int x, int y, char color);
	
	public char determinePixelColor(int x, int y);
	
	public boolean arePixelsAdjacent(int x1, int y1, int x2, int y2, char originalColor);
	
	public char getPixelColor(int x, int y);
	
	public void bucketFill(int x, int y, char color);
	
	public void createLine(int x1, int y1, int x2, int y2) throws Exception;
	
	public void createVerticalLine(int x1, int y1, int y2);
	
	public void createHorizontalLine(int x1, int x2, int y2);
	
	public void createRectangle(int x1, int y1, int x2, int y2);
	
	public void display();
}

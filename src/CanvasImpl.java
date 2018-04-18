
public class CanvasImpl implements Canvas {
	
	private int width;
	private int height;
	private char[][] body;
	public static final char LINE_COLOR = 'x';
	public static final char EMPTY = '\u0000';
	public static final char BLANK_COLOR = ' ';
	public static final char BORDER_BOTTOM_TOP_COLOR = '-';
	public static final char BORDER_LEFT_RIGHT_COLOR = '|';
	
	public CanvasImpl(int width, int height) throws Exception {
		if(width < 1 || height < 1) {
			throw new Exception("Invalid canvas dimension.");
		}
		this.width = width + 2;
		this.height = height + 2;
		this.body = new char[this.height][this.width];
		
		this.createBorder();
		this.bucketFill(1, 1, BLANK_COLOR);
	}

	@Override
	public boolean isBorder(int x, int y) {
		return isTopBorder(x, y) ||
				isBottomBorder(x, y) ||
				isLeftBorder(x, y) ||
				isRightBorder(x, y);
	}

	@Override
	public boolean isTopBorder(int x, int y) {
		return (y == 0);
	}

	@Override
	public boolean isBottomBorder(int x, int y) {
		return (y == (height - 1));
	}

	@Override
	public boolean isLeftBorder(int x, int y) {
		return (x == 0);
	}

	@Override
	public boolean isRightBorder(int x, int y) {
		return (x == (width -1));
	}

	@Override
	public boolean isYWithinBorder(int y) {
		return y > 0 && y < this.height - 1;
	}

	@Override
	public boolean isXWithinBorder(int x) {
		return x > 0 && x < this.width - 1;
	}

	@Override
	public boolean isWithinDrawableBounds(int x, int y) {
		return isXWithinBorder(x) && isYWithinBorder(y);
	}

	@Override
	public void createBorder() {
		//left canvas border
		createVerticalLine(0, 0, this.height - 1);
		
		//right canvas border
		createVerticalLine(this.width - 1, 0, this.height - 1);
		
		//top canvas border
		createHorizontalLine(0, this.width - 1, 0);
		
		//bottom canvas border
		createHorizontalLine(0, this.width - 1, this.height - 1);
	}

	@Override
	public void bucketFill(int x, int y, char color) {
		
		if(!isWithinDrawableBounds(x, y)) {
			return;
		}
		
		char originalColor = this.body[y][x];
		
		if(this.body[y][x] == color) {
			return;
		}
		
		if(arePixelsAdjacent(x, y, x + 1, y, originalColor)) {
			colorPixel(x, y, color);
			//find east			
			bucketFill(x + 1, y, color);
		}
		
		if(arePixelsAdjacent(x, y, x - 1, y, originalColor)) {
			colorPixel(x, y, color);
			//find west
			bucketFill(x - 1, y, color);
		}
		
		if(arePixelsAdjacent(x, y, x, y - 1, originalColor)) {
			colorPixel(x, y, color);
			//find north
			bucketFill(x, y - 1, color);
		}
		
		if(arePixelsAdjacent(x, y, x, y + 1, originalColor)) {
			colorPixel(x, y, color);
			//find south
			bucketFill(x, y + 1, color);
		}

		colorPixel(x, y, color);
	}

	@Override
	public void colorPixel(int x, int y, char color) {
		this.body[y][x] = color;
	}

	@Override
	public void display() { //display the current state of the canvas
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				System.out.print(this.body[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void createLine(int x1, int y1, int x2, int y2) throws Exception {
		if(x1 == x2) {
			createVerticalLine(x1, y1, y2);
		} else if( y1 == y2) {
			createHorizontalLine(x1, x2, y1);
		} else {
			throw new Exception("Line not supported (Supported line orientation: veritical and horizontal)");
		}
	}

	@Override
	public void createVerticalLine(int x, int y1, int y2) {
		for(int i = y1; i <= y2; i++) {
			this.body[i][x] = determinePixelColor(x, i);
		}	
	}

	@Override
	public void createHorizontalLine(int x1, int x2, int y) {
		for(int i = x1; i <= x2; i++) {
			this.body[y][i] = determinePixelColor(i, y);
		}
	}

	@Override
	public void createRectangle(int x1, int y1, int x2, int y2) {
		//left border
		createVerticalLine(x1, y1, y2);
		
		//right border
		createVerticalLine(x2, y1, y2);
		
		//top border
		createHorizontalLine(x1, x2, y1);
		
		//bottom border
		createHorizontalLine(x1, x2, y2);
	}

	@Override
	public char determinePixelColor(int x, int y) {
		if(!isBorder(x, y)) {
			return LINE_COLOR;
		} else if (isTopBorder(x, y) || isBottomBorder(x, y)) {
			return BORDER_BOTTOM_TOP_COLOR;
		} else if (isLeftBorder(x, y) || isRightBorder(x, y)) {
			return BORDER_LEFT_RIGHT_COLOR;
		} else {
			return BLANK_COLOR;
		}
	}

	@Override
	public boolean arePixelsAdjacent(int x1, int y1, int x2, int y2, char originalColor) {
		if(this.body[y1][x1] == EMPTY && this.body[y2][x2] == EMPTY) {
			return true;
		}
		return this.body[y2][x2] == originalColor; //if the adjacent pixel has the original color
	}

	@Override
	public char getPixelColor(int x, int y) {
		return this.body[y][x];
	}
}

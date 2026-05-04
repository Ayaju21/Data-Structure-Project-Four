package application;

public interface Drawable {
	default String drawLevelElement(String str , int maxHeight , int height , int wordLength) {
		int nodes = (int) Math.pow(2 , maxHeight - height);
		double spacesNum = ((Math.pow(2 , maxHeight) - nodes) / nodes) / 2;

		String spaces = mult((int) (spacesNum * wordLength));
		return spaces
				+ String.format("%" + wordLength + "s" , str)
				+ spaces;
	}

	default String drawLevelElement(String str , int maxHeight , int height , int wordLength , boolean pretify) {
		if ( pretify ) {
			return drawLevelElement(str , maxHeight , height , wordLength);
		}
		return " " + str + " ";
	}

	default String mult(int times) {
		String s = "";
		for ( int i = 0 ; i < times ; i++ ) {
			s += " ";
		}
		return s;
	}

}


package palletes;

public class Tilemap32 {

	private Tile32[][][] tiles32;

	public Tilemap32() {
		super();
		this.tiles32 = new Tile32[8][16][16];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 16; j++){
				for(int k = 0; k < 16; k++){
					tiles32[i][j][k] = null;
				}
			}
		}
	}

	public void addTile(Tile32 tile, int page, int line, int column) throws InvalidPositionException{
		if(page > 7 || page < 0 || line > 15 || line < 0 || column > 15 || column < 0){
			throw new InvalidPositionException(page, line, column);
		} else {
			tiles32[page][line][column] = tile;
		}
	}

	public Tile32 getTileAtPosition(int page, int line, int column) throws InvalidPositionException{
		if(page > 7 || page < 0 || line > 15 || line < 0 || column > 15 || column < 0){
			throw new InvalidPositionException(page, line, column);
		} else {
			return tiles32[page][line][column];
		}
	}
	
}

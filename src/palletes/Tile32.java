package palletes;

public class Tile32 {

	private byte[][] tileImage;

	public Tile32(byte[][] tileImage) throws InvalidTile32PalleteNumberException {
		if(tileImage.length == 8){
			this.tileImage = tileImage;
		}else{
			throw new InvalidTile32PalleteNumberException(tileImage.length);
		}
	}
	
	public Tile32(){
		this.tileImage = new byte[8][];
	}
	
	public void addImage(byte[] image, int pallete) throws InvalidTile32PalleteNumberException{
		if(pallete >= 0 && pallete < 8){
			tileImage[pallete] = image;
		}else{
			throw new InvalidTile32PalleteNumberException(tileImage.length);
		}
	}
	
	public void setTile(byte[][] tileImage) throws InvalidTile32PalleteNumberException{
		if(tileImage.length == 8){
			this.tileImage = tileImage;
		}else{
			throw new InvalidTile32PalleteNumberException(tileImage.length);
		}
	}
	
	public byte[] getImage(int pallete) throws InvalidTile32PalleteNumberException{
		if(pallete >= 0 && pallete < 8){
			return tileImage[pallete];
		}else{
			throw new InvalidTile32PalleteNumberException(tileImage.length);
		}
	}
	
	public byte[][] getTile() {
		return this.tileImage;
	}

}

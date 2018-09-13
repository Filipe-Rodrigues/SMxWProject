package levels;

public class Level_M_Style {
	/**Horizontal level layout*/
	public static final short HORIZONTAL = 0;
	/**Vertical level layout*/
	public static final short VERTICAL = 1;
	/**Player affects camera moving vertically*/
	public static final short ALLOW_VERTICAL_SCROLL = 0;
	/**Player doesn't affect camera moving vertically, unless flying or climbing something*/
	public static final short NO_VERTICAL_SCROLL_DEFAULT = 2;
	/**Player doesn't affect camera moving vertically*/
	public static final short NO_VERTICAL_SCROLL = 4;
	/**Fixed camera at the start of the level*/
	public static final short NO_VERTICAL_HORIZONTAL_SCROLL = 6;
	/**Don't use layer 2*/
	public static final short NO_LAYER_2 = 0;
	/**Use layer 2 without player/sprite interaction*/
	public static final short LAYER_2_DISABLE_INTERACTION = 8;
	/**Use layer 2 with player/sprite interaction*/
	public static final short LAYER_2_ENABLE_INTERACTION = 24;
	/**Normal level friction*/
	public static final short NORMAL_FRICTION = 0;
	/**Slippery level friction*/
	public static final short ICE_FRICTION = 32;
	/**Water-flooded level*/
	public static final short WATER_LEVEL = 64;
	/**Camera moves as player moves*/
	public static final short DISABLE_AUTO_SCROLLING = 0;
	/**Player moves bounded by camera movement*/
	public static final short ENABLE_AUTO_SCROLLING = 128;
	
	/**Combination of various properties, described by constants defined in this class*/
	public short level_Type;
	public short fg_bg_gfx, sprite_gfx;
	
	
}

import java.awt.Graphics2D;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10]; // We'll have 10 different tile types
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        // Load different tile types
        tile[0] = new Tile("res/tiles/grass.png", false);    // Grass
        tile[1] = new Tile("res/tiles/wall.png", true);      // Wall
        tile[2] = new Tile("res/tiles/water.png", true);     // Water
        tile[3] = new Tile("res/tiles/earth.png", false);    // Earth
        tile[4] = new Tile("res/tiles/tree.png", true);      // Tree
    }

    public void loadMap() {
        int map[][] = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,2,0,4,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        for(int row = 0; row < gp.maxScreenRow; row++) {
            for(int col = 0; col < gp.maxScreenCol; col++) {
                mapTileNum[col][row] = map[row][col];
            }
        }
    }

    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        // Calculate the visible tile range
        int startCol = cameraX / gp.titleSize;
        int startRow = cameraY / gp.titleSize;
        int endCol = startCol + (gp.screenWidth / gp.titleSize) + 1;
        int endRow = startRow + (gp.screenHeight / gp.titleSize) + 1;

        // Clamp to map boundaries
        if(startCol < 0) startCol = 0;
        if(startRow < 0) startRow = 0;
        if(endCol > gp.maxScreenCol) endCol = gp.maxScreenCol;
        if(endRow > gp.maxScreenRow) endRow = gp.maxScreenRow;

        // Draw only visible tiles
        for(int row = startRow; row < endRow; row++) {
            for(int col = startCol; col < endCol; col++) {
                int tileNum = mapTileNum[col][row];
                int x = col * gp.titleSize - cameraX;
                int y = row * gp.titleSize - cameraY;
                
                // Only draw if the tile is at least partially visible
                if(x + gp.titleSize > 0 && y + gp.titleSize > 0 && 
                   x < gp.screenWidth && y < gp.screenHeight) {
                    g2.drawImage(tile[tileNum].image, x, y, gp.titleSize, gp.titleSize, null);
                }
            }
        }
    }
} 
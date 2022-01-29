package cursor_education_JB;

import com.company.gameobjects.Food;
import com.company.gameobjects.ObjectOnScreen;
import com.company.gameobjects.Snake;
import com.company.gameobjects.Wall;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameController {
    private Snake snake;
    private List<ObjectOnScreen> gameObjects;
    private GameScreen gameScreen;

    private Scanner scanner = new Scanner(System.in);


    private void init() {

        snake =new Snake(7,3);

        gameObjects =new CopyOnWriteArrayList<>();
        gameObjects.add(new Food(1,2));
        gameObjects.add(new Food(4,6));
        gameObjects.add(new Food(8,2));
        gameObjects.add(new Wall(9,4));
        gameObjects.add(new Wall(5,5));
        gameScreen = new GameScreen();

    }

    public void startGame() {
        init();
        mainLoop();

    }

    private void mainLoop() {

        // main loop
        while (true) {
            refreshScreen();
            userInput();
            updateGameState();
        }
    }

    private void updateGameState() {
        for (ObjectOnScreen obj : gameObjects) {
            if (snake.intersectsWith(obj)) {
                System.out.println("Collide!");
                snake.collideWith(obj);
                gameObjects.remove(obj);
                generateFood();
            }
        }
    }

    private void generateFood() {
        boolean newFoodGenerated = false;
        int counter = 0;
        while (!newFoodGenerated && counter++ <3 ){
            int newX = (int) (Math.random()* gameScreen.screenSize);
            int newY = (int) (Math.random()* gameScreen.screenSize);

            boolean interstectionFound = false;
            for (ObjectOnScreen coll : gameObjects){
                if (coll.intersectsWith(newX, newY)|| snake.intersectsWith(newX,newY)){
                    interstectionFound = true;
                };
            }
            if(!interstectionFound) {
                gameObjects.add(new Food(newX, newY));
                newFoodGenerated = true;
            }
        }
    }

    private void userInput() {
        char input;
        switch (input = scanner.nextLine().charAt(0)) {
            case 'a':
                if (snake.x - 1< 0){
                    snake.x = gameScreen.screenSize;
                }
                else {
                    snake.x = snake.x - 1;
                }
                break;
            case 'd':

                if (snake.x+1 > gameScreen.screenSize){
                    snake.x = 0;
                }
                else {
                    snake.x = snake.x + 1;
                }
                break;
            case 'w':
                if (snake.y-1< 0){
                    snake.y = gameScreen.screenSize;
                }
                else {
                snake.y = snake.y - 1;
                }
                break;
            case 's':

                if (snake.y+1 > gameScreen.screenSize) {
                    snake.y = 0;
                }
                else{
                    snake.y = snake.y + 1;
                }
                break;

        }
    }

    private void refreshScreen() {
        gameScreen.fillScreenWithBlankCells();
        gameScreen.setObjectOnScreen(snake);

        for (ObjectOnScreen drawable : gameObjects) {
            gameScreen.setObjectOnScreen(drawable);
        }
        gameScreen.printScreen();
    }
}

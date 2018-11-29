package uet.oop.bomberman.gui.menu;
import uet.oop.bomberman.Game;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {
    public static String GetHighScoreFromFile(Game game) throws IOException {
        String path = "res\\files\\high_score.txt";
        FileReader fileReader = new FileReader(path);
        int i;
        String str="";
        while((i=fileReader.read())!=-1){
            str = str+ (char)i;
        }
        System.out.println(1111111111);
        System.out.println(str);
        fileReader.close();
        return str ;

    }
    //viet noi dung moi vao file, noi dung cu mat
    public static void SetHighScoreToFile(Game game)throws IOException {
        String path = "res\\files\\high_score.txt";
        FileWriter fileWriter = new FileWriter(path);
        int currentHighScore = game.getBoard().getPoints();
        String str = "" + currentHighScore;
        if(str.compareTo(GetHighScoreFromFile(game)) >= 0)
            for (int i = 0; i < str.length(); i++)
                fileWriter.write(str.charAt(i));

        //close the file
        fileWriter.close();
    }
}



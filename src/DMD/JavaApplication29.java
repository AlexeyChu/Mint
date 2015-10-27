package DMD;


import java.io.File;

/**
 * Created by alex on 26.10.15.
 */
public class JavaApplication29 {

    public static void main(String[] args) throws InterruptedException {

        //чтение каталога
        File folder = new File("/bin");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }

        //удаление файлов
        for (File file : new File("/toor").listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }
}

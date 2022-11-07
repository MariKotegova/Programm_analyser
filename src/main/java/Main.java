import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static BlockingQueue<String> textA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> textB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> textC = new ArrayBlockingQueue<>(100);
    public static final int QUANTITY = 10_000;
    public static final int LENGHT = 100_000;
    public static final String TEXT = "abc";

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < QUANTITY; i++) {
                try {
                    String text = generateText(TEXT, LENGHT);
                    textA.put(text);
                    textB.put(text);
                    textC.put(text);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        Thread threadA = new Thread(() -> {
            try {
                int result = 0;
                String resultText = null;
                for (int j = 0; j < QUANTITY; j++) {
                    String text = textA.take();
                    int count = 0;
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == 'a') {
                            count++;
                        }
                    }
                    if (count > result) {
                        result = count;
                        resultText = text;
                    }
                }
                result(resultText, 'a', result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                int result = 0;
                String resultText = null;
                for (int j = 0; j < QUANTITY; j++) {
                    String text = textB.take();
                    int count = 0;
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == 'b') {
                            count++;
                        }
                    }
                    if (count > result) {
                        result = count;
                        resultText = text;
                    }
                }
                result(resultText, 'b', result);
             } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                int result = 0;
                String resultText = null;
                for (int j = 0; j < QUANTITY; j++) {
                    String text = textC.take();
                    int count = 0;
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == 'c') {
                            count++;
                        }
                    }
                    if (count > result) {
                        result = count;
                        resultText = text;
                    }
                }
                result(resultText, 'c', result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        threadA.start();
        threadB.start();
        threadC.start();

        threadC.join();
        threadB.join();
        threadA.join();
        thread.join();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void result (String resultText, char symbol, int result){
        System.out.println("Текст - '" + resultText.substring(0, 10) +
                "...' содержет самое большое колличество символов '" + symbol + "' " +
                result + " шт.");
    }
}

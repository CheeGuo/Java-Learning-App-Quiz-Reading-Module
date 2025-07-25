// Created by: Yee Pei Ling (101378)
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class Gamification extends DataSystem implements GamificationInterface {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Start the timer
    public void startTimer() {
        startTime = LocalDateTime.now();
    }

    // Stop the timer
    public void stopTimer() {
        endTime = LocalDateTime.now();
    }

    // Get time taken in seconds
    public long getTimeTakenSeconds() {
        if (startTime != null && endTime != null) {
            Duration duration = Duration.between(startTime, endTime);
            return duration.getSeconds();
        } else {
            return 0;
        }
    }

    // Determine badge title
    public String badgeTitle(int points) {
        if (points >= 80) return "Outstanding!";
        else if (points >= 60) return "That's good!";
        else if (points >= 40) return "Good try!";
        else if (points >= 20) return "You can do better!";
        else return "Dont give up!";
    }

    // Call this at end of quiz to record score, time, and badge
public void saveResult(int score) {
        saveResult(score, getTimeTakenSeconds());
    }

    // Overloaded: with time
    public void saveResult(int score, long seconds) {
        saveResult(score, seconds, LocalDateTime.now().format(DateTimeFormatter.ofPattern("[dd.MM.yyyy] [HH.mm.ss]")));
    }

    // Overloaded: full custom
    public void saveResult(int score, long seconds, String timestamp) {
        String badge = badgeTitle(score);

        String history = timestamp + " You had attempted the quiz [" + score + "/20]\n" +
                         timestamp + " You had received the badge: " + badge + "\n" +
                         timestamp + " Time taken: " + seconds + " seconds\n";

        String badgeLine = timestamp + " You had received the badge: " + badge + "\n";

        WriteFile(history);
        WriteFileBadge(badgeLine);

        JOptionPane.showMessageDialog(null,
            "Quiz Completed!\n" +
            "Score: " + score + "/20\n" +
            "Badge: " + badge + "\n" +
            "Time: " + seconds + " seconds");
    }

}
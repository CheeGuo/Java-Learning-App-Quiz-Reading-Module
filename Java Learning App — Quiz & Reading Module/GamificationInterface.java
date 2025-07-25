public interface GamificationInterface{
    void startTimer();
    void stopTimer();
    long getTimeTakenSeconds();
    String badgeTitle(int points);
    void saveResult(int score);
}

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class JavaProject {

    // Main GUI components
    JFrame frame; // Home page frame
    BackgroundPanel mainPanel; // Panel with background image
    ReadingModule module; // Learning module page
    QuizModule quiz; // Learning module page
    Image backgroundImage; // Background image for all screens

    // Constructor: Initializes frame and background
    public JavaProject() {
        try {
            // Load background image from file
            backgroundImage = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up frame
        frame = new JFrame("GreenVerse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);

         // Initialize main panel with background and layout
        mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Position constraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        // Load and display app logo
        try {
            ImageIcon icon = new ImageIcon("Logo.png");
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImg));
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(iconLabel);

            gbc.insets = new Insets(300, 0, 0, 0);
            gbc.gridy++;
            mainPanel.add(Box.createVerticalStrut(10), gbc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // START button setup
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        startButton.setBackground(new Color(178, 235, 166));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(120, 35));
        mainPanel.add(startButton, gbc);

        // Button action - go to homepage
        startButton.addActionListener(e -> showHome());

        // Add main panel to frame
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);// Make container transparent
        container.add(mainPanel, BorderLayout.CENTER);
        frame.setContentPane(container);
        frame.setVisible(true);
    }

    // Home page with navigation buttons
    public void showHome() {
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        mainPanel.removeAll();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("GREENVERSE");
        title.setFont(new Font("Brush Script MT", Font.BOLD, 70));
        title.setForeground(Color.BLACK);

         // Navigation buttons
        JButton learningBtn = createStyledButton("Learning Module", new Color(178, 235, 166));
        JButton quizBtn = createStyledButton("Quiz", new Color(178, 235, 166));
        JButton historyBtn = createStyledButton("History", new Color(178, 235, 166));
        JButton achievementBtn = createStyledButton("Achievement", new Color(178, 235, 166));
        JButton exitBtn = createStyledButton("Exit", new Color(255, 153, 153));

        // Button actions
        learningBtn.addActionListener(e -> showLearning());
        quizBtn.addActionListener(e -> showQuiz());
        historyBtn.addActionListener(e -> showHistory());
        achievementBtn.addActionListener(e -> showAchievement());
        exitBtn.addActionListener(e -> System.exit(0));

        // Add all components
        mainPanel.add(title, gbc);
        mainPanel.add(learningBtn, gbc);
        mainPanel.add(quizBtn, gbc);
        mainPanel.add(historyBtn, gbc);
        mainPanel.add(achievementBtn, gbc);
        mainPanel.add(exitBtn, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    // Create custom-styled buttons
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(200, 38));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        return button;
    }
    // Show learning module
    void showLearning() {
        mainPanel.removeAll();
        module = new ReadingModule(this);
        module.dashBoard();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(module.getPanel(), BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
        }
    // Show quiz module    
    void showQuiz() {
        mainPanel.removeAll();
        quiz = new QuizModule(this);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    // Show user history (past results)
    void showHistory() {
        mainPanel.removeAll();

        JLabel title = new JLabel("History");
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(false);

        JTextArea area = new JTextArea(new DataSystem().ReadFile());
        area.setEditable(false);
        area.setOpaque(false); // Make background transparent
        area.setBackground(new Color(0, 0, 0, 0)); // Fully transparent background
        area.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        area.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(450, 500));

        // Vertical Box for content
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(0));
        verticalBox.add(title);
        verticalBox.add(Box.createVerticalStrut(0));
        verticalBox.add(scroll);

        // Wrap in fixed-width panel and center
        JPanel fixedPanel = new JPanel();
        fixedPanel.setLayout(new BoxLayout(fixedPanel, BoxLayout.Y_AXIS));
        fixedPanel.setOpaque(false);
        fixedPanel.setMaximumSize(new Dimension(420, Integer.MAX_VALUE)); // ⬅️ Control max width
        fixedPanel.add(verticalBox);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(fixedPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        backButton();
    }

    // Show achievements (earned badges)
    void showAchievement() {
        mainPanel.removeAll();

        JLabel title = new JLabel("Achievements");
        title.setFont(new Font("Times New Roman", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(false);

        JTextArea areabadges = new JTextArea(new DataSystem().ReadFileBadge());
        areabadges.setEditable(false);
        areabadges.setOpaque(false); // Make background transparent
        areabadges.setBackground(new Color(0, 0, 0, 0)); // Fully transparent background
        areabadges.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        areabadges.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scroll = new JScrollPane(areabadges);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(450, 500));

        // Create vertical box layout
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(0));
        verticalBox.add(title);
        verticalBox.add(Box.createVerticalStrut(0));
        verticalBox.add(scroll);

        // Wrap in fixed-width panel and center
        JPanel fixedPanel = new JPanel();
        fixedPanel.setLayout(new BoxLayout(fixedPanel, BoxLayout.Y_AXIS));
        fixedPanel.setOpaque(false);
        fixedPanel.setMaximumSize(new Dimension(420, Integer.MAX_VALUE)); // ⬅️ Control max width
        fixedPanel.add(verticalBox);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(fixedPanel); 

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        backButton();
    }

    // Reusable styled home button for navigation
    void backButton() {
        try {
            ImageIcon homeIcon = new ImageIcon("home.png");
            Image scaledImg = homeIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

            JButton homeButton = new JButton(new ImageIcon(scaledImg));
            homeButton.setPreferredSize(new Dimension(50, 50));
            homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            homeButton.setToolTipText("Back to Homepage");

            // Initial transparent button
            homeButton.setContentAreaFilled(false);
            homeButton.setBorderPainted(false);
            homeButton.setFocusPainted(false);
            homeButton.setOpaque(false);
            homeButton.setRolloverEnabled(false);// <- disables hover highlight

            homeButton.addActionListener(e -> showHome());

            // Centered bottom panel
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
            wrapper.setOpaque(false);
            wrapper.add(homeButton);

            mainPanel.add(wrapper, BorderLayout.SOUTH);
            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception ex) {
            System.err.println("Home button image not found.");
        }
    }

    // Entry point
    public static void main(String[] args) {
        new JavaProject();
    }
    // Custom JPanel to handle background image painting
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            // Always call the superclass method first to ensure the panel is properly rendered
            super.paintComponent(g);
                if (backgroundImage != null) {
                    // Get the size of the panel (current width and height)
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();

                    // Get the original size of the background image
                    int imgWidth = backgroundImage.getWidth(this);
                    int imgHeight = backgroundImage.getHeight(this);

                    // Calculate aspect ratios for both panel and image
                    double panelAspect = (double) panelWidth / panelHeight;
                    double imgAspect = (double) imgWidth / imgHeight;

                    int drawWidth, drawHeight;

                    // If the panel is proportionally wider than the image
                    if (panelAspect > imgAspect) {
                        // Stretch width to panel width, adjust height to maintain image aspect ratio
                        drawWidth = panelWidth;
                        drawHeight = (int) (panelWidth / imgAspect);
                    } else {
                        // If the panel is proportionally taller than the image
                        // Stretch height to panel height, adjust width to maintain image aspect ratio
                        drawHeight = panelHeight;
                        drawWidth = (int) (panelHeight * imgAspect);
                    }
                    // Center the image by calculating the top-left position to start drawing
                    int x = (panelWidth - drawWidth) / 2;
                    int y = (panelHeight - drawHeight) / 2;

                    // Finally, draw the image on the panel with the calculated size and position
                    g.drawImage(backgroundImage, x, y, drawWidth, drawHeight, this);
                }
        }
    }
}

// Created by: Khoo Chee Guo (99406), content idea by all
import java.awt.*;
import javax.swing.*;

public class QuizModule extends DataSystem implements QuizInterface {
    DataSystem data;
    JavaProject javaproject;
    JFrame frame;
    JPanel panel;
    // Quiz state tracking
    private int current = 0;
    private int score = 0;
    private boolean partOne = false;
    private boolean tfSubmitted = false;
    private boolean mcqSubmitted = false;

    // Answers from user
    private String[] answerTF = new String[10];
    private String[] answerMC = new String[10];
    // Gamification engine
    Gamification gamify = new Gamification();

    // Constructor initializes the quiz module
    public QuizModule(JavaProject javaproject) {
        this.javaproject = javaproject ;
        frame = new JFrame("Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(3);
        frame.setSize(600, 800);
        startScreen();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center on pop up screen
        javaproject.frame.setVisible(false);// Hide main window and show quiz window only
    }
    // Main screen before quiz begins
    @Override
    public void startScreen() {
        frame.getContentPane().removeAll();
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JPanel centerContent = new JPanel();
        centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.Y_AXIS));
        centerContent.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        centerContent.setOpaque(false);

        JLabel label = new JLabel("Attempt the Quiz Now - Part 1");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Georgia", Font.BOLD, 18));

        JLabel label2 = new JLabel("10 questions");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setFont(new Font("Georgia", Font.BOLD, 15));

        JLabel label3 = new JLabel("You will not able to change the answer once you submit.");
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setFont(new Font("Georgia", Font.PLAIN, 15));
        label3.setForeground(new Color(80, 80, 80));

        JLabel label4 = new JLabel("Exit button will be freezed when you entered the Quiz Session.");
        label4.setAlignmentX(Component.CENTER_ALIGNMENT);
        label4.setFont(new Font("Georgia", Font.PLAIN, 15));
        label4.setForeground(new Color(80, 80, 80));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER,100,0));
        buttonRow.setOpaque(false);

        JButton start = new JButton("Start Quiz");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setFont(new Font("Times New Roman", Font.BOLD, 16));
        start.setBackground(new Color(178, 235, 166));
        start.setForeground(Color.BLACK);
        start.setFocusPainted(false);
        start.setMargin(new Insets(5, 20, 5, 20));
        start.setPreferredSize(new Dimension(120, 35));

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setFont(new Font("Times New Roman", Font.BOLD, 16));
        back.setBackground(new Color(178, 235, 166));
        back.setForeground(Color.BLACK);
        back.setFocusPainted(false);
        back.setMargin(new Insets(5, 20, 5, 20));
        back.setPreferredSize(new Dimension(120, 35));

        buttonRow.add(start);
        buttonRow.add(back);
        
        centerContent.add(Box.createVerticalStrut(80));
        centerContent.add(label3);
        centerContent.add(Box.createVerticalStrut(5));
        centerContent.add(label4);
        centerContent.add(Box.createVerticalStrut(40));
        centerContent.add(label);
        centerContent.add(Box.createVerticalStrut(5));
        centerContent.add(label2);
        centerContent.add(Box.createVerticalStrut(60));
        centerContent.add(buttonRow);
        
        topPanel.add(Box.createVerticalGlue()); // Add space at the top
        topPanel.add(centerContent);
        topPanel.add(Box.createVerticalGlue()); // Add space at the bottom
        

        panel.add(topPanel, BorderLayout.NORTH);
        
        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);
        try {
            ImageIcon icon = new ImageIcon("bgQues.png");
            Image img = icon.getImage().getScaledInstance(600,600, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(img));
            logoPanel.add(logoLabel);
        } catch (Exception e) {
            System.out.println("Logo image not found!");
            e.printStackTrace();
        }

        // Button actions
        start.addActionListener(e -> {
            // Cannot exit, for the exit button 
            frame.setDefaultCloseOperation(0);
            gamify.startTimer();
            partOne = true;
            current = 0;
            TrueFalseQuestion();
        });

        back.addActionListener(e -> {
            frame.dispose(); // close quiz window
            javaproject.showHome();
        });

        panel.add(logoPanel, BorderLayout.SOUTH);

        panel.revalidate();
        panel.repaint();
    }

    // Quiz UI after end of Part 1 
    private void guideUI() {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(Color.WHITE);

            JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            labelPanel.setBackground(Color.WHITE);

            JLabel label = new JLabel("You've finished Part 1. Please review your answers before proceeding.");
            label.setFont(new Font("Times New Roman", Font.BOLD, 16));
            labelPanel.add(label);

            JButton review = new JButton("Review Answers");
            review.setFont(new Font("Times New Roman", Font.BOLD,16));
            review.setBackground(new Color(178, 235, 166));
            review.setAlignmentX(Component.CENTER_ALIGNMENT);
            review.addActionListener(e -> {
                tfSubmitted = true;
                reviewTrueFalseAnswers();
            });
            centerPanel.add(Box.createVerticalStrut(40));
            centerPanel.add(labelPanel);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(review);

            panel.add(centerPanel, BorderLayout.CENTER);

            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            logoPanel.setBackground(Color.WHITE);
            try {
                ImageIcon logoicon = new ImageIcon("bgQues.png");
                Image logoimg = logoicon.getImage().getScaledInstance(600,600, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(logoimg));
                logoPanel.add(logoLabel); // 🌿 Add logo to bottom-left
            } catch (Exception e) {
                System.out.println("Logo image not found!");
                e.printStackTrace();    
            }
            
            panel.add(logoPanel, BorderLayout.SOUTH);

            panel.revalidate();
            panel.repaint();
        }

        // List True/False answers for review
        private void reviewTrueFalseAnswers() {
            panel.removeAll();
            panel.setLayout(new BorderLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(Color.WHITE);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            JLabel review = new JLabel("Review your True/False answers:");
            review.setFont(new Font("Times New Roman", Font.BOLD, 16));
            review.setAlignmentX(Component.LEFT_ALIGNMENT);
            centerPanel.add(review);
            centerPanel.add(Box.createVerticalStrut(20));
            for (int i = 0; i < trueFalseQuestions.length; i++) {
                JTextArea row = new JTextArea((i + 1) + ". " + trueFalseQuestions[i] + " → " + (answerTF[i] == null ? "No Answer" : answerTF[i]));
                row.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                row.setLineWrap(true);
                row.setWrapStyleWord(true);
                row.setEditable(false);
                row.setOpaque(false);
                row.setFocusable(false);
                row.setBorder(null);
                row.setPreferredSize(new Dimension(500, 37));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);
                centerPanel.add(row);
                centerPanel.add(Box.createVerticalStrut(5));
            }
            
            JButton proceed = new JButton("Proceed to Part 2");
            proceed.setFont(new Font("Times New Roman", Font.BOLD, 16));
            proceed.setBackground(new Color(178, 235, 166));
            proceed.addActionListener(e -> {
                partOne = false;
                current = 0;
                MultipleChoiceQuestion();
            });
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(proceed);

            JPanel LWrapper= new JPanel();
            LWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
            LWrapper.setBackground(Color.WHITE);
            LWrapper.add(centerPanel);

            JPanel wrapper= new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setBackground(Color.WHITE);
            wrapper.add(LWrapper);
            wrapper.add(Box.createVerticalStrut(10));
            wrapper.add(buttonPanel);

            panel.add(wrapper, BorderLayout.CENTER);

            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            logoPanel.setBackground(Color.WHITE);
            try {
                ImageIcon icon = new ImageIcon("bgQues.png");
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(img));
                logoPanel.add(logoLabel);
                panel.add(logoPanel, BorderLayout.SOUTH);
            } catch (Exception e) {
                System.out.println("Logo image not found!");
                e.printStackTrace();
            }
            panel.revalidate();
            panel.repaint();
        }
        // List MCQ answers for review
        private void reviewMCQAnswers() {
            panel.removeAll();
            panel.setLayout(new BorderLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(Color.WHITE);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            JLabel review = new JLabel("Review your MCQ answers:");
            review.setFont(new Font("Times New Roman", Font.BOLD, 16));
            review.setAlignmentX(Component.LEFT_ALIGNMENT);
            centerPanel.add(review);
            centerPanel.add(Box.createVerticalStrut(10));
            for (int i = 0; i < mcqQuestions.length; i++) {
                JTextArea row = new JTextArea((i + 1) + ". " + mcqQuestions[i] + " → " + (answerMC[i] == null ? "No Answer" : answerMC[i]));
                row.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                row.setLineWrap(true);
                row.setWrapStyleWord(true);
                row.setEditable(false);
                row.setOpaque(false);
                row.setFocusable(false);
                row.setBorder(null);
                row.setPreferredSize(new Dimension(500, 37));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);
                centerPanel.add(row);
                centerPanel.add(Box.createVerticalStrut(5));
            }

            JButton submit = new JButton("Submit and View Score");
            submit.setFont(new Font("Times New Roman", Font.BOLD, 16));
            submit.setBackground(new Color(178, 235, 166));
            submit.addActionListener(e -> {
                mcqSubmitted = true;
                gamify.stopTimer();
                calculateScore();
                gamify.saveResult(score);
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(submit);
            
            JPanel LWrapper= new JPanel();
            LWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
            LWrapper.setBackground(Color.WHITE);
            LWrapper.add(centerPanel);

            JPanel wrapper= new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setBackground(Color.WHITE);
            wrapper.add(LWrapper);
            wrapper.add(Box.createVerticalStrut(10));
            wrapper.add(buttonPanel);

            panel.add(wrapper, BorderLayout.CENTER);


            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            logoPanel.setBackground(Color.WHITE);
            try {
                ImageIcon icon = new ImageIcon("bgQues.png");
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(img));
                logoPanel.add(logoLabel);
                panel.add(logoPanel, BorderLayout.SOUTH);
            } catch (Exception e) {
                System.out.println("Logo image not found!");
                e.printStackTrace();
            }
            
            panel.revalidate();
            panel.repaint();
        }

        // UI for True/False questions
        private void TrueFalseQuestion() {
            panel.removeAll();
            panel.setLayout(new BorderLayout());

            // Top panel holds the question and options
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBackground(Color.WHITE);

            // Display the question
            JTextArea questionTF = new JTextArea((current + 1) + ". " + trueFalseQuestions[current]);
            questionTF.setFont(new Font("Times New Roman", Font.BOLD, 15));
            questionTF.setLineWrap(true);
            questionTF.setWrapStyleWord(true);
            questionTF.setEditable(false);
            questionTF.setOpaque(false);
            questionTF.setFocusable(false);
            questionTF.setBorder(null);
            questionTF.setPreferredSize(new Dimension(500, 37));
            // Wrap the question in a left-aligned panel
            JPanel questionWrapper=new JPanel(new FlowLayout(FlowLayout.LEFT));
            questionWrapper.setBackground(Color.WHITE);
            questionWrapper.add(questionTF);
            topPanel.add(Box.createVerticalStrut(10));
            topPanel.add(questionWrapper);

            // Warning for last question
            if (current == trueFalseQuestions.length - 1) {
                JLabel warn = new JLabel("⚠️ This is the last question. You won't be able to change your answers after this.");
                warn.setFont(new Font("Times New Roman", Font.ITALIC, 14));
                warn.setAlignmentX(Component.CENTER_ALIGNMENT);
                topPanel.add(Box.createVerticalStrut(10));
                topPanel.add(warn);
            }
            // Create answer options
            JPanel tfPanel = new JPanel();
            tfPanel.setLayout(new BoxLayout(tfPanel, BoxLayout.Y_AXIS));
            tfPanel.setBackground(Color.WHITE);

            JRadioButton trueButton = new JRadioButton("True");
            trueButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            trueButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            trueButton.setBackground(Color.WHITE);

            JRadioButton falseButton = new JRadioButton("False");
            falseButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            falseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            falseButton.setBackground(Color.WHITE);

            // Group the radio buttons
            ButtonGroup group = new ButtonGroup();
            group.add(trueButton);
            group.add(falseButton);

            // Restore previous answer if already selected
            if ("True".equals(answerTF[current])) trueButton.setSelected(true);
            else if ("False".equals(answerTF[current])) falseButton.setSelected(true);

            // Disable options after submission
            trueButton.setEnabled(!tfSubmitted);
            falseButton.setEnabled(!tfSubmitted);

            // Save selected answer
            trueButton.addActionListener(e -> answerTF[current] = "True");
            falseButton.addActionListener(e -> answerTF[current] = "False");

            tfPanel.add(trueButton);
            tfPanel.add(falseButton);

            // Wrap option panel and add it to the top panel
            JPanel wrapper=new JPanel(new FlowLayout(FlowLayout.LEFT));
            wrapper.setBackground(Color.WHITE);
            wrapper.add(tfPanel);

            topPanel.add(Box.createVerticalStrut(10));
            topPanel.add(wrapper);

            ImageIcon nextIcon = new ImageIcon("Nextbutton.png");
            Image scaledImg = nextIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledNextIcon = new ImageIcon(scaledImg);

            // Navigation buttons for Next or Finish
            JButton next;
            if (current == trueFalseQuestions.length - 1) {
                next = new JButton("Finish Part 1");
                next.setFont(new Font("Times New Roman", Font.BOLD, 16));
                next.setBackground(new Color(178, 235, 166));
            } else {
                next= new JButton(scaledNextIcon);
                next.setBorderPainted(false);
                next.setContentAreaFilled(false);
                next.setFocusPainted(false);
                next.setOpaque(false);
            }

            next.setHorizontalTextPosition(SwingConstants.RIGHT);
            next.setIconTextGap(10);

            // Navigation buttons for Previous
            ImageIcon backIcon = new ImageIcon("back.png");
            Image scaledBackImg = backIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledBackIcon = new ImageIcon(scaledBackImg);
            JButton previous = new JButton(scaledBackIcon);
            previous.setBorderPainted(false);
            previous.setContentAreaFilled(false);   
            previous.setFocusPainted(false);
            previous.setOpaque(false);

            JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            navPanel.setBackground(Color.WHITE);

            // Add navigation logic
            next.addActionListener(e -> {
                if (answerTF[current] == null) {
                    JOptionPane.showMessageDialog(frame, "Please select an answer before proceeding.");
                    return;
                }
                if (current < trueFalseQuestions.length - 1) {
                    current++;
                    TrueFalseQuestion();
                } else {
                    guideUI();
                }
            });

            previous.addActionListener(e -> {
                if (current > 0) {
                    current--;
                    TrueFalseQuestion();
                }
            });

            
            if (current > 0) navPanel.add(previous);
            navPanel.add(next);
            topPanel.add(Box.createVerticalStrut(20));
            topPanel.add(navPanel);
            panel.add(topPanel, BorderLayout.NORTH);

            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            logoPanel.setBackground(Color.WHITE);
            try {
                ImageIcon icon = new ImageIcon("bgQues.png");
                Image img = icon.getImage().getScaledInstance(600,600, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(img));
                logoPanel.add(logoLabel); // 🌿 Add logo to bottom-left
            } catch (Exception e) {
                System.out.println("Logo image not found!");
                e.printStackTrace();    
            }
            
            panel.add(logoPanel, BorderLayout.SOUTH);

            panel.revalidate();
            panel.repaint();
        }

        // UI for MCQ questions
        private void MultipleChoiceQuestion() {
            panel.removeAll();
            panel.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBackground(Color.WHITE);

            JTextArea questionLabel = new JTextArea((current + 1) + ". " + mcqQuestions[current]);
            questionLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
            questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            questionLabel.setLineWrap(true);
            questionLabel.setWrapStyleWord(true);
            questionLabel.setEditable(false);
            questionLabel.setOpaque(false);
            questionLabel.setFocusable(false);
            questionLabel.setBorder(null);
            questionLabel.setPreferredSize(new Dimension(500, 37));

            JPanel multipleWrapper=new JPanel(new FlowLayout(FlowLayout.LEFT));
            multipleWrapper.setBackground(Color.WHITE);
            multipleWrapper.add(questionLabel);
            topPanel.add(Box.createVerticalStrut(10));
            topPanel.add(multipleWrapper);


            if (current == mcqQuestions.length - 1) {
                JLabel warn = new JLabel("⚠️ This is the last question. You won't be able to change your answers after this.");
                warn.setFont(new Font("Times New Roman", Font.ITALIC, 14));
                warn.setAlignmentX(Component.CENTER_ALIGNMENT);
                topPanel.add(Box.createVerticalStrut(10));
                topPanel.add(warn);
            }

            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
            optionsPanel.setBackground(Color.WHITE);

            JRadioButton optionA = new JRadioButton(mcqOptions[current][0]);
            optionA.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            optionA.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionA.setBackground(Color.WHITE);

            JRadioButton optionB = new JRadioButton(mcqOptions[current][1]);
            optionB.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            optionB.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionB.setBackground(Color.WHITE);

            JRadioButton optionC = new JRadioButton(mcqOptions[current][2]);
            optionC.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            optionC.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionC.setBackground(Color.WHITE);

            JRadioButton optionD = new JRadioButton(mcqOptions[current][3]);
            optionD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            optionD.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionD.setBackground(Color.WHITE);

            ButtonGroup group = new ButtonGroup();
            group.add(optionA);
            group.add(optionB);
            group.add(optionC);
            group.add(optionD);

            if ("A".equals(answerMC[current])) optionA.setSelected(true);
            else if ("B".equals(answerMC[current])) optionB.setSelected(true);
            else if ("C".equals(answerMC[current])) optionC.setSelected(true);
            else if ("D".equals(answerMC[current])) optionD.setSelected(true);

            optionA.setEnabled(!mcqSubmitted);
            optionB.setEnabled(!mcqSubmitted);
            optionC.setEnabled(!mcqSubmitted);
            optionD.setEnabled(!mcqSubmitted);

            optionA.addActionListener(e -> answerMC[current] = "A");
            optionB.addActionListener(e -> answerMC[current] = "B");
            optionC.addActionListener(e -> answerMC[current] = "C");
            optionD.addActionListener(e -> answerMC[current] = "D");

            optionsPanel.add(optionA);
            optionsPanel.add(optionB);  
            optionsPanel.add(optionC);
            optionsPanel.add(optionD);

            JPanel optionsWrapper=new JPanel(new FlowLayout(FlowLayout.LEFT));
            optionsWrapper.setBackground(Color.WHITE);
            optionsWrapper.add(optionsPanel);
            topPanel.add(Box.createVerticalStrut(10));
            topPanel.add(optionsWrapper);
            
            ImageIcon nextIcon = new ImageIcon("Nextbutton.png");
            Image scaledImg = nextIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledNextIcon = new ImageIcon(scaledImg);
            JButton next;
            if(current == mcqQuestions.length - 1) {
                next = new JButton("Finish Part 2");
                next.setFont(new Font("Times New Roman", Font.BOLD, 16));
                next.setBackground(new Color(178, 235, 166));
            } else {
                next = new JButton(scaledNextIcon);
                next.setBorderPainted(false);
                next.setContentAreaFilled(false);
                next.setFocusPainted(false);
                next.setOpaque(false);
            } 

            ImageIcon backIcon = new ImageIcon("back.png");
            Image scaledBackImg = backIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledBackIcon = new ImageIcon(scaledBackImg);
            JButton previous = new JButton(scaledBackIcon);
            previous.setBorderPainted(false);
            previous.setContentAreaFilled(false);   
            previous.setFocusPainted(false);
            previous.setOpaque(false);

            next.addActionListener(e -> {
                if (answerMC[current] == null) {
                    JOptionPane.showMessageDialog(frame, "Please select an answer before proceeding.");
                    return;
                }
                if (current < mcqQuestions.length - 1) {
                    current++;
                    MultipleChoiceQuestion();
                } else {
                    reviewMCQAnswers();
                }
            });

            previous.addActionListener(e -> {
                if (current > 0) {
                    current--;
                    MultipleChoiceQuestion();
                }
            });

            JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            navPanel.setBackground(Color.WHITE);
            if (current>0) {
                navPanel.add(previous);
            }
            navPanel.add(next);
            
            topPanel.add(Box.createVerticalStrut(20));
            topPanel.add(navPanel);

            panel.add(topPanel, BorderLayout.NORTH);

            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            logoPanel.setBackground(Color.WHITE);
            try {
                ImageIcon icon = new ImageIcon("bgQues.png");
                Image img = icon.getImage().getScaledInstance(600,600, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(img));
                logoPanel.add(logoLabel); 
            } catch (Exception e) {
                System.out.println("Logo image not found!");
                e.printStackTrace();
        }

            panel.add(logoPanel, BorderLayout.SOUTH);

            panel.revalidate();
            panel.repaint();
        }

        private void calculateScore() {
        score = 0;
        for (int i = 0; i < trueFalseQuestions.length; i++) {
            if ("True".equals(answerTF[i]) && (i == 1 || i == 6 || i == 8 || i == 9)) score++;
            if ("False".equals(answerTF[i]) && (i == 0 || i == 2 || i == 3 || i == 4 || i == 5 || i == 7)) score++;
        }
        for (int i = 0; i < mcqCorrectAnswers.length; i++) {
            if (mcqCorrectAnswers[i].equals(answerMC[i])) score++;
        }

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel result = new JLabel("Quiz Completed! Your total score is: " + score + " / 20");
        result.setFont(new Font("Times New Roman", Font.BOLD, 16));
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setForeground(new Color(34, 139, 34)); // Forest Green color
        centerPanel.add(result);

        ImageIcon homeIcon = new ImageIcon("home.png");
        Image scaledHomeImg = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledHomeIcon = new ImageIcon(scaledHomeImg);
        JButton mainmenu = new JButton(scaledHomeIcon);
        mainmenu.setBorderPainted(false);
        mainmenu.setContentAreaFilled(false);
        mainmenu.setFocusPainted(false);
        mainmenu.setOpaque(false);
        mainmenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainmenu.addActionListener(e -> {
        frame.setDefaultCloseOperation(3);
        javaproject.frame.setVisible(true);  // bring the main menu back
        javaproject.showHome();              // refresh the home screen
        frame.dispose();                     // close the quiz window
    });

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(Box.createVerticalStrut(50));
        wrapper.add(centerPanel);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(mainmenu);

        panel.add(wrapper, BorderLayout.CENTER);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);
            try {
                ImageIcon icon = new ImageIcon("bgQues.png");
                Image img = icon.getImage().getScaledInstance(600,600, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(img));
                logoPanel.add(logoLabel); 
            } catch (Exception e) {
                System.out.println("Logo image not found!");
                e.printStackTrace();
        }

        panel.add(logoPanel, BorderLayout.SOUTH);

        panel.revalidate();
        panel.repaint();
    }


        String[] trueFalseQuestions = {
            "Climate change is caused solely by human activities?",
            "Deforestation contributes to the loss of biodiversity?",
            "Plastic pollution mainly affects terrestrial ecosystems?",
            "Air pollution only affects human health, not the environment?",
            "Water pollution has no significant effect on aquatic ecosystems?",
            "Biodiversity is only important for food production and has no other uses?",
            "Renewable energy sources, such as solar and wind, contribute to the reduction of greenhouse gases?",
            "Overfishing reduces the availability of fish species but has no other environmental consequences?",
            "Climate change can lead to species extinction by altering their natural habitats?",
            "The 3Rs rule (Reduce, Reuse, Recycle) helps in reducing waste and conserving resources?"
        };

        String[] mcqQuestions = {
            "Which of the following is the main cause of deforestation?",
            "What is the main benefit of using renewable energy sources?",
            "What type of pollution primarily harms marine life and ecosystems?",
            "Which of the following is an example of an eco-friendly habit?",
            "What is biodiversity?",
            "Which of the following is NOT a major source of air pollution?",
            "Which of the following is a renewable energy source?",
            "Which action helps reduce water consumption?",
            "What is the primary effect of plastic pollution on marine life?",
            "Which of the following activities contributes most to reducing your environmental footprint?"
        };

        String[][] mcqOptions = {
            {"A. Agriculture", "B. Urbanization", "C. Mining", "D. Tourism"},
            {"A. Increases electricity bills", "B. Reduces greenhouse gases", "C. Uses fossil fuels", "D. Pollutes the air"},
            {"A. Noise pollution", "B. Air pollution", "C. Light pollution", "D. Plastic pollution"},
            {"A. Using plastic bags", "B. Leaving lights on", "C. Recycling waste", "D. Driving daily"},
            {"A. A type of forest", "B. Variety of living organisms", "C. Pollution in water", "D. Energy source"},
            {"A. Vehicle emissions", "B. Factories", "C. Trees", "D. Power plants"},
            {"A. Coal", "B. Oil", "C. Wind", "D. Natural gas"},
            {"A. Running tap while brushing", "B. Fixing leaks", "C. Washing car daily", "D. Long showers"},
            {"A. Enhances ocean beauty", "B. Provides shelter", "C. Kills marine animals", "D. Increases food supply"},
            {"A. Eating meat daily", "B. Driving everywhere", "C. Using reusable bags", "D. Ignoring recycling"}
        };

        String[] mcqCorrectAnswers = { "A", "B", "D", "C", "B","C", "C", "B", "C", "C" };
    }

    interface QuizInterface {
        void startScreen();
    }
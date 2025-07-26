
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

// Custom panel to paint background image, with optional 90-degree rotation when maximized
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            boolean isMaximized = topFrame != null &&
                (topFrame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = backgroundImage.getWidth(this);
            int imgHeight = backgroundImage.getHeight(this);

            Graphics2D g2d = (Graphics2D) g.create();

            if (isMaximized) {
                // Rotate 90 degrees and stretch to fill the window
                g2d.translate(panelWidth / 2, panelHeight / 2);
                g2d.rotate(Math.toRadians(90));
                double scaleX = (double) panelHeight / imgWidth;
                double scaleY = (double) panelWidth / imgHeight;
                g2d.scale(scaleX, scaleY);
                g2d.drawImage(backgroundImage, -imgWidth / 2, -imgHeight / 2, this);
            } else {
                // Normal vertical, stretch to fill the window 
                g2d.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);
            }
            g2d.dispose();
        }
    }
}
public class ReadingModule extends DataSystem implements ReadingModuleInterface {

    JPanel panel;
    private JavaProject app;

    public ReadingModule(JavaProject app) {
        this.app = app;
        Image bgImg = null;
        try {
            bgImg = ImageIO.read(new File("bgModule.jpeg"));
        } catch (Exception e) {
            System.out.println("Background image not found, using default.");
        }
        // Use BackgroundPanel if image exists; otherwise use plain JPanel
        panel = (bgImg != null) ? new BackgroundPanel(bgImg) : new JPanel(new BorderLayout());
        panel.setOpaque(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    // Main page with list of learning units
    public void dashBoard() {
        panel.removeAll();

        // Main Title
        JLabel title = new JLabel("ENVIRONMENTAL AWARENESS");
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        // Section Heading
        JLabel heading = new JLabel("Learning Units:");
        heading.setFont(new Font("Times New Roman", Font.BOLD, 25));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Learning Units
        String[] topics = {
            "Introduction to Environmental Awareness",
            "Climate Change",
            "Deforestation",
            "Plastic Pollution",
            "Air Pollution",
            "Water Pollution",
            "Biodiversity Loss",
            "Breaking the Fossil Habit",
            "Smart Living: Reduce, Reuse, Recycle",
            "Be Earth's Hero from Small Acts",
            "One Earth, One Chance"
        };

        // Content for each topic
        String[] contents = {
            // 0
            "<b>Definition of Environmental Awareness</b><br>Environmental awareness is the understanding of human impact on the environment, the impacts of human behaviors on it, and the importance of its protection.<br>" +
            "<br><b>What is Environmental Awareness?</b><br>" +
            "• Ideology that evokes the necessity and responsibility of humans to respect, protect, and preserve the natural world from its anthropogenic (caused by humans) afflictions.<br>" +
            "• Integral part of the movement's success.<br>" +
            "• By spreading awareness to others that the physical environment is fragile and indispensable, we can begin fixing the issues that threaten it.<br>" +
            "<br><b>Importance of understanding human impact on nature.</b><br>" +
            "• Pollution, burning fossil fuels, deforestation, and more.<br>" +
            "• Triggered climate change, mass extinction, plastic pollution, air pollution and water pollution.<br>" +
            "<b>Statistics:</b> Over 1 million species are at risk due to human activities.",
            // 1
            "<b>Causes</b><br>• Generating power, use of transportation and manufacturing goods by burning coal, oil and gas which also produces carbon dioxide and nitrous oxide which are powerful greenhouse gases.<br>" +
            "• Manufacturing industry is one of the largest contributors to greenhouse gases.<br>" +
            "• Deforestation, limit nature's ability to keep emissions out of the atmosphere. Around 12 million hectares of forest are destroyed.<br>" +
            "<br><b>Effects</b><br>• 75% of global greenhouse gas emissions and nearly 90% all carbon dioxide emissions trap the Sun's heat.<br>" +
            "• Warming speed increases and affects global surface temperature, currently faster than at any point in recorded history.<br>" +
            "• Warmer temperatures over time are changing weather patterns and disrupting the usual balance of nature.<br>" +
            "• Risks to human beings and all other forms of life on Earth due to insufficient food sources.<br>" +
            "• Lost of species at a rate 1000 times greater than in human history.",
            // 2
            "<b>Causes</b><br>• Expansion of agricultural land, including palm oil plantations and cattle ranching.<br>" +
            "• Deforestation, limit nature's ability to keep emissions out of the atmosphere. Around 12 million hectares of forest are destroyed.<br>" +
            "• Infrastructure development such as roads, urban expansion and mining.<br>" +
            "<br><b>Effects</b><br>• Loss of animal and plant species due to their loss of habitat.<br>" +
            "• Greenhouse gases released into the atmosphere. Trees absorb emissions from the air, acting as a great storage system - or \"carbon sink\" - for the greenhouse gases and could provide more than one-third of the total Co2 reductions required to keep global warming below 2°C through to 2030.<br>" +
            "• Soil erosion and coastal flooding. Trees help the land to retain water and topsoil, which provides the rich nutrients to sustain additional forest life.<br>" +
            "• Drying and forest fires as seen in Brazil, California, Indonesia, Australia and the Congo Basin. Forests act as nature's air conditioning unit, transpiring water, which forms clouds that lowers global temperature.",
            // 3
            "<b>Causes</b><br>• Plastic is ease of shaping, low cost, mechanical resistance, etc. More than 430 million tons of plastic are produced each year.<br>" +
            "• Household waste, which is poorly recycled, dumped in landfills or abandoned in nature and carried by the winds, pushed by the rains into sewers, streams, rivers, and finally in the oceans.<br>" +
            "<br><b>Effects</b><br>• Plastic debris contain compounds that can be chemically transferred to organisms during ingestion, which are toxic and can accumulate in the body.<br>" +
            "• Affect the growth of crops, by hindering the process of photosynthesis in agricultural fields.<br>" +
            "• Imprisonment of animals in nets or large debris. Death of marine mammals, turtles and birds due to ingestion, that concerns the entire food chain of the marine ecosystem.",
            // 4
            "<b>Causes</b><br>• Ozone high up in our atmosphere helps block radiation from the Sun, Ozone is closer to the ground, can be really bad for our health.<br>" +
            "• Ground level ozone is created when sunlight reacts with certain chemicals that come from sources of burning fossil fuels, such as factories or car exhaust.<br>" +
            "<br><b>Effects</b><br>• Respiratory disorders, heart diseases, lung cancers. Children living near polluted areas are more prone to pneumonia and asthma.<br>" +
            "• Emission of greenhouse gases leads to global warming, melting of glaciers and increase in sea levels.<br>" +
            "• Acid rain which damages human, animal and plant life.<br>" +
            "• Depleting of good ozone layer could not block harmful ultraviolet rays coming from the sun and causes skin diseases and eye problems.",
            // 5
            "<b>Causes</b><br>• Inadequate sewage collection and treatment, more than 80% of the worldwide wastewater goes back in the environment without being treated or reused.<br>" +
            "• Urbanization and deforestation generates an acceleration of flows which does not give enough time for water to infiltrate and be purified by the ground.<br>" +
            "• Agriculture use of chemicals such as fertilizers, pesticides, fungicides, herbicides or insecticides running off in the water, as well as livestock excrement, manure and methane (greenhouse effect).<br>" +
            "• Industries waste containing toxic chemicals and pollutants are then flows into canals, rivers and eventually in the sea.<br>" +
            "• Garbage such as plastic, paper, aluminum, food, glass, or rubber are deposited into the sea and take to hundreds of years to decompose.<br>" +
            "<br><b>Effects</b><br>• Harms biodiversity and aquatic ecosystems. The toxic chemicals change the color of water and increase the amount of minerals - also known as eutrophication - which has a bad impact on life in water.<br>" +
            "• Thermal pollution, defined by a rise in the temperature of water bodies, contributes to global warming and causes serious hazard to water organisms.<br>" +
            "• Diseases due to drinking or being in contact with contaminated water, such as diarrhea, cholera, typhoid, dysentery or skin infections in zones where there is no available drinking water, the main risk is dehydration.",
            // 6
            "<b>What is Biodiversity?</b><br>• A variety of all life form on earth.<br>" +
            "• Includes animals, plants, fungi and microorganisms.<br>" +
            "• Found in all environments - land, water, air.<br>" +
            "<b>3 main types:</b><br>" +
            "• Genetic diversity - variety within a species<br>" +
            "• Species diversity - number of different species<br>" +
            "• Ecosystem diversity - different ecosystems and habitats<br>" +
            "<b>Biodiversity is important for food, clean water, medicine, and climate balance.</b><br>" +
            "<br><b>Human Activities Reducing Species Diversity</b><br>• Deforestation - destroys habitats.<br>" +
            "• Pollution - poisons land, air, and water.<br>" +
            "• Overfishing & Hunting - reduces animal populations.<br>" +
            "• Urbanization - replaces nature with buildings and roads.<br>" +
            "• Climate Change - causes species to migrate or become extinct.",
            // 7
            "<b>What are Renewable Resources?</b><br>• Natural resources that naturally replenished and are sustainable.<br>" +
            "<b>Common types:</b><br>" +
            "• Solar energy - from sunlight<br>" +
            "• Wind energy - from moving air<br>" +
            "• Hydropower - from flowing water<br>" +
            "• Biomass - from organic material<br>" +
            "• Geothermal - from Earth's heat<br>" +
            "<br><b>Positive Impacts of Renewable Sources</b><br>• Clean and does not pollute the air.<br>" +
            "• Helps fight climate change.<br>" +
            "• Reduces dependence on fossil fuels.<br>" +
            "• Better for long-term energy security.",
            // 8
            "<b>The 3R Rule</b><br>• Reduce - Use less to create less waste<br>" +
            "• Reuse - Use items again instead of throwing them away<br>" +
            "• Recycle - Turn waste into new items<br>" +
            "<br><b>Easy Eco-Friendly Habits</b><br>• Avoid using plastic straws and utensils<br>" +
            "• Donate clothes instead of throwing them away<br>" +
            "• Sort your trash: paper, plastic, and food waste<br>" +
            "• Buy products made from recycled materials",
            // 9
            "<b>Smart Choices You Can Make Today</b><br>• Turn off lights when not in use<br>" +
            "• Walk, bike, or use public transport<br>" +
            "• Save water - shorter showers, fix leaks <br>" +
            "• Use reusable bags, bottles, and containers <br>" +
            "• Support local and eco-friendly products <br>" +
            "• Plant trees or join clean-up events<br>" +
            "<br><b>Why earth need you?</b><br>• Influence others - Your habits inspire family, friends and community.<br>" +
            "• You can reduce harm - By wasting less and choosing green options.<br>" +
            "• Your footprint matters - Less waste, less carbon, more life.<br>" +
            "• The planet is in crisis - Climate change, pollution, and biodiversity loss need urgent action.<br>" +
            "• Solutions start with people - Government and tech help, but individuals create real change.<br>" +
            "• The future depends on us - What we do today shapes tomorrow's world.",
            // 10
            "This planet is not someone else's responsibility — it's ours.<br>" +
            "Every choice you make, no matter how small, shapes the world we live in.<br>" +
            "From what we eat to how we travel; from the products we buy to how we dispose the waste — your actions matter.<br>" +
            "<br><b>But here's the good news:</b><br><br>" +
            "<center>You have the power to make a difference.<br>" +
            "A cleaner, greener Earth starts with simple daily choices.<br>" +
            "It starts with you.<br>" +
            "Be the reason the Earth heals, not the reason it hurts.</center>"
        };

        // Images to show for each topic
        ImageIcon[] icons = {
            null,
            new ImageIcon(new ImageIcon("climateChange.png").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("deforestation.jpg").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("plasticPollution.png").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("airPollution.jpeg").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("waterPollution.png").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("biodiversityLoss.png").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("fossilFuel.png").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("3Rs.png").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH)),
            null,
            new ImageIcon(new ImageIcon("goGreen.jpg").getImage().getScaledInstance(430, 250, Image.SCALE_SMOOTH))
        };

        JLabel label = new JLabel();
        JLabel image = new JLabel();

        // Create styled topic buttons
        JButton[] buttons = new JButton[topics.length];
        for (int i = 0; i < topics.length; i++) {
            buttons[i] = new JButton(topics[i]);
            buttons[i].setFont(new Font("Times New Roman", Font.BOLD, 17));
            buttons[i].setPreferredSize(new Dimension(380, 30));
            buttons[i].setBackground(new Color(178, 235, 166));
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(true);
            buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            // Add hover cursor effect
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // Add action listener to each button to show content
        for (int i = 0; i < topics.length; i++) {
            final int index = i;
            buttons[i].addActionListener(e -> {
                image.setIcon(icons[index]);
                image.setVisible(icons[index] != null);
                showContent(topics[index], contents[index], label, image);
            });
        }

        // Home button
        ImageIcon homeIcon = new ImageIcon("home.png");
        Image scaledImg = homeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton homeButton = new JButton(new ImageIcon(scaledImg));
        homeButton.setPreferredSize(new Dimension(40, 40));
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setToolTipText("Back to Homepage");
        homeButton.addActionListener(e -> app.showHome());
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.add(homeButton);

        // Build vertical content layout
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(60));
        verticalBox.add(title);
        verticalBox.add(heading);
        for (JButton button : buttons) {
            verticalBox.add(button);
            verticalBox.add(Box.createVerticalStrut(10));
        }
        verticalBox.add(Box.createVerticalStrut(20));
        verticalBox.add(navPanel);

        // Centering + scrollable panel setup
        JPanel fixedWidthPanel = new JPanel();
        fixedWidthPanel.setLayout(new BoxLayout(fixedWidthPanel, BoxLayout.Y_AXIS));
        fixedWidthPanel.setOpaque(false);
        fixedWidthPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        fixedWidthPanel.add(verticalBox);

        // Center the content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(fixedWidthPanel);

        // ScrollPane for vertical scrolling
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    // Displays full content with optional image based on selected topic
    private void showContent(String titletext, String text, JLabel label, JLabel image) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("<html><b>" + titletext.replaceAll("\n", "<br>") + "</b></html>");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Determine image file based on learning units
        String imagePath = switch (titletext) {
            case "Climate Change" -> "climateChange.png";
            case "Deforestation" -> "deforestation.jpg";
            case "Plastic Pollution" -> "plasticPollution.jpg";
            case "Air Pollution" -> "airPollution.jpeg";
            case "Water Pollution" -> "waterPollution.png";
            case "Biodiversity Loss" -> "biodiversityLoss.png";
            case "Breaking the Fossil Habit" -> "fossilFuel.png";
            case "Smart Living: Reduce, Reuse, Recycle" -> "3Rs.png";
            case "One Earth, One Chance" -> "goGreen.jpg";
            default -> "";
        };

        // Check if image file exists
        String htmlImage = "";
        if (!imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                htmlImage = "<br><br><center><img src='" + file.toURI() + "'width='450' height='250'></center>";
            }
        }

        // Combine text and image into HTML content
        String html = "<html><body style='font-family: Times New Roman; font-size: 18pt; line-height: 1.6; width: 450px;padding-left: 15px; padding-right: 15px;'>" +
                    text + htmlImage +
                    "</body></html>";

        // Create text pane with combined content
        JTextPane contentPane = new JTextPane();
        contentPane.setContentType("text/html");
        contentPane.setText(html);
        contentPane.setEditable(false);
        contentPane.setOpaque(false);
        contentPane.setBorder(null);
        contentPane.setCaretPosition(0);

        // Determine dynamic width
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int dynamicWidth = 500;
        Window window = SwingUtilities.getWindowAncestor(panel);
        if (window instanceof JFrame frameWindow) {
            boolean isMaximized = (frameWindow.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
            if (isMaximized) {
                dynamicWidth = screenWidth - 100; // Stretch more in fullscreen
            }
        }
        // Scroll pane for content
        JScrollPane contentScrollPane = new JScrollPane(contentPane);
        contentScrollPane.setOpaque(false);
        contentScrollPane.getViewport().setOpaque(false);
        contentScrollPane.setBorder(null);
        contentScrollPane.setMaximumSize(new Dimension(dynamicWidth, 400));
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Back button
        JButton backButton = new JButton(new ImageIcon(new ImageIcon("back.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        backButton.setPreferredSize(new Dimension(30, 30));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setToolTipText("Back");
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            panel.removeAll();
            dashBoard();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        // Build vertical layout
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(60));
        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0)); // 30px left inset
        titleWrapper.setOpaque(false);
        titleWrapper.add(titleLabel);
        verticalBox.add(titleWrapper);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(contentScrollPane);
        verticalBox.add(Box.createVerticalStrut(20));
        verticalBox.add(buttonPanel);

        // Fixed width container
        JPanel fixedWidthPanel = new JPanel();
        fixedWidthPanel.setLayout(new BoxLayout(fixedWidthPanel, BoxLayout.Y_AXIS));
        fixedWidthPanel.setOpaque(false);
        fixedWidthPanel.setMaximumSize(new Dimension(dynamicWidth, Integer.MAX_VALUE));
        fixedWidthPanel.add(verticalBox);

        // Centered panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(fixedWidthPanel);

        // Scroll for entire panel
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}

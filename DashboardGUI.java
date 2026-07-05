import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;

public class DashboardGUI extends JFrame {
    // Ultra-Premium Cyberpunk Obsidian Theme
    private final Color COLOR_BG = new Color(0x0B, 0x0C, 0x10);       // Pure Abyss Black
    private final Color COLOR_SURFACE = new Color(0x1F, 0x28, 0x33);  // Sleek Dark Charcoal Metallic
    private final Color COLOR_SIDEBAR = new Color(0x15, 0x1A, 0x21);  // Deep Slate Panel
    private final Color COLOR_CYAN = new Color(0x66, 0xFC, 0xF1);     // Neon Electric Cyan
    private final Color COLOR_DARK_CYAN = new Color(0x45, 0xB2, 0x9D);
    private final Color COLOR_NEON_PINK = new Color(0xFF, 0x00, 0x55); // Laser Crimson Action Button
    private final Color COLOR_TEXT_MAIN = new Color(0xC5, 0xA5, 0xC5); // Titanium White-Silver
    private final Color COLOR_TEXT_MUTE = new Color(0x66, 0x72, 0x7A); // Cool Muted Gray

    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Backend Hooks
    private final WorldClockManager clockManager = new WorldClockManager();
    private final PrayerTimeManager prayerManager = new PrayerTimeManager();
    private final TaskManager taskManager = new TaskManager();
    private final AlarmManager alarmManager = new AlarmManager();

    // Live Metrics Components
    private JLabel totalTasksCountLabel;
    private JLabel activeAlarmsCountLabel;

    public DashboardGUI() {
        setTitle("CHRONOSPHERE COMMAND NEXUS // V2.0");
        setSize(1050, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        // --- LEFT SIDEBAR PANEL ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(250, 720));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(0x1F, 0x28, 0x33)));

        // Branding Title Head
        JLabel titleLabel = new JLabel("CHRONOSPHERE");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_CYAN);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(35, 30, 5, 10));
        
        JLabel subtitleLabel = new JLabel("SYSTEM COMMAND");
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        subtitleLabel.setForeground(COLOR_TEXT_MUTE);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitleLabel.setBorder(new EmptyBorder(0, 32, 35, 10));

        sidebar.add(titleLabel);
        sidebar.add(subtitleLabel);

        // --- MAIN APPLICATION SCREEN DISPLAY DECK ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(COLOR_BG);

        // Attach UI Screens
        cardPanel.add(buildHomeDashboard(), "Dashboard");
        cardPanel.add(buildWorldClockPanel(), "WorldClock");
        cardPanel.add(buildPrayerPanel(), "PrayerTimes");
        cardPanel.add(buildTaskPanel(), "Tasks");
        cardPanel.add(buildAlarmPanel(), "Alarms");

        // Inject Modern Navigation Links
        addSleekNavLink(sidebar, " SYSTEM METRICS", "Dashboard");
        addSleekNavLink(sidebar, " GLOBAL TIMEWATCH", "WorldClock");
        addSleekNavLink(sidebar, " CHRONO PRAYER", "PrayerTimes");
        addSleekNavLink(sidebar, " OPERATIONS TASKER", "Tasks");
        addSleekNavLink(sidebar, " SECURITY ALARMS", "Alarms");

        add(sidebar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        
        // Populate view counters on initial screen launch
        updateDynamicMetricsCounters();
    }

    private void addSleekNavLink(JPanel sidebar, String text, final String targetCard) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 55));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(new Color(0x95, 0xA5, 0xA6));
        btn.setBackground(COLOR_SIDEBAR);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(COLOR_CYAN);
                btn.setBackground(new Color(0x1F, 0x28, 0x33));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(0x95, 0xA5, 0xA6));
                btn.setBackground(COLOR_SIDEBAR);
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDynamicMetricsCounters();
                cardLayout.show(cardPanel, targetCard);
            }
        });
        sidebar.add(btn);
    }

    private void styleInputWrapper(JTextField field) {
        field.setBackground(new Color(0x15, 0x1A, 0x21));
        field.setForeground(Color.WHITE);
        field.setCaretColor(COLOR_CYAN);
        field.setFont(new Font("Consolas", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x45, 0xB2, 0x9D), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
    }

    private void styleNeonActionButton(JButton button, Color bg) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void updateDynamicMetricsCounters() {
        if (totalTasksCountLabel != null) {
            totalTasksCountLabel.setText(String.valueOf(taskManager.getAllTasks().size()));
        }
        if (activeAlarmsCountLabel != null) {
            activeAlarmsCountLabel.setText(String.valueOf(alarmManager.getActiveAlarms().size()));
        }
    }

    private JPanel createSectionHeader(String title, String description) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLbl.setForeground(Color.WHITE);

        JLabel descLbl = new JLabel(description);
        descLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLbl.setForeground(COLOR_TEXT_MUTE);

        headerPanel.add(titleLbl, BorderLayout.NORTH);
        headerPanel.add(descLbl, BorderLayout.SOUTH);
        return headerPanel;
    }

    // --- VIEW 1: HOME MAIN DASHBOARD OVERHAUL ---
    private JPanel buildHomeDashboard() {
        JPanel mainContainer = new JPanel(new BorderLayout(30, 30));
        mainContainer.setBackground(COLOR_BG);
        mainContainer.setBorder(new EmptyBorder(40, 40, 40, 40));

        mainContainer.add(createSectionHeader("SYSTEM OPERATIONS MAINBOARD", "Real-time summary data indexes"), BorderLayout.NORTH);

        // Center Panel holds Hero Clock + Metrics Display Matrix Cards
        JPanel centerGrid = new JPanel(new GridLayout(2, 1, 30, 30));
        centerGrid.setBackground(COLOR_BG);

        // Huge Neon Central Clock Component Panel
        JPanel timeHeroCard = new JPanel(new GridBagLayout());
        timeHeroCard.setBackground(COLOR_SURFACE);
        timeHeroCard.setBorder(BorderFactory.createLineBorder(new Color(0x2F, 0x3B, 0x4C), 1));

        final JLabel timeHeroDisplay = new JLabel("00:00:00 PM", SwingConstants.CENTER);
        timeHeroDisplay.setFont(new Font("Consolas", Font.BOLD, 64));
        timeHeroDisplay.setForeground(COLOR_CYAN);

        final JLabel dateHeroDisplay = new JLabel("LOADING ENGINE...", SwingConstants.CENTER);
        dateHeroDisplay.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dateHeroDisplay.setForeground(COLOR_TEXT_MAIN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        timeHeroCard.add(timeHeroDisplay, gbc);
        gbc.gridy = 1;
        timeHeroCard.add(dateHeroDisplay, gbc);

        Timer ticker = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                timeHeroDisplay.setText(now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                dateHeroDisplay.setText(now.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")).toUpperCase());
            }
        });
        ticker.start();

        // Dual Bottom Horizontal Display Data Cards Row
        JPanel subCardsGrid = new JPanel(new GridLayout(1, 2, 30, 0));
        subCardsGrid.setBackground(COLOR_BG);

        // Task Data Metrics Panel Block
        JPanel taskCard = new JPanel(new BorderLayout());
        taskCard.setBackground(COLOR_SURFACE);
        taskCard.setBorder(new EmptyBorder(20, 25, 20, 25));
        JLabel taskTitle = new JLabel("TOTAL SCHEDULED OPERATIONS");
        taskTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        taskTitle.setForeground(COLOR_TEXT_MUTE);
        totalTasksCountLabel = new JLabel("0");
        totalTasksCountLabel.setFont(new Font("Consolas", Font.BOLD, 42));
        totalTasksCountLabel.setForeground(COLOR_CYAN);
        taskCard.add(taskTitle, BorderLayout.NORTH);
        taskCard.add(totalTasksCountLabel, BorderLayout.CENTER);

        // Alarm Metrics Block
        JPanel alarmCard = new JPanel(new BorderLayout());
        alarmCard.setBackground(COLOR_SURFACE);
        alarmCard.setBorder(new EmptyBorder(20, 25, 20, 25));
        JLabel alarmTitle = new JLabel("ACTIVE RUNTIME ALARM VECTORS");
        alarmTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        alarmTitle.setForeground(COLOR_TEXT_MUTE);
        activeAlarmsCountLabel = new JLabel("0");
        activeAlarmsCountLabel.setFont(new Font("Consolas", Font.BOLD, 42));
        activeAlarmsCountLabel.setForeground(COLOR_NEON_PINK);
        alarmCard.add(alarmTitle, BorderLayout.NORTH);
        alarmCard.add(activeAlarmsCountLabel, BorderLayout.CENTER);

        subCardsGrid.add(taskCard);
        subCardsGrid.add(alarmCard);

        centerGrid.add(timeHeroCard);
        centerGrid.add(subCardsGrid);

        mainContainer.add(centerGrid, BorderLayout.CENTER);
        return mainContainer;
    }

    // --- VIEW 2: WORLD CLOCK CONTROL OVERHAUL ---
    private JPanel buildWorldClockPanel() {
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(COLOR_BG);
        mainContainer.setBorder(new EmptyBorder(40, 40, 40, 40));

        mainContainer.add(createSectionHeader("GLOBAL ZONE MONITORS", "Active international database clock synchronization logs"), BorderLayout.NORTH);

        final DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> clockList = new JList<>(listModel);
        clockList.setBackground(COLOR_SURFACE);
        clockList.setForeground(Color.WHITE);
        clockList.setFont(new Font("Consolas", Font.PLAIN, 16));
        clockList.setFixedCellHeight(48);
        clockList.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton refreshBtn = new JButton("REFRESH ACTIVE TIME ARRAYS");
        styleNeonActionButton(refreshBtn, COLOR_CYAN);
        refreshBtn.setForeground(COLOR_BG);
        refreshBtn.setPreferredSize(new Dimension(0, 50));

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                for (Map.Entry<String, String> entry : clockManager.getAllWorldTimes().entrySet()) {
                    listModel.addElement(String.format("  %-25s  ▶  %s", entry.getKey(), entry.getValue()));
                }
            }
        });

        mainContainer.add(new JScrollPane(clockList), BorderLayout.CENTER);
        mainContainer.add(refreshBtn, BorderLayout.SOUTH);

        return mainContainer;
    }

    // --- VIEW 3: PRAYER PANEL OVERHAUL ---
    private JPanel buildPrayerPanel() {
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(COLOR_BG);
        mainContainer.setBorder(new EmptyBorder(40, 40, 40, 40));

        mainContainer.add(createSectionHeader("CHRONO PRAYER ARCHIVE", "Static local target lookup databases"), BorderLayout.NORTH);

        String[] cities = {"Islamabad", "Rawalpindi", "Lahore", "Karachi", "Peshawar"};
        JComboBox<String> cityBox = new JComboBox<>(cities);
        cityBox.setBackground(COLOR_SURFACE);
        cityBox.setForeground(Color.WHITE);
        cityBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cityBox.setPreferredSize(new Dimension(0, 45));

        final DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> displayList = new JList<>(model);
        displayList.setBackground(COLOR_SURFACE);
        displayList.setForeground(COLOR_CYAN);
        displayList.setFont(new Font("Consolas", Font.BOLD, 16));
        displayList.setFixedCellHeight(45);
        displayList.setBorder(new EmptyBorder(15, 15, 15, 15));

        cityBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                String city = (String) cityBox.getSelectedItem();
                String[] times = prayerManager.getPrayersForCity(city);
                String[] names = prayerManager.getPrayerNames();
                if (times != null) {
                    for (int i = 0; i < times.length; i++) {
                        model.addElement(String.format(" 🕌  %-15s ─────────────────── %s", names[i].toUpperCase(), times[i]));
                    }
                }
            }
        });

        mainContainer.add(cityBox, BorderLayout.NORTH);
        mainContainer.add(new JScrollPane(displayList), BorderLayout.CENTER);
        return mainContainer;
    }

    // --- VIEW 4: OPERATIONS TASKER PANEL OVERHAUL ---
    private JPanel buildTaskPanel() {
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(COLOR_BG);
        mainContainer.setBorder(new EmptyBorder(40, 40, 40, 40));

        mainContainer.add(createSectionHeader("CENTRAL TASK MATRIX", "Permanent flat-file schedule configurations"), BorderLayout.NORTH);

        final DefaultListModel<String> model = new DefaultListModel<>();
        List<String> initialTasks = taskManager.getAllTasks();
        for (String task : initialTasks) {
            model.addElement(task);
        }

        final JList<String> taskList = new JList<>(model);
        taskList.setBackground(COLOR_SURFACE);
        taskList.setForeground(Color.WHITE);
        taskList.setFont(new Font("Consolas", Font.PLAIN, 15));
        taskList.setFixedCellHeight(40);
        taskList.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Horizontal Panel Configuration for Data Entry Forms
        JPanel inputGrid = new JPanel(new GridLayout(1, 3, 15, 0));
        inputGrid.setBackground(COLOR_BG);
        final JTextField txtDate = new JTextField("05-07-2026");
        final JTextField txtDesc = new JTextField("Core software platform check");
        styleInputWrapper(txtDate);
        styleInputWrapper(txtDesc);

        JButton btnAdd = new JButton("COMMIT OPERATION");
        styleNeonActionButton(btnAdd, COLOR_CYAN);
        btnAdd.setForeground(COLOR_BG);

        inputGrid.add(txtDate);
        inputGrid.add(txtDesc);
        inputGrid.add(btnAdd);

        // Control Panel Modification Buttons Footer Area Strip
        JPanel controlRow = new JPanel(new GridLayout(1, 2, 25, 0));
        controlRow.setBackground(COLOR_BG);
        controlRow.setPreferredSize(new Dimension(0, 48));

        JButton btnDeleteSelected = new JButton("TERMINATE SPECIFIC NODE");
        styleNeonActionButton(btnDeleteSelected, COLOR_NEON_PINK);

        JButton btnClearAll = new JButton("WIPE WHOLE STORAGE MATRIX");
        styleNeonActionButton(btnClearAll, new Color(0x34, 0x49, 0x5E));

        controlRow.add(btnDeleteSelected);
        controlRow.add(btnClearAll);

        // Wiring Events Logic Block Configurations
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskManager.saveTask(txtDate.getText(), txtDesc.getText());
                model.clear();
                List<String> refreshedTasks = taskManager.getAllTasks();
                for (String task : refreshedTasks) {
                    model.addElement(task);
                }
                updateDynamicMetricsCounters();
            }
        });

        btnDeleteSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIdx = taskList.getSelectedIndex();
                if (selectedIdx != -1) {
                    taskManager.removeTaskAtIndex(selectedIdx);
                    model.clear();
                    List<String> refreshedTasks = taskManager.getAllTasks();
                    for (String task : refreshedTasks) {
                        model.addElement(task);
                    }
                    updateDynamicMetricsCounters();
                } else {
                    JOptionPane.showMessageDialog(null, "Operational Warning: Select active index line first to wipe.");
                }
            }
        });

        btnClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskManager.clearAllTasks();
                model.clear();
                updateDynamicMetricsCounters();
            }
        });

        mainContainer.add(inputGrid, BorderLayout.NORTH);
        mainContainer.add(new JScrollPane(taskList), BorderLayout.CENTER);
        mainContainer.add(controlRow, BorderLayout.SOUTH);
        return mainContainer;
    }

    // --- VIEW 5: ALARM VIEW MATRIX PANEL OVERHAUL ---
    private JPanel buildAlarmPanel() {
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setBackground(COLOR_BG);
        mainContainer.setBorder(new EmptyBorder(40, 40, 40, 40));

        mainContainer.add(createSectionHeader("ALARM THREAD MATRIX", "Manage active tracking alerts inside system memory"), BorderLayout.NORTH);

        final DefaultListModel<String> model = new DefaultListModel<>();
        List<String> initialAlarms = alarmManager.getActiveAlarms();
        for (String alarm : initialAlarms) {
            model.addElement(alarm);
        }

        final JList<String> alarmList = new JList<>(model);
        alarmList.setBackground(COLOR_SURFACE);
        alarmList.setForeground(COLOR_CYAN);
        alarmList.setFont(new Font("Consolas", Font.BOLD, 16));
        alarmList.setFixedCellHeight(42);
        alarmList.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel controlGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        controlGrid.setBackground(COLOR_BG);
        controlGrid.setPreferredSize(new Dimension(0, 45));
        final JTextField txtTime = new JTextField("07:00 AM");
        styleInputWrapper(txtTime);

        JButton btnSet = new JButton("DEPLOY INTERCEPT VECTORS");
        styleNeonActionButton(btnSet, COLOR_CYAN);
        btnSet.setForeground(COLOR_BG);
        controlGrid.add(txtTime);
        controlGrid.add(btnSet);

        JButton btnDeleteAlarm = new JButton("SHUTDOWN CHOSEN ALARM FREQUENCY");
        styleNeonActionButton(btnDeleteAlarm, COLOR_NEON_PINK);
        btnDeleteAlarm.setPreferredSize(new Dimension(0, 50));

        btnSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmManager.addAlarm(txtTime.getText());
                model.clear();
                List<String> refreshedAlarms = alarmManager.getActiveAlarms();
                for (String alarm : refreshedAlarms) {
                    model.addElement(alarm);
                }
                updateDynamicMetricsCounters();
            }
        });

        btnDeleteAlarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int targetIdx = alarmList.getSelectedIndex();
                if (targetIdx != -1) {
                    alarmManager.removeAlarm(targetIdx);
                    model.clear();
                    List<String> refreshedAlarms = alarmManager.getActiveAlarms();
                    for (String alarm : refreshedAlarms) {
                        model.addElement(alarm);
                    }
                    updateDynamicMetricsCounters();
                } else {
                    JOptionPane.showMessageDialog(null, "Selection Missing: Target an active frequency slot value to kill.");
                }
            }
        });

        mainContainer.add(controlGrid, BorderLayout.NORTH);
        mainContainer.add(new JScrollPane(alarmList), BorderLayout.CENTER);
        mainContainer.add(btnDeleteAlarm, BorderLayout.SOUTH);
        return mainContainer;
    }
}
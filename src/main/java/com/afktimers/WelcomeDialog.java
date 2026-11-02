package com.afktimers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.KeyEvent;
import javax.swing.*;
import net.runelite.client.util.LinkBrowser;

/** Optional clan link; never opens a browser unless the user clicks Join. */
final class WelcomeDialog {
    static final String DISCORD_URL = "https://discord.gg/UAAe6QEGD";

    static JDialog create(Window owner) {
        JDialog dialog = new JDialog(owner, "Welcome to AFK Timers");
        dialog.setModal(false);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setContentPane(content(() -> dialog.dispose()));
        dialog.getRootPane().registerKeyboardAction(e -> dialog.dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);
        return dialog;
    }

    static JPanel content(Runnable close) {
        Color background = new Color(35, 39, 46);
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(22, 24, 20, 24));
        JLabel text = new JLabel("<html><body style='width:370px;color:#eeeeee;font-family:sans-serif'>"
            + "<h1 style='color:#57d6dc;margin-top:0'>AFK Timers</h1>"
            + "<p><b>Created by Th3keeza0</b></p><br>"
            + "<p>Keep track of your potion effects, prayer and heart cooldown with clear, coloured bars. "
            + "Bars flash when less than 10% remains; the prayer warning uses your remaining prayer points.</p><br>"
            + "<h3>Make it your own</h3>"
            + "<p><b>Move:</b> Hold <b>Alt</b>, then click and drag the bars into position. "
            + "New bars stack <b>upwards</b>, keeping the bottom of the stack in place.</p><br>"
            + "<p><b>Customise:</b> Open <b>AFK Timers settings</b> to turn timers on or off, "
            + "adjust transparency and change bar size. Use <b>Preview bars</b> to position them "
            + "without active potions.</p><br>"
            + "<h3 style='color:#efc370'>Join OSRS geezers</h3>"
            + "<p>Want to join the clan? Add <b>Th3keeza0</b> in game, or join our Discord below.</p><br>"
            + "<p style='color:#b8bec8'>This welcome appears once. You can reopen it using "
            + "<b>Show welcome</b> in the plugin settings.</p></body></html>");
        panel.add(text, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        JButton discord = new JButton("Join our Discord");
        discord.setToolTipText(DISCORD_URL);
        discord.addActionListener(e -> LinkBrowser.browse(DISCORD_URL));
        JButton done = new JButton("Got it");
        done.addActionListener(e -> close.run());
        buttons.add(discord);
        buttons.add(done);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }
    private WelcomeDialog() { }
}

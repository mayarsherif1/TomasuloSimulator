package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import simulator.Simulator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class TomasulosApp {

    private JFrame frame;
    private JTextArea simulationOutput;
    private JButton startButton;
    private Simulator simulator;
    private JTextField multiplyLatencyField;
    private JTextField addReservationStationField;
    private JTextField multiplyReservationStationField;
    private JTextField loadBuffersField;
    private JTextField storeBuffersField;
    private JTextField addLatencyField;
    private JTextField loadLatencyField;
    private JTextField storeLatencyField;

    // byakhod latency w num of res station
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TomasulosApp window = new TomasulosApp();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TomasulosApp() {
        initialize();
        simulator = new Simulator();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 595, 376);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        startButton = new JButton("Start");
        startButton.addActionListener(this::startSimulation);
        frame.getContentPane().add(startButton, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));

        addLabelAndTextField(inputPanel, "Add Latency:", addLatencyField = new JTextField());
        addLatencyField.setColumns(12);
        addLabelAndTextField(inputPanel, "Multiply Latency:", multiplyLatencyField = new JTextField());
        addLabelAndTextField(inputPanel, "Load Latency:", loadLatencyField = new JTextField());
        addLabelAndTextField(inputPanel, "Store Latency:", storeLatencyField = new JTextField());
        addLabelAndTextField(inputPanel, "Add Reservation Stations:", addReservationStationField = new JTextField());
        addLabelAndTextField(inputPanel, "Multiply Reservation Stations:", multiplyReservationStationField = new JTextField());
        addLabelAndTextField(inputPanel, "Load Buffers:", loadBuffersField = new JTextField());
        addLabelAndTextField(inputPanel, "Store Buffers:", storeBuffersField = new JTextField());

        frame.getContentPane().add(inputPanel, BorderLayout.WEST);

        // JTextArea for simulation output
        simulationOutput = new JTextArea();
        simulationOutput.setEditable(false);
        simulationOutput.setColumns(20);
        JScrollPane scrollPane = new JScrollPane(simulationOutput);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addLabelAndTextField(JPanel panel, String labelText, JTextField textField) {
        JPanel subPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 20)); // Adjust the width as needed
        subPanel.add(label, BorderLayout.WEST);
        subPanel.add(textField, BorderLayout.CENTER);
        panel.add(subPanel);
    }

    private void startSimulation(ActionEvent event) {
        new Thread(() -> {
            try {
                // Clear existing output
                SwingUtilities.invokeLater(() -> simulationOutput.setText(""));

                int addLatency = Integer.parseInt(addLatencyField.getText());
                int mulLatency = Integer.parseInt(multiplyLatencyField.getText());
                
                int loadLatency = Integer.parseInt(loadLatencyField.getText());
                int storeLatency = Integer.parseInt(storeLatencyField.getText());
                
                int floatAddResStation = Integer.parseInt(addReservationStationField.getText());
                int intAddResStation = Integer.parseInt(addReservationStationField.getText());

                int floatMulResStations = Integer.parseInt(multiplyReservationStationField.getText());
                int intMulResStations = Integer.parseInt(multiplyReservationStationField.getText());

                int loadBuffers = Integer.parseInt(loadBuffersField.getText());
                int storeBuffers = Integer.parseInt(storeBuffersField.getText());
                Simulator.configure(addLatency, mulLatency,loadLatency, storeLatency, floatAddResStation, intAddResStation, floatMulResStations,
                        intMulResStations, loadBuffers, storeBuffers);

                // Redirect System.out to JTextArea
                JTextAreaOutputStream outStream = new JTextAreaOutputStream(simulationOutput);
                System.setOut(new PrintStream(outStream));

                Simulator.main(null);

                // Restore standard output
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values for all fields.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error during simulation: " + e.getMessage());
            }
        }).start();
    }
    
    // Helper class to redirect System.out to JTextArea
    private static class JTextAreaOutputStream extends OutputStream {
        private JTextArea textArea;

        public JTextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}


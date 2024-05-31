import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class StudentManagementSystem extends JFrame {
    private JLabel titleLabel;
    private JButton addStudentButton;
    private JButton updateStudentButton;
    private JButton viewStudentButton;
    private JButton assignGradeButton;
    private JTextField studentNameField;
    private JTextField studentIDField;
    private JComboBox<String> courseListComboBox;
    private JTextArea studentDetailsTextArea;

    private HashMap<String, String[]> studentDatabase = new HashMap<>();

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("Welcome to Student Management System.");
        addStudentButton = new JButton("Add Student");
        updateStudentButton = new JButton("Update Student");
        viewStudentButton = new JButton("View Student Details");
        assignGradeButton = new JButton("Assign Grade");
        studentDetailsTextArea = new JTextArea(10, 20);
        studentDetailsTextArea.setEditable(false);

        studentNameField = new JTextField(20);
        studentIDField = new JTextField(10);

        String[] courses = {"English Course", "Math Course", "Computer Science Course"};
        courseListComboBox = new JComboBox<>(courses);

        setLayout(new GridLayout(0, 1));

        add(titleLabel);
        add(addStudentButton);
        add(updateStudentButton);
        add(viewStudentButton);
        add(assignGradeButton);

        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Enter Student Name: "));
                panel.add(studentNameField);
                panel.add(new JLabel("Enter Student ID: "));
                panel.add(studentIDField);
                panel.add(new JLabel("Select Course: "));
                panel.add(courseListComboBox);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String studentName = studentNameField.getText();
                    String studentID = studentIDField.getText();
                    String selectedCourse = (String) courseListComboBox.getSelectedItem();
                    String[] studentDetails = {studentName, selectedCourse};
                    studentDatabase.put(studentID, studentDetails);
                    JOptionPane.showMessageDialog(null, "Student added: " + studentName + ", ID: " + studentID + ", Course: " + selectedCourse);
                }
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentID = JOptionPane.showInputDialog("Enter student ID to update: ");
                if (studentID != null && !studentID.isEmpty()) {
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Student ID: "));
                    panel.add(new JLabel(studentID));
                    panel.add(new JLabel("Enter Updated Student Name: "));
                    JTextField updatedNameField = new JTextField(20);
                    panel.add(updatedNameField);
                    panel.add(new JLabel("Enter Updated Course: "));
                    JComboBox<String> updatedCourseComboBox = new JComboBox<>(courses);
                    panel.add(updatedCourseComboBox);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String updatedName = updatedNameField.getText();
                        String updatedCourse = (String) updatedCourseComboBox.getSelectedItem();
                        JOptionPane.showMessageDialog(null, "Student Updated: Name: " + updatedName + ", Course: " + updatedCourse);
                        updateDisplayedStudentDetails(studentID, updatedName, updatedCourse);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid student ID.");
                }
            }
        });

        viewStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentID = JOptionPane.showInputDialog("Enter student ID to view details: ");
                if (studentID != null && !studentID.isEmpty()) {
                    String studentDetails = getStudentDetails(studentID);
                    studentDetailsTextArea.setText(studentDetails);
                    JOptionPane.showMessageDialog(null, new JScrollPane(studentDetailsTextArea), "Student Details", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid student ID.");
                }
            }
        });

        assignGradeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentID = JOptionPane.showInputDialog("Enter student ID to assign grade: ");
                if (studentID != null && !studentID.isEmpty()) {
                    String studentCourses = getStudentCourses(studentID);
                    String course = JOptionPane.showInputDialog("Select a course to assign grade:\n" + studentCourses);
                    if (course != null && !course.isEmpty()) {
                        String grade = JOptionPane.showInputDialog("Enter grade for " + course + ":");
                        if (grade != null && !grade.isEmpty()) {
                            assignGrade(studentID, course, grade);
                        } else {
                            JOptionPane.showMessageDialog(null, "Please enter a valid grade.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a course.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid student ID.");
                }
            }
        });
    }

    private void updateDisplayedStudentDetails(String studentID, String updatedName, String updatedCourse) {
        String[] studentDetails = {updatedName, updatedCourse};
        studentDatabase.put(studentID, studentDetails);
    }

    private String getStudentDetails(String studentID) {
        String[] details = studentDatabase.get(studentID);
        if (details != null) {
            return "Student ID: " + studentID + "\nStudent Name: " + details[0] + "\nCourse: " + details[1];
        } else {
            return "Student details not found.";
        }
    }

    private String getStudentCourses(String studentID) {
        String[] details = studentDatabase.get(studentID);
        if (details != null) {
            return details[1];
        } else {
            return "Student course not found.";
        }
    }

    private void assignGrade(String studentID, String course, String grade) {
        String[] details = studentDatabase.get(studentID);
        if (details != null) {
            String updatedDetails = details[1] + "\n" + course + ": " + grade;
            details[1] = updatedDetails;
            JOptionPane.showMessageDialog(null, "Grade assigned: Student ID: " + studentID + ", Course: " + course + ", Grade: " + grade);
        } else {
            JOptionPane.showMessageDialog(null, "Student not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StudentManagementSystem gui = new StudentManagementSystem();
                gui.setVisible(true);
            }
        });
    }
}
/*
 * 
 * This is the summary dialog for displaying all Employee details
 * 
 * */

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class EmployeeSummaryDialog extends JDialog implements ActionListener {
	// vector with all Employees details
	Vector<Object> allEmployees;
	JButton back;
	
	public EmployeeSummaryDialog(Vector<Object> allEmployees) {
		setTitle("Employee Summary");
		setModal(true);
		this.allEmployees = allEmployees;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane(summaryPane());
		setContentPane(scrollPane);

		setSize(850, 500);
		setLocation(350, 250);
		setVisible(true);

	}
	// initialise container
	public Container summaryPane() {
		JPanel summaryDialog = new JPanel(new MigLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JTable employeeTable;
		DefaultTableModel tableModel;
		
		Vector<String> header = new Vector<String>();
		// header names
		String[] headerName = { "ID", "PPS Number", "Surname", "First Name", "Gender", "Department", "Salary",
				"Full Time" };
		
		// add headers
		for (int i = 0; i < headerName.length; i++) {
			header.addElement(headerName[i]);
		}// end for
		
		tableModel = this.createDefaultTableModel(header);
		employeeTable = this.createTable(tableModel, headerName);
		
		JScrollPane scroll = new JScrollPane(employeeTable);
		back.addActionListener(this);
		back.setToolTipText("Return to main Screen");
		
		summaryDialog.add(buttonPanel, "growx, pushx, wrap");
		summaryDialog.add(scroll, "growx, pushx, wrap");
		
		scroll.setBorder(BorderFactory.createTitledBorder("Employees"));
		return summaryDialog;}
			
		
		public DefaultTableModel createDefaultTableModel(Vector<String>header){
			return new DefaultTableModel(this.allEmployees, header){
			public Class getColumnClass(int c) {
				switch (c) {
				case 0:
					return Integer.class;
				case 4:
					return Character.class;
				case 6:
					return Double.class;
				case 7:
					return Boolean.class;
				default:
					return String.class;
				}// end switch
			}// end getColumnClass
		};
		}
		
		public JTable createTable(DefaultTableModel tableModel, String[] headerName){
			
		JTable employeeTable = new JTable(tableModel);
		//center column alignment
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		//left column alignment
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		
		//set column width
		int[] colWidth = {15,100,120,50,120,80,80};
		// add header names to table
		for (int i = 0; i < employeeTable.getColumnCount(); i++) {
			employeeTable.getColumn(headerName[i]).setMinWidth(colWidth[i]);
		}// end for
		// set alignments
		employeeTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		employeeTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		employeeTable.getColumnModel().getColumn(6).setCellRenderer(new DecimalFormatRenderer());

		employeeTable.setEnabled(false);
		employeeTable.setPreferredScrollableViewportSize(new Dimension(800, (15 * employeeTable.getRowCount() + 15)));
		employeeTable.setAutoCreateRowSorter(true);
		
		return employeeTable;
		
	}
		

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back){
			dispose();
		}

	}
	// format for salary column
	static class DecimalFormatRenderer extends DefaultTableCellRenderer {
		 private static final DecimalFormat format = new DecimalFormat(
		 "\u20ac ###,###,##0.00" );

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			 JLabel label = (JLabel) c;
			 label.setHorizontalAlignment(JLabel.RIGHT);
			 // format salary column
			value = format.format((Number) value);

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}// end getTableCellRendererComponent
	}// DefaultTableCellRenderer
}// end class EmployeeSummaryDialog

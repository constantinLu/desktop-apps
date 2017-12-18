package org.random;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;

public class Randomizer extends JFrame {

	private RandomizerPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Randomizer() {

		setupFrame();

	}

	private void setupFrame() {

		contentPane = new RandomizerPanel();
		contentPane.setSize(new Dimension(500, 350));
		contentPane.setPreferredSize(new Dimension(500, 350));
		contentPane.setBorder(null);
		this.setContentPane(contentPane);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Randomizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(255, 255, 255));
	}
}

	

	




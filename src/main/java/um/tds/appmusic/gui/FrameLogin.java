package um.tds.appmusic.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import um.tds.appmusic.controlador.Controlador;

import javax.swing.border.EtchedBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FrameLogin {

	private JFrame frame;
	private JTextField textUsuario;
	private JPasswordField textPassword;
	private JButton btnRegistro;
	private JPanel panelLogo;

	public FrameLogin() {
		initialize();
	}

	public void mostrarVentana() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("AppMusic");
		frame.setBackground(new Color(0, 0, 0));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(375, 125, 600, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		crearPanelNorte();
		crearPanelCentral();
		crearPanelSur();

	}

	private void crearPanelNorte() {

		JPanel panelNorte = new JPanel();
		panelNorte.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panelNorte.setBackground(new Color(135, 206, 235));
		panelNorte.setPreferredSize(new Dimension(450, 80));
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));

		crearPanelLogo();
		panelNorte.add(panelLogo);

	}

	private void crearPanelLogo() {

		panelLogo = new JPanel();
		panelLogo.setBackground(new Color(135, 206, 235));
		FlowLayout flowLayout = (FlowLayout) panelLogo.getLayout();
		flowLayout.setVgap(18);

		JLabel lblNewLabel = new JLabel(" AppMusic");
		lblNewLabel.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\LogoIco.png"));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 25));
		panelLogo.add(lblNewLabel);

	}

	private void crearPanelCentral() {

		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(new Color(135, 206, 235));
		panelCentral
				.setBorder(new TitledBorder(
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"", TitledBorder.RIGHT, TitledBorder.TOP, null, new Color(0, 0, 0)),
						"LET THE MUSIC PLAY", TitledBorder.TRAILING, TitledBorder.TOP, null, Color.BLACK));
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

		panelCentral.add(crearPanelUsuario());
		panelCentral.add(crearPanelClave());

		JButton btnLogin = new JButton("Iniciar sesión");
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setFocusPainted(false);

		btnLogin.addActionListener(ev -> {

			boolean login = Controlador.INSTANCE.loginUsuario(textUsuario.getText(),
					new String(textPassword.getPassword()));

			if (login) {
				FrameMain window = new FrameMain();
				window.mostrarVentana();
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "Nombre de usuario o contraseña no valido", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		panelCentral.add(btnLogin);

	}

	private void crearPanelSur() {

		JPanel panelSur = new JPanel();
		panelSur.setBackground(new Color(135, 206, 235));
		frame.getContentPane().add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
		panelSur.add(crearPanelRegistro());

	}

	private JPanel crearPanelUsuario() {

		JPanel panelUsuario = new JPanel();
		panelUsuario.setBackground(new Color(135, 206, 235));
		panelUsuario.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblUsuario = new JLabel("Usuario :");
		lblUsuario.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		panelUsuario.add(lblUsuario);

		textUsuario = new JTextField();

		panelUsuario.add(textUsuario);
		textUsuario.setColumns(15);

		return panelUsuario;

	}

	private JPanel crearPanelClave() {

		JPanel panelClave = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelClave.getLayout();
		flowLayout_2.setHgap(3);
		panelClave.setBackground(new Color(135, 206, 235));

		JLabel lblNewLabel_2 = new JLabel("   Clave :");
		lblNewLabel_2.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		panelClave.add(lblNewLabel_2);

		textPassword = new JPasswordField();
		textPassword.setColumns(15);
		textPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {
					boolean login = Controlador.INSTANCE.loginUsuario(textUsuario.getText(),
							new String(textPassword.getPassword()));

					if (login) {
						FrameMain window = new FrameMain();
						window.mostrarVentana();
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "Nombre de usuario o contraseña no valido", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		panelClave.add(textPassword);
		return panelClave;
	}

	private JPanel crearPanelRegistro() {

		JPanel panelRegistro = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelRegistro.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelRegistro.setBackground(new Color(135, 206, 235));

		btnRegistro = new JButton("\u00BFNo tienes cuenta? Registrate ahora.");
		btnRegistro.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegistro.setForeground(new Color(255, 0, 0));
		btnRegistro.setBackground(new Color(135, 206, 235));
		btnRegistro.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 11));
		btnRegistro.setBorderPainted(false);
		btnRegistro.setContentAreaFilled(false);
		btnRegistro.setOpaque(true);
		btnRegistro.setFocusPainted(false);

		panelRegistro.add(btnRegistro);

		btnRegistro.addActionListener(ev -> {
			FrameRegistro registroView = new FrameRegistro();
			registroView.mostrarVentana();
			frame.dispose();
		});

		return panelRegistro;
	}
}

package lejos.mf.pc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lejos.mf.common.UnitMessage;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import lejos.mf.common.MessageListenerInterface;
import lejos.mf.common.StreamUnitMessage;
import lejos.mf.common.UnitMessageType;

public class MessageFrameworkTester implements MessageListenerInterface {
	// MessageFramework m_mfBobomb;
	MessageFramework m_mfDaneel;

	// MessageFramework m_mfNxt;

	private JFrame m_frame;

	protected final JPanel buttonPanel = new JPanel();
	protected final JButton StopButton = new JButton("Stop");
	protected final JButton ForwardButton = new JButton("Forward");
	protected final JButton BackwardButton = new JButton("Backward");
	protected final JButton LeftButton = new JButton("Left");
	protected final JButton RightButton = new JButton("Right");

	protected final JTextArea m_logTextArea = new JTextArea();
	protected final JTextArea m_commandTextArea = new JTextArea();

	protected static String title = "NXJHost";
	protected boolean m_btOpen = false;

	protected NXTInfo m_daneelInfo = new NXTInfo(NXTCommFactory.BLUETOOTH,
			"Daneel", "001653119482");

	public static void main(String args[]) {
		try {
			MessageFrameworkTester m_instance = new MessageFrameworkTester();
			m_instance.run(args);
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println("Error: " + t.getMessage());
			System.exit(1);
		}
	}

	public MessageFrameworkTester() {
		m_mfDaneel = new MessageFramework();
		m_mfDaneel.addMessageListener(this);
		m_mfDaneel.ConnectToNXT(m_daneelInfo);
	}

	public void run(String[] args) {

		m_frame = new JFrame(title);

		Dimension d = new Dimension(800, 600);
		m_frame.setSize(d);
		m_frame.setResizable(false);

		WindowListener listener = new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				m_mfDaneel.close();
				System.exit(0);
			}
		};

		m_frame.addWindowListener(listener);

		StopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				StreamUnitMessage msg = new StreamUnitMessage(UnitMessageType.Command,
						"stop");
				m_mfDaneel.SendMessage(msg);
			}
		});

		ForwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				StreamUnitMessage msg = new StreamUnitMessage(UnitMessageType.Command,
						"forward");
				m_mfDaneel.SendMessage(msg);
			}
		});

		BackwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				StreamUnitMessage msg = new StreamUnitMessage(UnitMessageType.Command,
						"backward");
				m_mfDaneel.SendMessage(msg);
			}
		});

		LeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				StreamUnitMessage msg = new StreamUnitMessage(UnitMessageType.Command,
						"left");
				m_mfDaneel.SendMessage(msg);
			}
		});

		RightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				StreamUnitMessage msg = new StreamUnitMessage(UnitMessageType.Command,
						"right");
				m_mfDaneel.SendMessage(msg);
			}
		});

		buttonPanel.add(StopButton);
		buttonPanel.add(ForwardButton);
		buttonPanel.add(BackwardButton);
		buttonPanel.add(LeftButton);
		buttonPanel.add(RightButton);

		m_logTextArea.setEditable(false);
		final JScrollPane logPane = new JScrollPane(m_logTextArea);

		m_commandTextArea.setEditable(false);
		final JScrollPane commandPane = new JScrollPane(m_commandTextArea);

		final JPanel textPanel = new JPanel(new GridLayout(1, 2));
		textPanel.add(logPane);
		textPanel.add(commandPane);

		m_frame.getContentPane().add(new JScrollPane(buttonPanel),
				BorderLayout.NORTH);
		m_frame.getContentPane().add(new JScrollPane(textPanel),
				BorderLayout.CENTER);

		// m_frame.pack();
		m_frame.setVisible(true);
	}

	@Override
	public void receivedNewMessage(UnitMessage msg) {
		System.out.println("Received: " + msg);
	}
}

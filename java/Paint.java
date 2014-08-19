

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Paint extends JFrame {
	PaintPanel pp = new PaintPanel();
	int pressX;
	int pressY;
	int type = 1;

	private MyImage bufferedImage = new MyImage(
			1024,768,
			BufferedImage.TYPE_INT_BGR);

	public Paint() {
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == 'q'){
					System.exit(0);
				}else if(e.getKeyChar() == 'r'){
					type = 1;
				}else if(e.getKeyChar() == 'p'){
					type = 2;
				}else if(e.getKeyChar() == 'l'){
					type = 3;
				}else if(e.getKeyChar() == 'o'){
					type = 4;
				}else if(e.getKeyChar() == 'c'){
					pp.getGraphics().clearRect(0, 0, 1024, 768);
					bufferedImage = new MyImage(
							1024,768,
							BufferedImage.TYPE_INT_BGR);
				}else if(e.isControlDown()){
					Robot robot = null;
					try {
						robot = new Robot();
					} catch (AWTException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, 1024, 768));  
						try {
							ImageIO.write(image, ".JPG", new File("c:\\temp.jpg"));
							JOptionPane.showMessageDialog(null,"done!");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}

		});

		pp.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				pressX = e.getX();
				pressY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(type==1){
					drawRect(bufferedImage.getGraphics(),pressX,pressY,e);
				}else if(type==2){
					drawLine(bufferedImage.getGraphics(),pressX,pressY,e);
				}else if(type==3){
					drawLine2(bufferedImage.getGraphics(),pressX,pressY,e);
				}else if(type==4){
					drawOval(bufferedImage.getGraphics(),pressX,pressY,e);
				}
			}

		});

		pp.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				if(type==1){
					drawRect(pp.getGraphics(),pressX,pressY,e);
				}else if(type==2){
					drawLine(bufferedImage.getGraphics(),pressX,pressY,e);
					pressX = e.getX();
					pressY = e.getY();
				}else if(type==3){
					drawLine2(pp.getGraphics(),pressX,pressY,e);
				}else if(type==4){
					drawOval(pp.getGraphics(),pressX,pressY,e);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		pp.setBackground(Color.white);
		add(pp);
	}

	//矩形工具
	public void drawRect(Graphics g,int pressX,int pressY,MouseEvent e){
		g.drawImage(bufferedImage, 0, 0,
				1024,768,Color.BLUE, null);
		g.setColor(Color.red);
		int x = pressX<e.getX()?pressX:e.getX();
		int y = pressY<e.getY()?pressY:e.getY();
		g.drawRect(x,y, Math.abs(e.getX()-pressX), Math.abs(e.getY()-pressY));
	}

	//椭圆工具
	public void drawOval(Graphics g,int pressX,int pressY,MouseEvent e){
		g.drawImage(bufferedImage, 0, 0,
				1024,768,Color.BLUE, null);
		g.setColor(Color.red);
		int x = pressX<e.getX()?pressX:e.getX();
		int y = pressY<e.getY()?pressY:e.getY();
		g.drawOval(x,y, Math.abs(e.getX()-pressX), Math.abs(e.getY()-pressY));
	}

	//铅笔工具
	public void drawLine(Graphics g,int pressX,int pressY,MouseEvent e){
		pp.getGraphics().drawImage(bufferedImage, 0, 0,
				1024,768,Color.BLUE, null);
		g.setColor(Color.red);
		g.drawLine(pressX, pressY, e.getX(),e.getY());
	}

	//直线工具
	public void drawLine2(Graphics g,int pressX,int pressY,MouseEvent e){
		g.drawImage(bufferedImage, 0, 0,
				1024,768,Color.BLUE, null);
		g.setColor(Color.red);
		g.drawLine(pressX, pressY, e.getX(),e.getY());
	}

	public static void main(String[] args) {
		new Paint();
	}
}

class PaintPanel extends JPanel{}

class MyImage extends BufferedImage{
	public MyImage(int width, int height, int type) {
		super(width,height,type);
		this.getGraphics().fillRect(0, 0, 1024, 768);
	}
}

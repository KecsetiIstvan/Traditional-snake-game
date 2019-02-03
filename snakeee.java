//Kecseti István 
//kiim1727
//524/2
package jatekok;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class snakeee extends JFrame implements KeyListener{
	private static final long serialVersionUID = 1L;
	
	static Panel fej=new Panel();
	static Panel players[]=new Panel[100];
	static int   playerHossz=4;
	static Panel food=new Panel();
	static Panel ebbe=new Panel();
	
	static public Boolean jobb=false;
	static public Boolean bal=true;
	static public Boolean fel=false;
	static public Boolean le=false;
	
	static int playerW=30;
	static int playerH=30;
	static int foodH=15;
	static int foodW=15;
	static int startX=400;
	static int startY=400;
	static int pontok=0;
	static Label pontTar=new Label("Pontok:  "+pontok);
	
	public snakeee() {
		this.setLayout(new BorderLayout());
		ebbe.setLayout(null);
		fej.setSize(playerW,playerH);
		food.setBounds(startX,startY,foodW,foodH);
		pontTar.setBounds(0,0,100,20);
		pontTar.setFont(new Font("Serif", Font.BOLD, 16));
		
		for(int i = 0; i < 100; i++){
			   players[i] = new Panel();
		}
		ebbe.add(fej);
		ebbe.add(food);
		ebbe.add(pontTar);
		this.add(ebbe,BorderLayout.CENTER);
		this.addKeyListener(this);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stu
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		// TODO Auto-generated method stub
		if(e.getKeyCode()==37 && !bal) {
			jobb=true;
			bal=false;
			fel=false;
			le=false;
		} //jobb
		if(e.getKeyCode()==39 && !jobb) {
			jobb=false;
			bal=true;
			fel=false;
			le=false;
		} //balra
		if(e.getKeyCode()==38 && !le) {
			jobb=false;
			bal=false;
			fel=true;
			le=false;
		}//FEL
		if(e.getKeyCode()==40 && !fel) {
			jobb=false;
			bal=false;
			fel=false;
			le=true;
		}//le
		if(e.getKeyCode()==27) {
			System.exit(0);
		}//le
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) throws InterruptedException {
		snakeee k=new snakeee();
		k.setExtendedState(Frame.MAXIMIZED_BOTH);
		k.setUndecorated(true);
		k.setVisible(true);
		
		Color c;
		Random rand = new Random();
		int  r = rand.nextInt(255) ;
		int  g = rand.nextInt(255) ;
		int  b = rand.nextInt(255) ;
		int rj=1;
		int gj=-1;
		int bj=1;
		
		double x=startX;
		double y=startY;
		double speed=52;
		int hibaHatar=10;
		
		fej.setLocation(startX,startY);
		Color p;
		p=new Color(0,0,0);
		fej.setBackground(p);
		
		Color f;
		int fr=rand.nextInt(255) ;
		int fg=rand.nextInt(255) ;
		int fb=rand.nextInt(255) ;
		f=new Color(fr,fg,fb);
		food.setBackground(f);
		
		int randX;
		int randY;
		randX=rand.nextInt(k.getWidth()-60)+30;
		randY=rand.nextInt(k.getHeight()-60)+30;
		while(randX%15!=0) randX--;
		while(randY%15!=0) randY--;
		
		Boolean halal=false;
		
		for(int i=0;i<=playerHossz;i++) {
			players[i].setBackground(p);
			players[i].setSize(playerW,playerH);
			ebbe.add(players[i]);
		}
		
		while(!halal) {
			r+=rj;
			g+=gj;
			b+=bj;
			if(r>=253 || r <= 3) rj*=-1;
			if(g>=253 || g <= 3) gj*=-1;
			if(b>=253 || b <= 3) bj*=-1;
			c=new Color(r,g,b);
			k.setBackground(c);
			ebbe.setBackground(c);
			pontTar.setBackground(c);
			
			if(x<=0-playerH) x=k.getWidth()-playerH;//halal=true;//x=0;
			if(y<=0-playerW) y=k.getHeight()-playerH;//halal=true;//y=0;
			if(y>=k.getHeight())y=-playerH;//halal=true;// y=ebbe.getHeight()-playerW;
			if(x>=k.getWidth()) x=-playerW;//halal=true;// x=ebbe.getWidth()-playerH;
			for(int i=1;i<=playerHossz;i++) {
				if(players[i].getLocation().getX()==x && players[i].getLocation().getY()==y) {
					halal=true;
				}
			}
			if((randX+foodW<=x+playerW+hibaHatar && randX >=x-hibaHatar)&& (randY+foodH<=y+playerH+hibaHatar && randY >=y-hibaHatar)){
				
				randX=rand.nextInt(k.getWidth()-60)+30;
				randY=rand.nextInt(k.getHeight()-60)+30;
				while(randX%15!=0) randX--;
				while(randY%15!=0) randY--;
				
				pontok++;
				pontTar.setText("Pontok:  "+pontok);
				
				food.setLocation(randX, randY);
				
				fr=rand.nextInt(255) ;
				fg=rand.nextInt(255) ;
				fb=rand.nextInt(255) ;
				f=new Color(fr,fg,fb);
				food.setBackground(f);
				
				speed-=2;
				playerHossz++;
				players[playerHossz].setBackground(p);
				players[playerHossz].setSize(playerW,playerH);
				ebbe.add(players[playerHossz]);
			}
			food.setLocation(randX, randY);
			for(int i=playerHossz;i>=0;i--) {
				if(i==0)
					players[i].setLocation(fej.getLocation());
				else
					players[i].setLocation(players[i-1].getLocation());
			}
			fej.setLocation((int)x,(int)y);
			if(jobb) x-=playerW+1;
			if(bal)  x+=playerW+1;
			if(fel)  y-=playerH+1;
			if(le)	 y+=playerH+1;
			TimeUnit.MILLISECONDS.sleep((int)speed);
		}
		
		Component frame = null;
		JOptionPane.showMessageDialog(frame,   "  Meghaltál :( \n  Dekár érted :( \n  Azért jó játék volt!  Pontszámod: "+pontok, "Game Over :(", JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	}
}
